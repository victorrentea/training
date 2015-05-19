package victor.training.jee6.ejb.singleton;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
public class ReadersWritersSingleton {

	final AtomicInteger readerCount = new AtomicInteger(0);
	final AtomicInteger writerCount = new AtomicInteger(0);

	@Lock(LockType.READ)
	public void readerMethod(int threadId) {
		System.out.println("Enter READER " + threadId + ". R:" + readerCount.incrementAndGet() + "/w:"
				+ writerCount.get());
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Exit READER " + threadId + ". R:" + readerCount.decrementAndGet() + "/w:"
				+ writerCount.get());
	}

	@Lock(LockType.WRITE)
	public void writerMethod(int threadId) {
		System.out.println("Entering WRITER " + threadId + ". R:" + readerCount.get() + "/w:"
				+ writerCount.incrementAndGet());
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Exiting WRITER " + threadId + ". R:" + readerCount.get() + "/w:"
				+ writerCount.decrementAndGet());
	}
}
