package be.ddd.application.beverage;

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

    private final MemberBeverageLikeRepository memberBeverageLikeRepository;
    private final MemberRepository memberRepository;
    private final CafeBeverageRepository cafeBeverageRepository;

    @Override
    public void likeBeverage(Long memberId, UUID productId) {
        Member member =
                memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        CafeBeverage beverage =
                cafeBeverageRepository
                        .findByProductId(productId)
                        .orElseThrow(CafeBeverageNotFoundException::new);

        if (memberBeverageLikeRepository
                .findByMemberIdAndBeverageProductId(memberId, productId)
                .isPresent()) {
            throw new AlreadyLikeBeverageException();
        }

        memberBeverageLikeRepository.save(new MemberBeverageLike(member, beverage));
    }

    @Override
    public void unlikeBeverage(Long memberId, UUID productId) {
        MemberBeverageLike memberBeverageLike =
                memberBeverageLikeRepository
                        .findByMemberIdAndBeverageProductId(memberId, productId)
                        .orElseThrow(LikeNotFoundException::new);
        memberBeverageLikeRepository.delete(memberBeverageLike);
    }
}
