package victor.training.eepatterns;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.eepatterns.parallelizer.Paralellizer;


@RunWith(Arquillian.class)
public class ParalellizerTest extends AbstractArquillianTest {

	@Inject
	private Paralellizer paralellizer;
	
	@Test
	public void execute20TasksInParallelLastsLessThan5TasksLength() {
		List<String> items = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
				"p", "q", "r", "s", "t", "u", "v", "x", "y", "z");
		long t0 = System.currentTimeMillis();
		paralellizer.doALotOfWork(items);
		assertTrue("Lasted less than 5 seconds", System.currentTimeMillis() - t0 < 5000);
	}

}
