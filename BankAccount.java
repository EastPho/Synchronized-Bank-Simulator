import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * This class contains the deposit and withdrawal methods to insert and remove money
 * from the fictitious bank account.
 */


public class BankAccount {
	// lock for mutual exclusive access to shared bank account
	private Lock accessLock = new ReentrantLock();
	
	// condition to control excess withdrawals from shared bank account
	private Condition canWithdraw = accessLock.newCondition();
	
	private int balance = 0;
	private Random cash = new Random();
	
	// depositor threads will call this method to put money into the bank account
	public void deposit() {
		// this pulls an arbitrary num to deposit into the account from $1 to $500
		int deposit = cash.nextInt(500) + 1;
		// acquire lock
		accessLock.lock();
		try {
			// update balance in account
			balance += deposit;
			System.out.printf("%s deposits $%d\t\t\t\t\t\t\t\t" + 
			"(+) Balance is $%d\n", Thread.currentThread().getName(), deposit, balance);
			
			// signal the withdrawal threads that they are able to withdraw from the account
			canWithdraw.signalAll();
		} 
		finally {
			// release lock
			accessLock.unlock();
		}
	}
	
	public void withdrawal() {
		// withdraw an arbitrary amount from $1 to $100
		int withdraw = cash.nextInt(100) + 1;
		//acquire lock
		accessLock.lock();
		try {
			// if there is sufficient funds in account, withdraw is success
			if(balance >= withdraw) {
				// update balance
				balance -= withdraw;
				System.out.printf("%s deposits $%d\t\t\t\t\t\t\t\t" + 
				"(-) Balance is $%d\n", Thread.currentThread().getName(), withdraw, balance);
			} 
			// else that means there is not enough funds in account, withdrawal cannot be done
			else {
				System.out.printf("\t\t\t\t\t%s withdraws $%d\t\t\t" +
				"(*****) WITHDRAWAL BLOCKED - INSUFFICIENT FUNDS!!!\n",
				Thread.currentThread().getName(), withdraw);
				// signal withdrawal threads to wait before more money is deposited into the shared
				// bank account
				canWithdraw.await();
			}
		} 
		catch (InterruptedException ie) {
			ie.printStackTrace();
		} 
		finally {
			// release lock
			accessLock.unlock();
		}
	}
}
