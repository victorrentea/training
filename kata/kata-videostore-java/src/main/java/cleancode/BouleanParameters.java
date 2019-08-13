package cleancode;

import java.util.List;

@SuppressWarnings("ALL")
public class BouleanParameters {

    public static void main(String[] args) {
        // The method is called from various foreign places in the codebase
        bigUglyMethod(1,2,3,4,5);
        bigUglyMethod(1,2,3,4,5);
        bigUglyMethod(1,2,3,4,5);
        bigUglyMethod(1,2,3,4,5);
        bigUglyMethod(1,2,3,4,5);

        // TODO From my use-case, I call it too, to do more within:
        bigUglyMethod(1,2,3,4,5);

    }

    static void bigUglyMethod(int a, int b, int c, int d, int e) {
        // complex logic
        // complex logic
        // complex logic

        // more complex logic
        // more complex logic
        // more complex logic
    }


    // ============== "BOSS" LEVEL: A log harder to break down =================

    static void bossLevel(boolean stuff, boolean fluff, List<Integer> trebi) {
        int i = 0;
        int j = 1;
        // more code
        if (stuff) {
            // more code
            if (fluff) {
                // more code
                for (int treaba:trebi) {
                     i ++;
                    // more code
                    // TODO HERE, when call this method, I want MY own custom code to run here
                    // more code
                }
                // more code
            }
        }
        // more code

    }
}
