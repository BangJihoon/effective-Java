`java.lang` 

|__ `Object` 클래스 

|__ `toString` Method

📌  모든 Object toString 을 재정의하자. 

- print method = toString() method invoked

```java
public String toString() {
  return getClass().getName()
					+"@"+Integer.toHexString(hashCode());
}
```

- Object 클래스에서 반환 ⇒ `클래스_이름@16진수로_표현한_해시코드`

```java
{Jenny=PhoneNumber@addbb} 
{Jenney=707-867-5308} -> 가독성 좋음.
```

- toString() 일반 규약
    - 간결하면서 사람이 읽기 쉬운 형태의 유익한 정보 반환
    - 모든 하위 클래스에서 이 메서드를 재정의
    - 규악 잘 이용시, 디버깅이 쉬움.
- 호출하지 않아도 쓰임
    - println() / prinf() / 문자열 연결 연산자(+) → 디버거 객체 출력에 자동 쓰임
    

📌 toString() 은 객체가 가진 주요 정보 모두를 반환하는것이 좋음. 

- toString 구현할때, 반환값 특정 포맷으로 문서화 가능
    - CSV 파일
    - 정적 팩터리/ 생성자 함께 제공 가능
    - ex. `BigInteger`, `BigDecimal`
    - 단점 : 포맷을 바꾸게 되면 사용하던 코드들의 데이터가 망가짐.
        - 포맷 이용 X → 포맷 개선 및 유연성
- toString 제공 필요 X
    - 정적 유틸리티
    - 열거 타입

⭐ 하위 클래스들이 공유해야하는 추상화 클래스는 재정의 필수 ! 

- 컬렉션 구현체는 추상 클래스 toStirng 메서드 상속

- AutoValue 프레임워크  → toString 생성
    - 클래스의 의미 파악은 어려움.
    - 자동 생성 되어 있는 toString 은 객체의 값에 관해 아무것도 알려주지 않는 Object 의 toString 보다는 유용
