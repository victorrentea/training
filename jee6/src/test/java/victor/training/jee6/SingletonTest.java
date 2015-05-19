package victor.training.jee6;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.jee6.ejb.singleton.ReadersWritersSingleton;
import victor.training.jee6.ejb.singleton.SynchronizedSingleton;

@RunWith(Arquillian.class)
public class SingletonTest extends AbstractArquillianTest {
	@Inject
	private ReadersWritersSingleton readersWritersSingleton;

	@Inject
	private SynchronizedSingleton synchronizedSingleton;

	@Test
	public void playWithSynchronizedMethod() throws Throwable {
		final AtomicInteger threadId = new AtomicInteger(1);
		executeInParallel(2, 2, new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				synchronizedSingleton.synchronizedMethod(threadId.getAndIncrement());
				return null;
			}
		});
	}

	@Test
	public void playWithReadersWriters() throws Throwable {
		final AtomicInteger readerId = new AtomicInteger(1);
		final AtomicInteger writerId = new AtomicInteger(1);
		executeInParallel(10, 2, new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				if (Math.random() > 0.3) {
					readersWritersSingleton.readerMethod(readerId.getAndIncrement());
				} else {
					readersWritersSingleton.writerMethod(writerId.getAndIncrement());
				}
				return null;
			}
		});
	}
}
