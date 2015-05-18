package victor.training.oo.behavioral.iterator;

import java.util.Iterator;

public class MyArrayIterator implements Iterator<String> {
	private int index;

	private final String[] array;

	public MyArrayIterator(String[] array) {
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return index < array.length;
	}

	@Override
	public String next() {
		return array[index++];
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();

	}

}
