package effectivejava.chapter2.item3.staticfactory;

// 코드 3-2 정적 팩터리 방식의 싱글턴 (24쪽)
public class Elvis{
    private static final Elvis INSTANCE = new Elvis();
    private int cnt=0;
    private Elvis() {cnt++;}
    public static Elvis getInstance() { return INSTANCE; }

    public void leaveTheBuilding() {
        System.out.println(cnt);
    }

    // 이 메서드는 보통 클래스 바깥(다른 클래스)에 작성해야 한다!
    public static void main(String[] args) {
        Elvis elvis = Elvis.getInstance();
        Elvis elvis2 = new Elvis();
        elvis2.leaveTheBuilding();
    }
}
