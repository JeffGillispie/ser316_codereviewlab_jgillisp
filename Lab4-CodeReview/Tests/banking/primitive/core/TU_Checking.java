package banking.primitive.core;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class TU_Checking {

	@Test
	public void testCheckingType() {
		Account checking = new Checking("test", 0.0f);
		Assert.assertEquals("Checking", checking.getType());
	}
	
	@Test
	public void testCheckingToString() {
		Account checking = new Checking("test", 0.0f);
		Assert.assertEquals("Checking: test: 0.0", checking.toString());
	}
	
	@Test
	public void testCheckingState() {
		Account checking = new Checking("test", 0.0f);
		Assert.assertEquals(Account.State.OPEN, checking.getState());
	}

	@Test
	public void testCheckingDepositOpen() {
		Account checking = new Checking("test", 0.0f);
		Assert.assertTrue(checking.deposit(0.1f));
	}
	
	@Test
	public void testCheckingDepositOverdrawn() {
		Account checking = new Checking("test", -0.1f);
		checking.setState(Account.State.OVERDRAWN);
		Assert.assertTrue(checking.deposit(0.1f));
		Assert.assertEquals(Account.State.OPEN, checking.getState());
	}
	
	@Test
	public void testCheckingDepositClosed() {
		Account checking = new Checking("test", 0.0f);
		checking.setState(Account.State.CLOSED);
		Assert.assertFalse(checking.deposit(0.1f));
	}
	
	@Test
	public void testCheckingWithdrawPositiveValues() {
		Checking checking = new Checking("test", 250.50f);
		Assert.assertTrue(checking.withdraw(50.50f));		
		Assert.assertTrue(200.0f == checking.getBalance());
	}
	
	@Test
	public void testCheckingWithdrawNegativeValues() {
		Checking checking = new Checking("test", 250.00f);
		Assert.assertFalse(checking.withdraw(-50.00f));
		Assert.assertTrue(250.00f == checking.getBalance());
	}
	
	@Test
	public void testCheckingWithdrawClosedAccount() {
		Checking checking = new Checking("test", 250.50f);
		checking.setState(Account.State.CLOSED);
		Assert.assertFalse(checking.withdraw(50.50f));
		Assert.assertTrue(250.50f == checking.getBalance());
	}
	
	@Test
	public void testCheckingWithdrawOverdrawnAccount() {
		Checking checking = new Checking("test", -100f);
		checking.setState(Account.State.OVERDRAWN);
		Assert.assertTrue(checking.withdraw(50.00f));
		Assert.assertTrue(-150.00f == checking.getBalance());
	}
	
	@Test
	public void testCheckingWithdrawOpenAccount() {
		Checking checking = new Checking("test", 250.50f);
		checking.setState(Account.State.OPEN);
		Assert.assertTrue(checking.withdraw(50.50f));
		Assert.assertTrue(200.00f == checking.getBalance());
	}
	
	@Test
	public void testCheckingWithdrawFee() {
		int start = 100;
		float balance = 1000;
		float fee = 0;
		Checking checking = new Checking("test", balance);
		
		for (int x = start; x > 0; x--) {
			if (x <= start - 10)
				fee = 2;			
			checking.withdraw(1f);
			balance -= (1 + fee);
			Assert.assertTrue(balance == checking.getBalance());
		}		
	}

	@Test
	public void testCheckingWithdrawOverdrawn() {
		Checking checking = new Checking("test", 5f);
		
		for (int i = 0; i < 1000; i++) {
			float balance = checking.getBalance();
			boolean result = checking.withdraw(1f);
			
			if (balance < -100) {
				Assert.assertFalse(result);
				Assert.assertTrue(balance == checking.getBalance());
			}
			else {
				balance -= (i >= 10) ? 3 : 1;
				Assert.assertTrue(balance == checking.getBalance());
			}
		}
		
	}
}
