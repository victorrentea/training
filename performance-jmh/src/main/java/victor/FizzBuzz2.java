package victor;

import java.util.ArrayList;
import java.util.List;

class FizzBuzz2 {

	public static List<String> run() {
		List<String> results = new ArrayList<>();
		for (int i = 1; i < 100; i++) {
			String temp = Integer.toString(i);
			if (i % 15 == 0)
				temp = "fizz fuzz";
			else if (i % 5 == 0)
				temp = "fuzz";
			else if (i % 3 == 0)
				temp = "fizz";
			results.add(temp);
		}
		return results;
	}

}