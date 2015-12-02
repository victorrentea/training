package victor.mbean;

public interface HelloMBean {

	int sum(int a, int b);
	
	int getBufferSize();
	void setBufferSize(int size);
	
	void resetCounter();
	void count();
	int getCounter();
	
}
