package hello.core.sigleton;

public class SingletonService {
    // static 영역에 객체 한개만 올림 그리고 getInstance를 통해 접근 가능하도록 함.
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }
    // private 으로 생성자를 선언해줌으로써 다른 곳에서 생성을 못하게 함.
    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
