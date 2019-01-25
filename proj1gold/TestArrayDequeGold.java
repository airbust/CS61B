import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void addRemoveTest() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<Integer>();

        String message = "";
        for (int i = 0; i < 10; i++) {
            sad.addLast(i);
            ads.addLast(i);
            message += "addLast(" + i + ")\naddLast(" + i + ")\n";
        }

        for (int i = 0; i < 5; i++) {
            message += "removeLast()\n";
            assertEquals(message, ads.removeLast(), sad.removeLast());
        }
    }

}
