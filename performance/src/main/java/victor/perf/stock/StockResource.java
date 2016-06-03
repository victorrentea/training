package victor.perf.stock;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import victor.perf.leaks.BigObject200KBApprox;
import victor.perf.stock.api.StockPriceUtils;
import victor.perf.stock.impl.MockStockPriceEntityManagerFactory;
import victor.perf.stock.impl.StockPriceHistoryImpl;

@Path("stock")
public class StockResource {
	
	
	static BigObject200KBApprox[] cache = new BigObject200KBApprox[0];
	
	static AtomicInteger counter = new AtomicInteger(0);

	private static EntityManager em;

	@PostConstruct
	public static void initEM() {
        EntityManagerFactory emf = new MockStockPriceEntityManagerFactory("MockEntityManager");
        em = emf.createEntityManager();
    }
	@PostConstruct
	public void setup() {
		System.out.println("Created resource instance " + hashCode()); 
	}
	
	@GET
	public synchronized String getStocks50Cache(@QueryParam("cacheMB") @DefaultValue("100") int cacheSizeMB) throws ParseException {
		int cacheSize = cacheSizeMB * 1024 / 200; 
		if (cacheSize != cache.length) {
			System.out.println("Change to "  +cacheSize);
			cache = new BigObject200KBApprox[cacheSize];
		}
		int i = counter.incrementAndGet();
		String symbol = StockPriceUtils.makeSymbol(i);
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
		StockPriceHistoryImpl sph = new StockPriceHistoryImpl(symbol, df.parse("01/01/12"), df.parse("12/31/12"), em);
		
		BigObject200KBApprox bigObject = new BigObject200KBApprox();
		cache[i % cache.length] = bigObject;
		bigObject.largeArray[0]=sph.hashCode();
		
		return "Hello";
	}
	
}
