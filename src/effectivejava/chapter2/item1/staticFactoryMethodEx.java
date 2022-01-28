package effectivejava.chapter2.item1;

public class staticFactoryMethodEx {
	public static void main(String[] args) {
		/*
		 장점 1 : 정적 팩토리 메소드는 이름을 가진다 								-> 사용시 메소드로 직관적인 이해 가능
		 장점 2 : 정적 팩토리 메소드를 쓰면 호출할 때마다 새로운 객체를 생성할 필요가 없다 	-> 객체생성없이도 바로 사용가능
		 장점 3 : 반환값 자료형의 하위 자료형 객체를 반환할 수 있다 					-> List - ArrayList, Map -> HashMap 이런식으로... 보통해당안됨
		 장점 4 : 입력 매개변수에 따라 다른 클래스의 객체를 반환할 수 있다.				-> 반환값에 자유도가 높다
		 장점 5 : 정적 팩토리 메소드를 작성하는 시점에는 반환할 객체의 클래스가 없어도 된다.	-> ex) JDBC Connection, Driver 등 반환 객체가 없는 서비스 인터페이스..
		 
		 단점
		 첫 번째, 상속을 할 땐 public or protected 생성자가 필요하기에 정적 팩토리 메서드만 사용하면 하위 클래스를 만들 수 없다. 
		  	-> interface에 정의한 정적 메소드를 상속해서 오버라이딩 할 수 없다는 의미
		 두 번째, 정적 팩토리 메소드는 프로그래머가 찾기 힘들다.
			-> API나 IDE에 사용설명이 잘 되어있지만, 인스턴스화 하기위한 방법을 직접 찾아야함
		 */		
	}
}
