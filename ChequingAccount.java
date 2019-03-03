/**
  * The purpose of this class is to allow the initialization of a ChequingAccount object.
  * This class holds the logic needed to create a new chequing account, displaying account information,
  * deposit/withdraw from the account, and perform monthly updates.  
  * @author Zachary Sabourin
  * @version 1.0
  * @since 1.8
 */
public final class ChequingAccount extends BankAccount
{
	//The current monthly fee for the account
	private double monthlyFee;
	
	/**
	  * The default constructor for the ChequingAccount class. Calls the BankAccount constructor 
	  * and adds a monthly fee to the object's attributes.
	 */
	public ChequingAccount()
	{
		super();
		monthlyFee = 0;
	}
	
	/**
	  * Overrides the toString() method of the BankAccount class. 
	  * This method returns a formatted String that contains the the value returned from BankAccount.toString()
	  * as well as a formatted version of the monthly fee. 
	  * @return	
	  * 	A value of type String.
	 */
	public String toString()
	{
		return super.toString() + ", Monthly fee:" + dF.format(monthlyFee);
	}
	
	/**
	  * This method calls BankAccount.addBankAccount() and prompts the user for a valid monthly fee.
	  * It then returns true.
	  * @return	
	  * 	A value of type boolean.
	 */
	protected boolean addBankAccount()
	{
		super.addBankAccount();
		
		//Receive a String and attempt to convert to int. Instantiate when successful.
		System.out.println("Enter a monthly fee:");
		String intCheck = input.next();
		monthlyFee = validateDouble(intCheck);		

		return true;
	}
	
	/**
	  * Overrides the monthlyAccountUpate() method in the BankAccount class.
	  * Subtracts the unique monthly fee from the balance.
	  * Displays a warning if the balance drops below 0
	  *
	 */
	protected void monthlyAccountUpdate()
	{
		balance -= monthlyFee;
		
		/* I considered not allowing the monthly fee to be withdrawn, but I know that banks will take their money 
		 * from the account holder regardless of how much is in their account.
		 * It will still display a warning about the account balance being negative.
		*/
		if(balance < 0)
		{
			System.out.println("Account " + accNumber + " balance negative");
		}
	}
}
