package victor.training.concurrency;

public class OddEvenIncrementer {
	static int n = 0; 
	
	static Object even = new Object();
	static Object odd = new Object();
	
	static class OddIncrementer extends Thread {
		@Override
		public void run() {
			while(n<20) {
				ConcurrencyUtil.log("Testing "+ n);
				synchronized(OddEvenIncrementer.class) {
					synchronized(odd) {
						while (n % 2 == 0) {
							try {
								ConcurrencyUtil.log("Waiting to become odd");
								odd.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						// n e odd SIGUR
						ConcurrencyUtil.log("Incrementing "+ n);
						n++;
						even.notifyAll(); // a devenit odd
					}
				}
				ConcurrencyUtil.sleep2(1);
			}
		}
	}
	
	static class EvenIncrementer extends Thread {
		@Override
		public void run() {
			while(n<20) {
				ConcurrencyUtil.log("Testing "+ n);
				synchronized(even) {
					synchronized(odd) {
						while (n % 2 == 1) {
							try {
								ConcurrencyUtil.log("Waiting to become even");
								even.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						// n e even SIGUR
						ConcurrencyUtil.log("Incrementing "+ n);
						n++;
						odd.notifyAll(); // a devenit odd
					}
				}
				ConcurrencyUtil.sleep2(1);
			}
		}
	}
	
	public static void main(String[] args) {
		new OddIncrementer().start();
		new EvenIncrementer().start();
	}

}
