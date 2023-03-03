import java.util.Random;

/*
 * This is the withdrawal thread which will act as multiple threads withdrawing
 * cash from the shared bank account.
 */

public class Withdrawal implements Runnable {
	private static Random generator = new Random();
	private BankAccount account;
	private static int i=0;
	private int instanceNum;
	
	// constructor for withdrawal thread
	public Withdrawal(BankAccount account) {
		this.instanceNum = ++i;
		this.account = account;
	}
	
	// withdraw money from shared bank account
	@Override
	public void run() {
		// set withdrawal thread to a specific number name in the format: Thread W#
		Thread.currentThread().setName("Thread W" + instanceNum);
		try {
			// infinite loop to keep withdrawal thread running
			while(true) {
				// withdraws between $1 and $100
				account.withdrawal();
				// thread sleeps for 0 to 2 seconds
				Thread.sleep(generator.nextInt(2000));
			}
		}
		catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
}
