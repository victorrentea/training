package victor.perf.stock;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import victor.perf.stock.api.StockPriceHistory;
import victor.perf.stock.api.StockPriceUtils;
import victor.perf.stock.impl.MockStockPriceEntityManagerFactory;
import victor.perf.stock.impl.StockPriceHistoryImpl;
import victor.perf.stock.impl.StockPriceHistoryLogger;

public class StockPriceHistoryBatcher {
    private static final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
    private static int numStocks;
    private static int mode;


    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static void initEM() {
        String s = System.getProperty("MockEntityManager", "MockEntityManager");
        if (s != null) {
            emf = new MockStockPriceEntityManagerFactory(s);
        } else {
            emf = Persistence.createEntityManagerFactory("StockPU");
        }
        em = emf.createEntityManager();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
//    	System.out.println(Runtime.getRuntime().freeMemory());
//    	long t0=System.currentTimeMillis();
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
        Date startDate;
        Date endDate;
        int save = 100;

        numStocks = (args.length < 1) ? 10000 : Integer.parseInt(args[0]);
        if (args.length < 2) {
            startDate = df.parse("01/01/12");
            endDate = df.parse("12/31/12");
        } else {
            startDate = df.parse(args[1]);
            endDate = df.parse(args[2]);
        }
        if (args.length < 3) {
            mode = 0;
        } 
        else {
            mode = Integer.parseInt(args[3]);
        }
        if (args.length > 4) {
            save = Integer.parseInt(args[4]);
        }
//        System.out.println("Num stocks " + numStocks + " " + startDate + " " + endDate);

        Random rand = new Random();
        initEM();
        StockPriceHistory[] saved = new StockPriceHistory[save];
        for (int i = 0; i < numStocks; i++) {
            String symbol = StockPriceUtils.makeSymbol(i);
            StockPriceHistory sph;
            if (mode == 0) {
                sph = new StockPriceHistoryImpl(symbol, startDate, endDate, em);
            } else {
                sph = new StockPriceHistoryLogger(symbol, startDate,
                              endDate, em);
            }
//            System.out.println("For " + sph.getSymbol()
//                + ": High " + nf.format(sph.getHighPrice())
//                + ", Low " + nf.format(sph.getLowPrice())
//                + ", Standard Deviation: " + sph.getStdDev().doubleValue());
            if (save > 0) {
//                saved[i % save] = sph;
                saved[rand.nextInt(save)] = sph;
            }
        }
//        System.out.println("Total time: " + (System.currentTimeMillis() - t0) + " ms");
    }
}
