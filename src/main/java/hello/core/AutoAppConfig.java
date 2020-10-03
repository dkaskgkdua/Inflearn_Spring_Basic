package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// 다른 예제의 Configuration 정보들이 있기 때문에 설정정보충돌 방지를 위해 제외시킴
@ComponentScan(
        // basePackages 가 없다면 현재 AutoAppConfig 클래스 경로를 기준으로 하위 클래스를 다 찾음
        // 영한님의 추천 : 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것
        //               최근 스프링 부트도 이 방법을 기본으로 제공한다.
        basePackages = "hello.core",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}
