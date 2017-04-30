package caplin.tdd;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolaes on 07/01/2016.
 */
public class FizzBuzz {

		public String generateFizzBuzz() {
				List<String> result = new ArrayList<String>();

				for (int i = 0; i < 3; i++) {
						if ((i + 1) % 3 != 0 ) {
								result.add(i, Integer.toString(i));
						} else {
								result.add(i, "Fizz");
						}

				}
				return StringUtils.join(result, ",");
		}
}
