package effectivejava.chapter2.item3.staticfactory;

public class TEST {
	public static void main(String[] args) {
		Elvis e1 = Elvis.getInstance();
		Elvis e2 = Elvis.getInstance();
		e1.leaveTheBuilding();
		e2.leaveTheBuilding();
	}		
}
