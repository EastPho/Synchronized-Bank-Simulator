import java.util.Random;

/*
 * This is the depositor which will act as multiple threads depositing
 * cash into the shared bank account.
 */

public class Depositor implements Runnable {
	private static Random generator = new Random();
	private BankAccount account;
	private static int i=0;
	private int instanceNum;
	
	// constructor for depositor thread
	public Depositor(BankAccount account) {
		this.instanceNum = ++i;
		this.account = account;
	}
	
	// deposit money into the shared bank account
	@Override
	public void run() {
		// set depositor thread a specific number name in the format: Thread D#
		Thread.currentThread().setName("Thread D" + instanceNum);
		try {
			// infinite loop to keep depositor thread running
			while(true) {
				// deposit from$1 to $500
				account.deposit();
				// thread sleeps from 0 to 4 seconds
				Thread.sleep(generator.nextInt(4000));
			}
		}
		catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

}
