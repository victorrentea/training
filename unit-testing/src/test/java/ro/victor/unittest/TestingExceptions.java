package ro.victor.unittest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestingExceptions {
    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void throwsForNegativeParam() {
        exception.expectMessage("negativ");
        ClasaDeTestat.cevaGreuCareMaiSiCrapa(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsForParamOver10() {
        ClasaDeTestat.cevaGreuCareMaiSiCrapa(11);
    }

    @Test
    public void okForParam9() {
        ClasaDeTestat.cevaGreuCareMaiSiCrapa(9);
    }

}

class ClasaDeTestat {
    public static void cevaGreuCareMaiSiCrapa(int i) {

        if (i < 0) {
            throw new IllegalArgumentException(
                    "Draga debugere, nu ar trebui ca aceasta metoda sa primeasca " +
                    "vreodata un parametru negatiV");
        }


        if (i > 10) {
            throw new IllegalArgumentException("Draga debugere, nu ar trebui ca aceasta metoda sa primeasca " +
                    "vreodata un parametru mai mare ca 10");
        }
    }
}


class ExceptiaMea extends RuntimeException {
    public enum ErrorCode {
        NEGATIVE_PARAM
    }

    private final ErrorCode code;

    public ExceptiaMea(ErrorCode code) {
        this.code = code;
    }
}