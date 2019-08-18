package victor.training.concurrency;

public class StringPool {
    public static void main(String[] args) {

        System.out.println("a" == "a");
        int v = 1;
        String a1 = "a" + v;
        a1 =a1.intern(); // nu -l folosim la servici.
        System.out.println("a1" == a1);

    }
}
