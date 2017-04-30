package caplin.tdd;

/**
 * Created by nicolaes on 07/01/2016.
 */
public class BankAccount {
		private int balance = 0;

		public int getBalance() {
				return balance;
		}

		public void credit(int transferAmount) {
				balance += transferAmount;
		}

		void transfer(BankAccount payee, int transferAmount) {
				credit(-transferAmount);
				payee.credit(transferAmount);
		}
}
