import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void addRemoveTest() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<Integer>();

        for (int i = 0; i < 10; i++) {
            int tmp = StdRandom.uniform(1000);
            int tmp1 = StdRandom.uniform(100);
            sad.addLast(tmp);
            ads.addLast(tmp);
            sad.addLast(tmp1);
            ads.addLast(tmp1);

            String message = "addLast(" + tmp + ")\naddLast(" + tmp1 + ")\nremoveFirst()\n";
            assertEquals(message, ads.removeFirst(), sad.removeFirst());
        }

    }

}
