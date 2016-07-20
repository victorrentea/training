package victor.training.java8.advanced;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.function.Function;

public class ScalaClassSwitch<U> {
	
	private final U object;
	private boolean matched = false;
	private Object result;

	private ScalaClassSwitch(U object) {
		this.object = object;
	}

	private static <U> ScalaClassSwitch<U> on(U object) {
		return new ScalaClassSwitch<U>(object);
	}
	
	@SuppressWarnings("unchecked")
	private <T> ScalaClassSwitch<U> when(Class<T> clazz, Function<T, ?> function) {
		if (!matched && object != null && clazz.isAssignableFrom(object.getClass())) {
			result = function.apply((T) object);
			matched = true;
		}
		return this;
	}

	private Object orDefault(Function<U, ?> defaultFunction) {
		if (!matched) {
			return defaultFunction.apply(object);
		} else {
			return result;
		}
	}

	public static void main(String[] args) {
		OutputStream object = new BufferedOutputStream(new ByteArrayOutputStream());
		
		Object result = ScalaClassSwitch.on(object)
			.when(BufferedOutputStream.class, bos -> "bos")
			.when(FileOutputStream.class, fos -> "fos")
			.orDefault(other -> "other");
		System.out.println(result);
	}
	
	
}
