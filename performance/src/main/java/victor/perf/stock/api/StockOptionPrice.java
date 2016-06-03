package victor.perf.stock.api;

import java.math.BigDecimal;
import java.util.Date;

public interface StockOptionPrice {
    public String getSymbol();
    public Date getDate();
    public int getExpirationPeriod();
    public BigDecimal getPrice();
}
