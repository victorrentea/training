package victor.perf.stock.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import victor.perf.stock.api.StockPrice;

public class StockPriceHistoryLogger extends StockPriceHistoryImpl {
    private static final Logger logger = Logger.getAnonymousLogger();
    public StockPriceHistoryLogger(String s, Date startDate,
                                   Date endDate, EntityManager em) {
        super(s, startDate, endDate, em);
    }

    @Override
    public StockPrice getPrice(Date d) {
        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "Get price for {0}: {1}",
                   new Object[]{getSymbol(), d});
        }
        return super.getPrice(d);
    }

    @Override
    public synchronized BigDecimal getHighPrice() {
        BigDecimal bd = super.getHighPrice();
        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "Get High Price for {0}: {1}",
                   new Object[]{getSymbol(), bd});
        }
        return bd;
    }

    @Override
    public synchronized BigDecimal getLowPrice() {
        BigDecimal bd = super.getLowPrice();
        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "Get Low Price for {0}: {1}",
                   new Object[]{getSymbol(), bd});
        }
        return bd;
    }

    @Override
    public synchronized BigDecimal getAveragePrice() {
        BigDecimal bd = super.getAveragePrice();
        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "Get Low Price for {0}: {1}",
                   new Object[]{getSymbol(), bd});
        }
        return bd;
    }

    @Override
    public synchronized BigDecimal getStdDev() {
        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "Get Std Dev for {0}",
                   new Object[]{getSymbol()});
        }
        return BigDecimal.ZERO;
    }

    @Override
    protected void process() {
        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "Process {0}: {1}",
                   new Object[]{getSymbol()});
        }
        super.process();
    }
}
