package victor.training.java8.voxxed.more.patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ChainOfResponsiblity {

	public static class Strategies {
		public static Optional<Integer> parseInteger(String s) {
			try {
				return Optional.of(Integer.parseInt(s));
			} catch (NumberFormatException e) {
				return Optional.empty();
			}
		}
		
		public static Optional<Double> parseDouble(String s) {
			try {
				return Optional.of(Double.parseDouble(s));
			} catch (NumberFormatException e) {
				return Optional.empty();
			}
		}
		
		public static Optional<Date> parseDate(String s) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(s);
				if (sdf.format(date).equals(s)) {
					return Optional.of(date);
				} else {
					return Optional.empty();
				}
			} catch (ParseException e) {
				return Optional.empty();
			}
		}
	}
	

	public static void main(String[] args) {
		System.out.println(parse("1234.2").orElseThrow(RuntimeException::new));
		System.out.println(parse("1234").orElseThrow(RuntimeException::new));
		System.out.println(parse("2012-02-02").orElseThrow(RuntimeException::new));
		System.out.println(parse("1234,2").orElseThrow(RuntimeException::new));
	}


	private static Optional<?> parse(String x) {
		List<Function<String, Optional<?>>> strategies = Arrays.<Function<String, Optional<?>>>asList(
				Strategies::parseInteger, 
				Strategies::parseDate, 
				Strategies::parseDouble);
		
		return strategies.stream()
			.map(f -> f.apply(x))
			.filter(Optional::isPresent)
			.findFirst()
			.flatMap(Function.identity());
	}
}
