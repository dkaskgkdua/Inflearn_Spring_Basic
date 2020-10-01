package hello.core.sigleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *  현재 스프링 없는 순수 DI컨테이너는 고객이 요청 시 마다 객체를 새로 생성함
 *  이는 메모리 낭비가 심하다.
 *  그러므로 객체가 딱 1개만 생성되고 공유하도록 설계하기 위해 싱글톤 패턴을 사용한다.
 */

/**
 * 싱글톤 단점!!
 * - 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
 * - 의존관계상 클라이언트가 구체 클래스에 의존한다. DIP를 위반한다.
 * - 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
 * - 테스트하기 어렵다.
 * - 내부 속성을 변경하거나 초기화 하기 어렵다.
 * - private 생성자로 자식 클래스를 만들기 어렵다.
 * - 결론적으로 유연성이 떨어진다.
 * - 안티패턴으로 불리기도 한다.
 */

/**
 * 싱글톤 방식의 주의점
 * - 무상태(stateless)로 설계해야 한다.
 * - 특정 클라이언트에 의존적인 필드가 있으면 안된다.
 * - 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다.
 * - 가급적 읽기만 가능해야 한다.
 * - 필드대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용해야한다.
 */
public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        //1. 조회: 호출할 때마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        //2. 조회: 호출할 때마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        // memberservice != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);

    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();
        System.out.println("instance1 = " + instance1);
        System.out.println("instance2 = " + instance2);

        assertThat(instance1).isSameAs(instance2);
        // same  == (참조비교)  call by reference
        // equal 내용 자체 비교  call by value
    }
    
    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
//        AppConfig appConfig = new AppConfig();
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        //1. 조회: 호출할 때마다 객체를 생성
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        //2. 조회: 호출할 때마다 객체를 생성
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        // memberservice != memberService2
        assertThat(memberService1).isSameAs(memberService2);

    }
}

