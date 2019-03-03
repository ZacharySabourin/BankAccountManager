/**
  * The purpose of this class is to allow the initialization of a SavingsAccount object.
  * This class holds the logic needed to create a new savings account, displaying account information,
  * deposit/withdraw from the account, and perform monthly updates.  
  * @author Zachary Sabourin
  * @version 1.0
  * @since 1.8
 */
public final class SavingsAccount extends BankAccount 
{
	//The current minimum balance for the account
	private double minimumBalance;
	
	//The current interest rate for the account
	private double interestRate;
	
	/**
	  * The default constructor for the SavingAccount class. Calls the BankAccount constructor 
	  * and adds a minimum balance and an interest rate to the object's attributes.
	 */
	public SavingsAccount()
	{
		super();
		minimumBalance = 0;
		interestRate = 0;
	}
	
	/**
	  * Overrides the toString() method of the BankAccount class. 
	  * This method returns a formatted String that contains the value returned from BankAccount.toString()
	  * as well as a formatted version of the minimum balance and the interest rate. 
	  * @return	
	  * 	A value of type String.
	 */
	public String toString()
	{
		return super.toString() + ", Minimum balance:" + dF.format(minimumBalance) + ", Interest Rate:" + dF.format(interestRate);
	}
	
	/**
	  * This method calls BankAccount.addBankAccount() and prompts the user for a valid minimum balance and interest rate.
	  * It then returns true.
	  * @return	
	  * 	A value of type boolean.
	 */
	protected boolean addBankAccount()
	{
		super.addBankAccount();
		
		//Receive a String and attempt to convert to double. Instantiate if successfully converted.
		System.out.println("Enter a minimum balance:");
		String doubleCheck = input.next();
		minimumBalance = validateDouble(doubleCheck);
		
		//Receive a String and attempt to convert to double. Instantiate if successfully converted.
		//Value must be less than 1
		System.out.println("Enter an interest rate (0.00 - 1.00:)");
		doubleCheck = input.next();
		interestRate = validateDouble(doubleCheck);
		while(interestRate > 1)
		{
			System.out.println("Enter a valid value:");
			doubleCheck = input.next();
			interestRate = validateDouble(doubleCheck);
		}
		
		return true;
	}
		
	/**
	  * Overrides the monthlyAccountUpdate() method in the BankAccount class.
	  * Adds one month's worth of interest to the account balance.
	  * If the current balance drops below the minimum balance set for the account, An error message displays. 
	 */
	protected void monthlyAccountUpdate()
	{
		//As long as the balance is greater than the minimum required balance, accrue interest
		//Otherwise display a warning message.
		if(balance > minimumBalance)
		{
			balance += ((interestRate / 12) * balance);
		}
		else
		{
			System.out.println("Account " + accNumber + " unable to accrue interest - balance too low");
		}
	}
}
