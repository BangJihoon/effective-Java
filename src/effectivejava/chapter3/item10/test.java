package effectivejava.chapter3.item10;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class test {
    public static void main(String[] args) {
        Set<Point> set = new HashSet<>();
        set.add(new Point(1,1));
        set.add(new Point(1,1));

        System.out.println(set.size());

        for (Point o : set) {
            System.out.println(o.hashCode());
        }
    }
}
