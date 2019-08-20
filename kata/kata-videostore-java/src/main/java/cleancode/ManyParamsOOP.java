package cleancode;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class ManyParamsOOP {
    public static void main(String[] args) {

    }

    @Inject // pretend
    private Validator validator = new Validator();

    public void bizLogic() {
        List<String> errors = new ArrayList<>();
        validator.m1("a",1, errors);
        validator.m2("b",1, errors);
        validator.m3("file.txt", 1L,"ref", errors);
        validator.m4("a", 1L,5L, "g", errors);
        validator.m5(1, errors);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString());
        }
    }
}
class Validator {

    public void m1(String a, int b, List<String> errors) {
        // stuff
    }
    public void m2(String s, int c, List<String> errors) {
        // stuff
    }
    public void m3(String fileName, long versionId, String reference, List<String> errors) {
        // stuff
    }
    public void m4(String a, long listId, long recordId, String g, List<String> errors) {
        // stuff
    }
    public void m5(int b, List<String> errors) {
        // stuff
    }
}
