# ITEM4 . 인스턴스 방지는 private 생성자로

```
package effectivejava.chapter2.item4;

// 코드 4-1 인스턴스를 만들 수 없는 유틸리티 클래스 (26~27쪽)
public class UtilityClass {
    // 기본 생성자가 만들어지는 것을 막는다(인스턴스화 방지용).
    private UtilityClass() {
        throw new AssertionError();
    }

    // 나머지 코드는 생략
}
```
이렇게 private에 익셉션까지 던져주도록 하면, 
어떠한 경우에서도 인스턴스가 생성되지 않는다.
이러면 상속 또한 불가하다.
