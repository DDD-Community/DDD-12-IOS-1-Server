package be.ddd.domain.repo;

import be.ddd.domain.entity.member.MemberBeverageLike;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBeverageLikeRepository extends JpaRepository<MemberBeverageLike, Long> {
    void deleteByMemberIdAndBeverageProductId(Long memberId, UUID productId);

    boolean existsByMemberIdAndBeverageProductId(Long memberId, UUID productId);

    long countByBeverageProductId(UUID productId);
}
