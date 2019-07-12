package ro.victor.unittest.db;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import ro.victor.unittest.db.prod.NotificationRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestExecutionListeners({ 
		DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public class DemoDBUnitTest {

	@Autowired
	private NotificationRepo notificationRepo;

	@ExpectedDatabase(value = "expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@Test
	public void notificationTextIsExtractedAfterPersit() {
		notificationRepo.insertNotification("aaa");
	}
	
	
	static class Garment {
		
	}
	
	static class GarmentPD {
		Garment load(long id) {
			throw new IllegalArgumentException();
		}
		
		void insert(Garment g) {
			throw new IllegalArgumentException();
		}
	}
	
	static void codProductie(GarmentPD pd) {
		Garment g= new Garment();
//		pd.insert(g);
	}
	
	
	@Test
	public void codProductieInsereazaGarment() {
		GarmentPD mock = Mockito.mock(GarmentPD.class);
		codProductie(mock);
		verify(mock).insert(any());
	}
	

}
