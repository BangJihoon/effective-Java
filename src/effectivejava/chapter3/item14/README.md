# item 14. Comparable을 고려하라

객체 정렬 사용 인터페이스 :  Comparator, Comparable

Comparable 인터페이스는 compareTo라는 하나의 메서드를 정의하는데

자바에서 제공하는 모든 값 클래스와 열거 타입이 Comparable을 구현했다. 

알파벳, 숫자, 연대, 번호등 순서가 있는 값 클래스를 만들 때 Comparable를 사용하자.

<br/>

### 구현 규약
equals 규약과의 비슷하다.
+ 해당 객체와 주어진(매개변수) **객체의 순서**를 비교한다.
  + ⇒ 해당 객체가 더 크다면 양수를 반환한다. 
  + ⇒ 해당 객체가 더 작다면 음수를 반환한다.
  + ⇒ 해당객체와 주어진 객체가 같을경우 0을 반환한다. 
  + ⇒ 해당 객체와 비교할 수 없는 타입의 객체가 전달되면 ClassCastException이 발생한다.
+ **대칭성**을 보장해야 한다.
  + ⇒ 모든 x, y클래스에 대해서 sgn(x.compareTo(y) == -sgn(y.compareTo(x))여야 한다.
  + ⇒ x.compareTo(y)는 y.compareTo(x)가 예외를 던질 때에 한해서 예외가 발생해야 한다.
+ **추이성**을 보장해야 한다.
  + ⇒ x.compareTo(y)가 양수이고 y.compareTo(z)도 양수라면, x.compareTo(z)도 양수여야한다.
  + (x > y && y > z 이면 x > z여야 한다.)
  + x.compareTo(y) == 0 일 때 sgn(x.compareTo(z)) == sgn(y.compareTo(z))이어야 한다.
  + x.compareTo(y) == 0 일 때 x.equals(y)어야 한다.
  + ⇒ 필수는 아니지만 지키는게 좋다. 

<br/>

#### equals 규약과의 차이점
+ 모든 객체에 대해 전역 동치관계를 부여하는 equals와 다르게 compareTo는 타입이 다른 객체를 신경쓰지 않아도 된다.
+ 다를 경우 정렬할 수 없고, 단순히 ClassCastException을 던지면 그만이다. 

<br/>

### 작성 요령
Comparable은 타입을 인수로 받는 제네릭 인터페이스라서 <br/> compareTo 메서드의 인수 타입은 컴파일 타임에 정해진다.  <br/> 
Comparable을 구현하지 않은 필드나 표준이 아닌 순서로 비교해야 할 경우  <br/> Comparator를 쓰면 된다. 

<br/>

### 기본적인 사용 사례 
#### 객체 참조 필드가 하나뿐인 비교자
```java
public final class CaseInsensitiveString implements Comparable<CaseInsensitiveString>{
  private final String str;
  public CaseInsensitiveString(String str) {
    this.str = Objects.requireNonNull(str);
}
  @Override
  public int compareTo(CaseInsensitiveString cis) {
    return String.CASE_INSENSITIVE_ORDER.compare(str, cis.str); //CASE_INSENSITIVE_ORDER에 compare는 대소문자 구분하지 않고 비교
  }
}
```

#### 기본타입(Primitive type)은 정적 메서드 compare를 이용하자. 
```Java
public class PhoneNumber implements Comparable<PhoneNumber>{
  private final int prefix;
  private final int middle;
  private final int suffix;
  @Override
  public int compareTo(PhoneNumber pn) {
    int result = Integer.compare(prefix, pn.prefix);
    if (result == 0) {
      result = Integer.compare(middle, pn.middle);
    if(result == 0)
      result = Integer.compare(suffix, pn.suffix);
    }
    return result;
  }
}
```

자바 8 부터는 Comparator 인터페이스가 비교자 생성 메서드를 이용해  <br/>  메서드 연쇄 방식으로 비교자를 생성할 수 있게 되었다.  <br/> 
방식은 간결하지만 성능은 떨어진다.  <br/> 

```java
public static final Comparator<PhoneNumber> COMPARATOR = comparingInt(PhoneNumber::getPrefix)
                                                            .thenComparingInt(PhoneNumber::getMiddle)
                                                            .thenComparingInt(PhoneNumber::getSuffix);
  @Override
  public int compareTo(PhoneNumber pn) {
    return COMPARATOR.compare(this, pn);
  }
```
  + ⇒ 이 코드는 클래스 초기화시 비교자 생성 메서드 2개를 이용해 비교자를 생성한다. 
  + ⇒ 최초 comparingInt에서는 객체 참조를 int 타입 키에 매핑하는 키 추출 함수(key extractor function)을 인수로 받아 해당 키를 기준으로 순서를 정하는 비교자를 반환하는 정적 메서드다.
  + ⇒ 위 예제와 에서는 Method Reference를 사용을 편하게 했지만, 직접 필드 접근으로 꺼내거나 람다식으로 꺼낼 경우 인자의 타입을 명시적으로 작성해줘야 한다. 
  + comparingInt((PhoneNumber pn) → pn.prefix)
  + 자바에서 여기까지 타입추론을 제대로 하지 못하기 때문에 명시해 줄 필요가 있다. 
  + 물론, 두 번째 호출부터는 타입추론이 제대로 동작한다. 
  + 정적 메서드 혹은 비교자 생성 메서드를 활용하자.
  + 객체간 순서를 정한다고 해시코드를 기준으로 정렬하기도하는데 
  + 단순히 첫 번째 값이 크면 양수, 같으면 0, 첫 번째 값이 작으면 음수를 반환한다는 것만 생각해서 다음과 같이 작성을해선 안된다.

#### 추이성을 위배하는 비교자
```java
static Comparator<Object> hashCodeOrder = new Comparator<>() {
  public int compare(Object 01, Object 02) {
    return ol.hashCode() - o2.hashCode();
  }
}
```

이런 방식은 얼핏보면 문제 없을 것 같지만 정수 오버플로 혹은  <br/> IEEE754 부동소수점 계산 방식에 따른 오류를 낼 수 있다.  <br/> 
게다가 속도가 엄청 빠르지도 않다.  <br/> 
대신, 다음처럼 정적 compare메서드 혹은 비교자 생성 메서드를 활용해보자.  <br/> 

```java
static Comparator<Object hashCodeOrder = new Comparator<>(){
  public int compare(Object o1, Object 02){
    return Integer.compare(01.hashCode(), o2.hashCode());
  }
}
```

#### 정적 compare 메서드를 활용한 비교자

```java
static Comparator<Object> hashCodeOrder =
Comparator.comparingInt(Object::hashCode);
```


#### 정리
```
순서를 고려해야하는 값 클래스는 Comparable인터페이스를 꼭 구현하면 좋다. 
 
compareTo 메서드에서는 < , >같은 연산자는 쓰지 않아야 한다.

박싱된 기본 타입 클래스가 제공하는 정적 compare 메서드나 Comparator 인터페이스가 제공하는 비교자 생성 메서드를 활용하자. 
```
