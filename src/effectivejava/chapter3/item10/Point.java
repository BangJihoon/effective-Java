package effectivejava.chapter3.item10;

// 단순한 불변 2차원 정수 점(point) 클래스 (56쪽)
public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 중복 검사 용도
    @Override public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        Point p = (Point)o;
        return p.x == x && p.y == y;
    }


    @Override public int hashCode()  { // 버켓을 찾는 용도
        return 31;
    }

}
