# ITEM3 . 싱글턴 보증
+ 싱글턴은 하나의 인스턴스만 생성해야 하는 경우를 말함.

ex) 다수의 사용자가 공유하는 1대의 프린터

<img src="https://user-images.githubusercontent.com/26866859/151317045-356646ab-00ff-48eb-af7d-ea3b4f95c65c.png" width="400">


+ 싱글톤 패턴은 객체가 프로그램 내부에서 단 1개만 생성됨 을 보장해야하는 경우이다.
+ 하나의 객체를 공유하며 동시에 접근하는 경우, 싱글톤으로 개발된다.



## 보증방법
### 1. 생성자를 private 으로 감추자.

생성자를 private로 만들면, 호출이 안된다.

### 1-1 private생성자를  public static final 필드를 통해  한번만 생성되게 한다
#### Elvis 클래스
```
// 코드 3-1 public static final 필드 방식의 싱글턴 (23쪽)
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    int cnt=1;
    private Elvis() { cnt++;}
    public void leaveTheBuilding() {
        System.out.println(cnt);
    }
}
```
#### 호출 클래스

```
// 이 메서드는 다른 클래스에 작성
    public static void main(String[] args) {
        Elvis elvis = Elvis.INSTANCE;
        Elvis elvis2 = Elvis.INSTANCE;
        elvis.leaveTheBuilding();
        elvis2.leaveTheBuilding();
    }
```

<img src="https://user-images.githubusercontent.com/26866859/151322283-0aa81748-32e5-4221-9857-e8e6d71de09e.png" width="400">


위와 같이 public static final 필드 방식으로 INSTANCE 변수에 접근하면
접근과 동시에 인스턴스가 생성되고
두번 생성은 이루어지지 않으므로, 싱글톤이 보증된다.


### 1-2 private 생성자를  public static 메소드를 통해 호출되게 하는법.
#### Elvis 클래스
```
// 코드 3-2 정적 팩터리 방식의 싱글턴 (24쪽)
public class Elvis{
    private static final Elvis INSTANCE = new Elvis();
    private int cnt=0;
    private Elvis() {cnt++;}
    public static Elvis getInstance() { return INSTANCE; }

    public void leaveTheBuilding() {
        System.out.println(cnt);
    }
```

```
public static void main(String[] args) {
        Elvis elvis = Elvis.getInstance();
        Elvis elvis2 = Elvis.getInstance();
        elvis.leaveTheBuilding();
        elvis2.leaveTheBuilding();
 }
```
<img src="https://user-images.githubusercontent.com/26866859/151322283-0aa81748-32e5-4221-9857-e8e6d71de09e.png" width="250">

똑같이 private 생성자를 내부에서만 호출하도록 하는데,
변수 접근 뿐만 아니라, 정적팩터리 메소드를 이용해서도 인스턴스를 만들도록 추가해준 것이다.


### 2. 열거 타입 방식의 싱글턴 
#### Elvis 클래스
```
public enum Elvis {
    INSTANCE;

    public void leaveTheBuilding() {
        System.out.println("기다려 자기야, 지금 나갈께!");
    }
```
```
  public static void main(String[] args) {
      Elvis elvis = Elvis.INSTANCE;
      elvis.leaveTheBuilding();
  }
```
원소가 하나뿐인 열거타입이 만들기 가장 쉬운 방법이라 말한다.
이 세 번째 방식을 사용한다면 [직렬화 문제](http://yoonbumtae.com/?p=1097), 리플렉션 공격들도 막을 수 있다.
하지만, Enum클래스는 다른 인터페이스를 상속할 수 없으므로 , 추가적인 인터페이스에 상속이 있다면
이 방법은 사용할 수 없다.

### private 생성자로 싱글톤을 보증하는 경우의 문제점

#### 1. Reflection API를 이용한 호출 
Reflection API 를 이용하면 private 생성자도 호출이 가능하여 여러 인스턴스가 생성되게 할 수 있다.
이를 막기 위해서는 생성자를 수정해 두 번째 객체가 생성되려 할 때 예외를 던지도록 추가적인 방어 코드가 필요하다.

```
private Student(){
  if(Objects.nonNull(INSTANCE)){
    throws new RuntimeException();
  }
}
```

#### 2. [직렬화 문제](http://yoonbumtae.com/?p=1097)
싱글톤 클래스를 직렬화 할 때는 
Serializable을 구현한다고 선언(implements)하는 것 만으로는 부족하다.
그냥 구현한다고 선언만 한 상태에서 직렬화 이후 다시 역징렬화(Deserialization)를 하게 되면 
새로운 인스턴스가 생성되어 버린다. 
그렇기에 이 문제를 해결하기 위해서는 모든 인스턴스 필드를 일시적(transient)이라 선언한 뒤 
readResolve 메소드를 오버라이딩해서 작성해줘야 한다. 

```
private Object readResolve() throws ObjectStreamException{
   return INSTANCE;
}
```




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
