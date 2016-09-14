package victor.training.java8.cleanlambda;

import java.util.function.Function;

public class Example10 {

	public static void main(String[] args) {
		new Example10().normalizeFunction(null);
	}
	
	private Function<String, String> normalizeFunction2(Function<String, String> dictionaryFunc) {
		return s -> {
			s = s.trim();
			s = s.toUpperCase();
			s = dictionaryFunc.apply(s);
			return s;
		};
	}
	
	private Function<String, String> normalizeFunction(Function<String, String> dictionaryFunc) {
		return Function.<String>identity()
				.andThen(String::trim)
				.andThen(String::toUpperCase)
				.andThen(dictionaryFunc);
	}

	private Function<String, String> createIgnoreFunction() {
		// TODO Auto-generated method stub
		return null;
	}

}
