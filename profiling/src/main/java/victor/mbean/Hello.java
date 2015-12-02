package victor.mbean;

public class Hello implements HelloMBean {
	private int bufferSize = 100* 1024;
	private int counter = 0;

	@Override
	public int sum(int a, int b) {
		return a + b;
	}

	@Override
	public int getBufferSize() {
		return bufferSize;
	}

	@Override
	public void setBufferSize(int size) {
		bufferSize = size;
	}

	@Override
	public void resetCounter() {
		counter = 0;
	}

	@Override
	public void count() {
		counter ++;
		
	}

	@Override
	public int getCounter() {
		return counter;
	}

}
