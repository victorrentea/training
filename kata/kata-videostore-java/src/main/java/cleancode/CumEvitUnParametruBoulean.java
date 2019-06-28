package cleancode;

import java.util.List;

public class CumEvitUnParametruBoulean {

    public static void main(String[] args) {
        metodaMareSiImputita(1,2,3,4,5, false);
        metodaMareSiImputita(1,2,3,4,5, false);
        metodaMareSiImputita(1,2,3,4,5, false);
        metodaMareSiImputita(1,2,3,4,5, false);
        metodaMareSiImputita(1,2,3,4,5, false);

        // de pe UC asta o chem eu :
        metodaMareSiImputita(1,2,3,4,5, true);

    }

    static void metodaMareSiImputita(int a, int b, int c, int d, int e) {
        multaLogicaUnu(a,b,c);

        multaLogicaDoi(c,d,e);

    }
    static void metodaMareSiImputitaVictor(int a, int b, int c, int d, int e) {
        multaLogicaUnu(a,b,c);

        //AICI vreau sa fac ceva in plus atunci cand chem eu functia asta - o line

        multaLogicaDoi(c,d,e);

    }

    private static void multaLogicaDoi(int c, int d, int e) {
        // si mai multa logica
    }

    private static void multaLogicaUnu(int a, int b, int c) {
        // multa logica
    }


    // ============== "BOSS" LEVEL =================

    static void metodaNaspaRau(boolean ceva, boolean altceva, List<Integer> trebi) {
        //asa-i mult mai greu de spart

        int i = 0;
        int j = 1;
        // cod mult
        if (ceva) {
            // cod mult
            if (altceva) {
                // cod mult
                for (int treaba:trebi) {
                     i ++;
                    // cod mult
                    // AICI, vreau SI codul meu ?!!! "Ai de capul meu"
                    // cod mult
                }
                // cod mult
            }
        }
        // cod mult

    }
}
