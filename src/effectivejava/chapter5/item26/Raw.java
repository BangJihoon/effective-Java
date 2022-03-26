package effectivejava.chapter5.item26;
import java.util.*;

// 코드 26-4 런타임에 실패한다. - unsafeAdd 메서드가 로 타입(List)을 사용 (156-157쪽)
public class Raw {
    public static void main(String[] args) {
        List rawList = new ArrayList<String>(); // 런타임에는 아무런 타입이 남지 않기때문에 컴파일 성공
        List<?> wildList = new ArrayList<String>(); // 컴파일 성공
        rawList.add("redboy"); // 잘 동작한다.

    }

    private static void unsafeAdd(List list, Object o) {
        list.add(o);
    }
}

