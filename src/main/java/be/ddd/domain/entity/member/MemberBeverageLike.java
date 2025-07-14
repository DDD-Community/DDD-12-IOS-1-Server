package be.ddd.domain.entity.member;

import be.ddd.common.entity.BaseTimeEntity;
import be.ddd.domain.entity.crawling.CafeBeverage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member_beverage_likes")
public class MemberBeverageLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beverage_id", nullable = false)
    private CafeBeverage beverage;

    public MemberBeverageLike(Member member, CafeBeverage beverage) {
        this.member = member;
        this.beverage = beverage;
    }
}
