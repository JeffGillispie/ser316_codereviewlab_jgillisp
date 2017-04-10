package banking.primitive.core;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class TU_Savings {
	
	@Test
	public void testDeposit() {
		Savings savings = new Savings("test", 10.0f);
		savings.setState(Account.State.OPEN);
		boolean result = savings.deposit(1.0f);
		Assert.assertTrue(result);
		Assert.assertEquals(10.5f, savings.getBalance());
		Assert.assertTrue(savings.getState().equals(Account.State.OPEN));
	}
	
	@Test
	public void testDepositOverdrawnNoStateChange() {
		Savings savings = new Savings("test", -10.0f);
		savings.setState(Account.State.OVERDRAWN);
		boolean result = savings.deposit(1.0f);
		Assert.assertTrue(result);
		Assert.assertEquals(-9.5f, savings.getBalance());
		Assert.assertFalse(savings.getState().equals(Account.State.OPEN));
	}
	
	@Test
	public void testDepositOverdrawnSetStateOpen() {
		Savings savings = new Savings("test", -10.0f);
		savings.setState(Account.State.OVERDRAWN);
		boolean result = savings.deposit(11.0f);
		Assert.assertTrue(result);
		Assert.assertEquals(0.5f, savings.getBalance());
		Assert.assertTrue(savings.getState().equals(Account.State.OPEN));
	}
	
	@Test
	public void testDepositFailsStateClosed() {
		Savings savings = new Savings("test", 0.01f);
		savings.setState(Account.State.CLOSED);
		boolean result = savings.deposit(0.50f);
		Assert.assertFalse(result);
		Assert.assertEquals(0.01f, savings.getBalance());
		Assert.assertTrue(savings.getState().equals(Account.State.CLOSED));
	}
	
	@Test
	public void testDepositFailsStateOpen() {
		Savings savings = new Savings("test", 10.0f);
		savings.setState(Account.State.OPEN);
		boolean result = savings.deposit(0.0f);
		Assert.assertFalse(result);
		Assert.assertEquals(10.0f, savings.getBalance());
		Assert.assertTrue(savings.getState().equals(Account.State.OPEN));
	}
	
	@Test
	public void testDepositFailsClosedZero() {
		Savings savings = new Savings("test", 0.00f);
		savings.setState(Account.State.CLOSED);
		boolean result = savings.deposit(0.50f);
		Assert.assertFalse(result);
		Assert.assertEquals(0.00f, savings.getBalance());
		Assert.assertTrue(savings.getState().equals(Account.State.CLOSED));
	}
	
	@Test
	public void testWithdraw() {
		Savings savings = new Savings("test", 10.0f);
		savings.setState(Account.State.OPEN);
		boolean result = savings.withdraw(1.0f);
		Assert.assertTrue(result);
	}
	
	@Test
	public void testWithdrawOverdrawn() {
		Savings savings = new Savings("test", 10.0f);
		savings.setState(Account.State.OPEN);
		boolean result = savings.withdraw(11.0f);
		Assert.assertTrue(result);
		Assert.assertTrue(savings.getState().equals(Account.State.OVERDRAWN));
	}
	
	@Test
	public void testWithdrawFee() {
		Savings savings = new Savings("test", 10.0f);
		savings.setState(Account.State.OPEN);
		
		for (int i = 0; i < 5; i++) {
			savings.withdraw(1.0f);
		}
		
		Assert.assertEquals(3.0f, savings.getBalance());
	}
	
	@Test
	public void testWithdrawFails() {
		Savings savings = new Savings("test", -10.0f);
		savings.setState(Account.State.OVERDRAWN);
		boolean result = savings.withdraw(1.0f);
		Assert.assertFalse(result);
	}
	
	@Test
	public void testWithdrawOverdrawnPositive() {
		Savings savings = new Savings("test", 10.0f);
		savings.setState(Account.State.OVERDRAWN);
		boolean result = savings.withdraw(1.0f);
		Assert.assertFalse(result);
	}
	
	@Test
	public void testWithdrawOpenZero() {
		Savings savings = new Savings("test", 10.0f);
		savings.setState(Account.State.OPEN);
		boolean result = savings.withdraw(0.0f);
		Assert.assertFalse(result);
	}

}
