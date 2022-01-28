package effectivejava.chapter2.item3.field;

// 코드 3-1 public static final 필드 방식의 싱글턴 (23쪽)
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    int cnt=1;
    private Elvis() { cnt++;}
    public void leaveTheBuilding() {
        System.out.println(cnt);
    }

    // 이 메서드는 보통 클래스 바깥(다른 클래스)에 작성해야 한다!
    public static void main(String[] args) {
        Elvis elvis = Elvis.INSTANCE;
        Elvis elvis2 = Elvis.INSTANCE;
        elvis.leaveTheBuilding();
        elvis2.leaveTheBuilding();
    }
}
