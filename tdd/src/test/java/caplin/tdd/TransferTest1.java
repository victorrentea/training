package caplin.tdd;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nicolaes on 07/01/2016.
 */
public class TransferTest1 {
		@Test
		public void testTransfer() {
				BankAccount payer = new BankAccount();
				BankAccount payee = new BankAccount();

				payer.credit(100);

				int transferAmount = 50;

				payer.transfer(payee, transferAmount);

				assertEquals(payer.getBalance(), 50);
				assertEquals(payee.getBalance(), 50);
		}

}


