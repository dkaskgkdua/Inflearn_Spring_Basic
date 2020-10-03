package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/*
*  실행에만 집중하면 됨!
*  생성자 주입
*  - 생성자 주입 시 필드에 fianl 키워드를 사용할 수 있고 혹시라도 설정되지 않는 오류를 컴파일 시점에 막음
*  - 항상 생성자 주입을 선택하되 가끔 옵션이 필요하면 수정자 주입을 선택!
* */
@Component
//@RequiredArgsConstructor
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
    public void setDiscountPolicy(@Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

     */
    /*
    @RequiredArgsConstructor 를 사용하면
    final 로 선언해준 필드를 기준으로 생성자를 만들어 준다.
    아래 생성자는 없애주면 됨.
     */
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, //@Qualifier("mainDiscountPolicy")
            @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    /*
    * - @Autowired 는 타입 매칭을 시도하고, 이때 여러 빈이 등록되어 있으면 파라미터 이름으로
    *   빈 이름을 추가 매칭한다.
    * - @Quilifier는 추가 구분자를 붙여주는 방법이다. 주입시 추가적인 방법을 제공하는 것이지
    *   빈 이름을 변경하는 것은 아니다. 만약 @Qualifier("mainDiscountPolicy")를 못찾게 된다면
    *   mainDiscountPolicy라는 이름의 스프링 빈을 추가로 찾는다.
    * - @Primary는 우선순위를 지정하는 방법이다. @Autowired 시에 여러 개가 매칭되면 @Primary가
    *   우선권을 가짐 현재는 rateDiscountPolicy에 적용했음.
    * */
    /**
     * 코드에서 자주 사용되는 메인 데이터베이스 커넥션을 획득하는 스프링 빈이 있고, 코드에서 특별한
     * 기능으로 가끔 사용하는 서브 데이터베이스의 커넥션을 획득하는 스프링 빈이 있다고 생각하자.
     * 메인 데이터베이스의 커넥션을 획득하는 스프링 빈은 @Primary를 적용해서 조회하는 곳에서 @Quilifier
     * 지정 없이 편리하게 조회하고, 서브 데이터베이스 커넥션 빈을 획득할 때는 @Qualifier를 지정해서
     * 명시적으로 획득하는 방식으로 사용하면 코드를 깔끔하게 유지할 수 있다. 물론 이때 메인
     * 데이터베이스의 스프링 빈을 등록할 때 @Qualifier를 지정해주는 것은 상관없다.
     */

}
