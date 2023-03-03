import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * This program implements a simulation of deposits and withdrawals being made
 * to a fictitious bank account using synchronized threads.
 */

public class BankInit {

	public static void main(String[] args) {
		ExecutorService bankApp = Executors.newFixedThreadPool(14);
		
		BankAccount sharedAccount = new BankAccount();
		
		System.out.printf("%-40s%s\t\t%s\n%-40s%s\n\n", "Deposit Threads", 
				"Withdrawal Threads", "		Balance", "_______________",
				"__________________\t		_______________________");

		try {
			bankApp.execute(new Depositor(sharedAccount));
			bankApp.execute(new Depositor(sharedAccount));
			bankApp.execute(new Depositor(sharedAccount));
			bankApp.execute(new Depositor(sharedAccount));
			bankApp.execute(new Depositor(sharedAccount));
			bankApp.execute(new Withdrawal(sharedAccount));
			bankApp.execute(new Withdrawal(sharedAccount));
			bankApp.execute(new Withdrawal(sharedAccount));
			bankApp.execute(new Withdrawal(sharedAccount));
			bankApp.execute(new Withdrawal(sharedAccount));
			bankApp.execute(new Withdrawal(sharedAccount));
			bankApp.execute(new Withdrawal(sharedAccount));
			bankApp.execute(new Withdrawal(sharedAccount));
			bankApp.execute(new Withdrawal(sharedAccount));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		bankApp.shutdown();
	}

}
