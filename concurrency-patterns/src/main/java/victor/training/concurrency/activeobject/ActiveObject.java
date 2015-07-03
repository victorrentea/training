package victor.training.concurrency.activeobject;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveObject {
	private static final Logger log = LoggerFactory.getLogger(ActiveObject.class);
	
	private static class MyFuture<T> implements Future<T> {
		private static final Logger log = LoggerFactory.getLogger(ActiveObject.MyFuture.class);
		private boolean cancelled;
		private T result;
		private Exception resultException;
		
		private final AsyncMethodInvocation<T> methodInvocation;
		
		public MyFuture(AsyncMethodInvocation<T> methodInvocation) {
			this.methodInvocation = methodInvocation;
		}

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			synchronized (methodInvocation) {
				if (isDone() && !isCancelled()) {
					return false;
				}
				if (mayInterruptIfRunning) {
					methodInvocation.interruptExecution();
				}
				cancelled = true;
				return true;
			}
		}

		@Override
		public T get() throws InterruptedException, ExecutionException {
			synchronized (methodInvocation) {
				log.debug("* Waiting on method");
				if (!isDone()) {
					methodInvocation.wait();
				}
				log.debug("* Waked up after wait on method");
				return doGetResult();
			}
		}

		@Override
		public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
			synchronized (methodInvocation) {
				if (!isDone()) {
					methodInvocation.wait(unit.convert(timeout, TimeUnit.MILLISECONDS));
				}
				log.debug("* Wait timeout expired, isDone = " + isDone());
				if (!isDone()) {
					throw new TimeoutException();
				}
				return doGetResult();
			}
		}
		
		private T doGetResult() throws ExecutionException {
			if (resultException != null) {
				throw new ExecutionException(resultException);
			} else if (result != null) {
				return result;
			} else {
				throw new IllegalStateException("Not executed yet!");
			}
		}
		
		
		// called under Method monitor
		public void setResult(T result) {
			this.result = result;
		}
		
		// called under Method monitor
		public void setResultException(Exception resultException) {
			this.resultException = resultException;
		}

		@Override
		public boolean isCancelled() {
			return cancelled;
		}

		// called under Method monitor
		@Override
		public boolean isDone() {
			return result != null || resultException != null;
		}
		
	}

	private static abstract class AsyncMethodInvocation<T> implements Runnable {
		private static final Logger log = LoggerFactory.getLogger(ActiveObject.AsyncMethodInvocation.class);
		
		private MyFuture<T> futureResponse = new MyFuture<>(this);
		
		public MyFuture<T> getFutureResponse() {
			return futureResponse;
		}
		
		public synchronized void interruptExecution() {
			// TODO interrupt
		}
		
		public abstract T execute() throws Exception;

		public synchronized boolean canExecute(ActiveObject target) {
			return !futureResponse.isCancelled() && guard();
		}
		
		public boolean guard() {
			return true;
		}

		@Override
		public void run() {
			T result = null;
			Exception exception = null;
			try {
				result = execute();
				log.debug("Execution completed with result: " + result);
			} catch (Exception e) {
				log.debug("Execution completed with exception: " + e);
				exception = e;
			}
			
			synchronized (this) {
				log.debug("* Set execution results in Future");
				if (result != null) {
					futureResponse.setResult(result);
				} else {
					futureResponse.setResultException(exception);
				}
				log.debug("* Notify all futures.get()");
				notifyAll();			
			}
		}
	}

	private BlockingQueue<AsyncMethodInvocation<?>> queue = new LinkedBlockingQueue<>();

	private boolean running = true;
	public ActiveObject() {
		new Thread() {
			public void run() {
				while (running) {
					try {
						AsyncMethodInvocation<?> invocation = queue.take(); 
						if (invocation.canExecute(ActiveObject.this)) {
							invocation.run();
						} else {
							queue.offer(invocation);
						}
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}.start();
		log.debug("Started thread");
	}
	
	public void stopActiveObject() {
		log.debug("Stopping thread");
		running = false;
	}
	
	
	public <T> Future<T> queueAndReturn(AsyncMethodInvocation<T> invocation) {
		queue.offer(invocation);
		return invocation.getFutureResponse();
	}
	
	public Future<Integer> delayedSum(final int a, final int b) {
		return queueAndReturn(new AsyncMethodInvocation<Integer>() {
			@Override
			public Integer execute() throws Exception {
				Thread.sleep(1000);
				return a + b;
			}
		});
	}
	
	public Future<Void> delayedException() {
		return queueAndReturn(new AsyncMethodInvocation<Void>() {
			@Override
			public Void execute() throws Exception {
				Thread.sleep(1000);
				throw new IOException("Mock exception");
			}
		});
	}
	
	private boolean canWrite = true;
	
	public Future<Void> suspendWrites() {
		return queueAndReturn(new AsyncMethodInvocation<Void>() {
			@Override
			public Void execute() throws Exception {
				canWrite = false;
				return null;
			}
		});
	}
	
	public Future<Void> resumeWrites() {
		return queueAndReturn(new AsyncMethodInvocation<Void>() {
			@Override
			public Void execute() throws Exception {
				canWrite = true;
				return null;
			}
		});
	}
	
	public Future<String> write() {
		return queueAndReturn(new AsyncMethodInvocation<String>() {
			@Override
			public boolean guard() {
				return canWrite;
			}
			@Override
			public String execute() throws Exception {
				Thread.sleep(10);
				return "result";
			}
		});
	}
	
	
	
}
