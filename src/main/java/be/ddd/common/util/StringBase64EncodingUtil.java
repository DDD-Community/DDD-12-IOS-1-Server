package be.ddd.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StringBase64EncodingUtil {
    @Value("${app.hmac.secret}")
    private String HMAC_SECRET;

    @Value("${app.hmac.algorithm}")
    private String HMAC_ALGO;

    public String encodeSignedCursor(Long cursorId) {
        String cursor = cursorId.toString();

        // cursor → Base64
        String cursorB64 =
                Base64.getUrlEncoder()
                        .withoutPadding()
                        .encodeToString(cursor.getBytes(StandardCharsets.UTF_8));

        // 2) cursor 바이트로 HMAC-SHA256 계산
        byte[] signature =
                hmacSha256(
                        cursor.getBytes(StandardCharsets.UTF_8),
                        HMAC_SECRET.getBytes(StandardCharsets.UTF_8));

        // 3) signature → Base64
        String sigB64 = Base64.getUrlEncoder().withoutPadding().encodeToString(signature);

        return cursorB64 + "." + sigB64;
    }

    public Long decodeSignedCursor(String token) {
        String[] parts = token.split("\\.", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException();
        }

        String cursorB64 = parts[0];
        String sigB64 = parts[1];

        byte[] cursorBytes = Base64.getUrlDecoder().decode(cursorB64);
        byte[] sigBytes = Base64.getUrlDecoder().decode(sigB64);

        // 재계산
        byte[] expectedSig = hmacSha256(cursorBytes, HMAC_SECRET.getBytes(StandardCharsets.UTF_8));

        // constant-time 비교
        if (!constantTimeEquals(sigBytes, expectedSig)) {
            throw new IllegalArgumentException("Cursor token signature mismatch");
        }

        String cursorStr = new String(cursorBytes, StandardCharsets.UTF_8);
        return Long.parseLong(cursorStr);
    }

    private byte[] hmacSha256(byte[] data, byte[] key) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGO);
            mac.init(new SecretKeySpec(key, HMAC_ALGO));
            return mac.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate HMAC-SHA256", e);
        }
    }

    private boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) return false;
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
}
