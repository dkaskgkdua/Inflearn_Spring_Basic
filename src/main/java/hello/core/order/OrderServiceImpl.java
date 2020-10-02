package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
*  실행에만 집중하면 됨!
*  생성자 주입
*  - 생성자 주입 시 필드에 fianl 키워드를 사용할 수 있고 혹시라도 설정되지 않는 오류를 컴파일 시점에 막음
*  - 항상 생성자 주입을 선택하되 가끔 옵션이 필요하면 수정자 주입을 선택!
* */
@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    /*
    *  필드 주입
    *  - 필들에 바로 주입하는 방법이지만 외부에서 변경이 불가능해 테스트 하기 힘들다.
    *  - DI 프레임워크가 없으면 아무것도 할 수가 없다.
    *  @Autowired private MemberRository memberRepository
    * */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member= memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
    // 테스트용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
    /*
    *  일반 메서드 주입
    *  - 일반 메서드를 통해 주입 받을 수 있다.
    *  @Autowired
    *  public void init(MemberRepositroy memberRepositroy, DiscountPolicy discountPolicy){
    *     this.memberRepository = memberRepository;
    *     this.discountPolicy = discountPolicy;
    *  }
    * */
    /*
      수정자 주입(setter 주입)
      - 선택, 변경 가능성이 있는 의존관계에 사용
      - 주입할 대상이 없어도 동작하게 하려면 required =false 를 선언해주면 된다.
        기본값은 true 임
    @Autowired(required = false)
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

     */
    /*
    @RequiredArgsConstructor 를 사용하면
    final 로 선언해준 필드를 기준으로 생성자를 만들어 준다.
    아래 생성자는 없애주면 됨.
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    */

}
