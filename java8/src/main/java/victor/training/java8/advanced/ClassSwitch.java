package victor.training.java8.advanced;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

public class ClassSwitch<U> {
	
	private final U object;
	private boolean matched = false;

	private ClassSwitch(U object) {
		this.object = object;
	}

	private static <U> ClassSwitch<U> on(U object) {
		return new ClassSwitch<U>(object);
	}
	
	private void orDefault(Consumer<U> defaultConsumer) {
		if (!matched) {
			defaultConsumer.accept(object);
		}
	}

	private <T> Acceptor<T> when(Class<T> clazz) {
		return new Acceptor<T>(clazz);
	}

	private class Acceptor<T> {
		private Class<T> clazz;
		public Acceptor(Class<T> clazz) {
			this.clazz = clazz;
		}
		
		@SuppressWarnings("unchecked")
		public ClassSwitch<U> then(Consumer<T> consumer) {
			if (ClassSwitch.this.object != null &&
				clazz.isAssignableFrom(ClassSwitch.this.object.getClass())) {
				consumer.accept((T) ClassSwitch.this.object);
				ClassSwitch.this.matched = true;
			}
			return ClassSwitch.this;
		}
		
	}
	
	public static void main(String[] args) {
		OutputStream object = new ByteArrayOutputStream();
		ClassSwitch.on(object)
			.when(BufferedOutputStream.class).then(bos -> {System.out.println("bos");})
			.when(FileOutputStream.class).then(fos -> {System.out.println("fos");})
			.orDefault(other -> {System.out.println("other");});
	}
	
	
}
