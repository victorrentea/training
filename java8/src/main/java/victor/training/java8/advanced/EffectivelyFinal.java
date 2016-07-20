package victor.training.java8.advanced;

public class EffectivelyFinal {

	public static void main(String[] args) {
		int a = 1;
		
		Runnable run = () -> {
			System.out.println(a);
//			a ++; // ERROR
		};
	}
}

