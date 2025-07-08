package be.ddd.application.beverage;

import be.ddd.api.dto.res.BeverageLikeDto;
import be.ddd.domain.entity.crawling.CafeBeverage;
import be.ddd.domain.entity.member.Member;
import be.ddd.domain.entity.member.MemberBeverageLike;
import be.ddd.domain.exception.AlreadyLikeBeverageException;
import be.ddd.domain.exception.CafeBeverageNotFoundException;
import be.ddd.domain.exception.LikeNotFoundException;
import be.ddd.domain.exception.MemberNotFoundException;
import be.ddd.domain.repo.CafeBeverageRepository;
import be.ddd.domain.repo.MemberBeverageLikeRepository;
import be.ddd.domain.repo.MemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BeverageLikeServiceImpl implements BeverageLikeService {

    private final MemberBeverageLikeRepository likeRepo;
    private final MemberRepository memberRepo;
    private final CafeBeverageRepository beverageRepo;

    @Override
    public BeverageLikeDto likeBeverage(Long memberId, UUID productId) {
        Member member = findMember(memberId);
        CafeBeverage bev = findBeverage(productId);

        if (likeRepo.existsByMemberIdAndBeverageProductId(memberId, productId)) {
            throw new AlreadyLikeBeverageException();
        }

        likeRepo.save(new MemberBeverageLike(member, bev));
        return toDto(bev.getProductId(), true);
    }

    @Override
    public BeverageLikeDto unlikeBeverage(Long memberId, UUID productId) {
        if (!likeRepo.existsByMemberIdAndBeverageProductId(memberId, productId)) {
            throw new LikeNotFoundException();
        }
        likeRepo.deleteByMemberIdAndBeverageProductId(memberId, productId);
        return toDto(productId, false);
    }

    private Member findMember(Long memberId) {
        return memberRepo.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private CafeBeverage findBeverage(UUID productId) {
        return beverageRepo
                .findByProductId(productId)
                .orElseThrow(CafeBeverageNotFoundException::new);
    }

    private BeverageLikeDto toDto(UUID productId, boolean liked) {
        long likeCount = likeRepo.countByBeverageProductId(productId);
        return new BeverageLikeDto(productId, liked, likeCount);
    }
}
