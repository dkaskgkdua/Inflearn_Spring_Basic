package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;


/**
 * CGLIB이라는라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다.
 * @Scope의 proxyMode = ScopedProxyMode.TARGET_CLASS 를 설정하면 스프링 컨테이너라는
 * CGLIB라는 바이트코드를 조작하는 라이브러리를 사용해서, MYLogger를 상속받은 가짜 프록시
 * 객체를 생성한다.
 * 가짜 프록시 객체는 요칭이 오면 그때 내부에서 진짜 빈을 요청하는 위임 로직이 들어있다.
 * 핵심은 진짜 객체 조회를 꼭 필요한 시점까지 지연처리 한다는 점!
 */
@Component
@Scope(value ="request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "] " + "["+requestURL+"] "+message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create : " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close : " + this);
    }
}
