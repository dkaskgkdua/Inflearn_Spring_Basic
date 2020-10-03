package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 스프링 빈은 간단하게 다음과 같은 라이프사이클을 가진다.
 * "객체 생성" -> "의존관계 주입"
 *
 * 스프링 빈은 객체를 생성하고, 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는
 * 준비가 완료된다. 따라서 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야 한다.
 * 그런데 개발자가 의존관계 주입이 모두 완료된 시점을 어떻게 알 수 있을까?
 * 스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는
 * 다양한 기능을 제공한다. 또한 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다.
 * 따라서 안전하게 종료 작업을 진행할 수 있다.
 *
 * - 스프링 빈의 이벤트 라이프사이클
 * 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 읜존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백
 * -> 스프링 종료
 *
 * - 초기화 콜백 : 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
 * - 소멸전 콜백 : 빈이 소멸되기 직전에 호출출
*/
/**
 * 스프링은 크게 3가지 방법으로 빈 생명주기 콜백을 지원함.
 * - 인터페이스(InitializingBean, DisposalbleBean)
 * - 설정 정보에 초기화 메서드, 종료 메서드 지정
 * - @PostConstruct, @PreDestroy 어노테이션 지원
 * */

public class BeanLifeCycleTest {
    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();

    }

    @Configuration
    static class LifeCycleConfig {
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
