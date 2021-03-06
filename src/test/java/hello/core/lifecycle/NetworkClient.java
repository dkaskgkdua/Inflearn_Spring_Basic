package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient
        //implements InitializingBean, DisposableBean
                            {
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);

    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }
    
    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("서비스 종료 : " + url);
    }

    /*
     * "초기화, 소멸 인터페이스의 단점"
     * 이 인터페이스는 스프링 전용 인터페이스이다. 해당 코드가 스프링 전용 인터페이스에 의존함
     * 초기화, 소멸 메서드의 이름을 변경할 수 없다.
     * 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
     */

    /*
       @PostContruct, @PreDestroy
       스프링에서 가장 권장하는 방법이며 어노테이션 하나만 붙이면 됨.
       유일한 단점은 외부 라이브러리에서 적용하지 못한다는 점인데 이는
       @Bean(init ~ 을 사용하자)
    * */
    @PostConstruct
    public void init(){
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }
    @PreDestroy
    public void close(){
        System.out.println("NetworkClient.close");
        disconnect();
    }

    /*
    // 의존관계 주입이 끝나면
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }
    // 종료시
    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
    */

}
