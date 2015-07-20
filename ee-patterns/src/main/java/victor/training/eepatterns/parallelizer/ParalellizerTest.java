package victor.training.eepatterns.parallelizer;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

@WebServlet("/Parallelizer")
public class ParalellizerTest extends HttpServlet {

	@Inject
	private Paralellizer paralellizer;
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		execute20TasksInParallelLastsLessThan5TasksLength();
	}
	@Test
	public void execute20TasksInParallelLastsLessThan5TasksLength() {
		List<String> items = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
				"p", "q", "r", "s", "t", "u", "v", "x", "y", "z");
		long t0 = System.currentTimeMillis();
		paralellizer.doALotOfWork(items);
		assertTrue("Lasted less than 5 seconds", System.currentTimeMillis() - t0 < 5000);
		System.out.println("Test OK");
	}

}
