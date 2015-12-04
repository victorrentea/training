package victor.mockito.ee;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MyServiceTest {

	@Mock
	private MyDao myDao;
	
	private MyService target = new MyService();
	
	@Before
	public void initFixture() {
		target.myDao = myDao;
	}
	
	@Test
//	public void givenAListOfEmployeesInDB_whenIncreaseSallaries_thenSallriesAreDoubled() {
	public void increaseSallariesOk() {
		
		Employee e = new Employee();
		e.setSallary(1000);
		when(myDao.getAllEmployees()).thenReturn(Collections.singletonList(e));
		
		target.increaseSallaries();
		
		assertEquals(2000, e.getSallary());
	}
	
}
