/*
 * Name: Zachary Sabourin
 * Student ID: 040940295
 * Course & Section: CST8132 311
 * Assignment: Lab 07
 * Date: March 31, 2019
 */


/**
  * The purpose of this class is to allow the initialization of a ChequingAccount object.
  * This class holds the logic needed to create a new chequing account, displaying account information,
  *  and performing monthly updates.  
  * @author Zachary Sabourin
  * @version 1.0
  * @since 1.8
 */
public class ChequingAccount extends BankAccount
{
	//The current monthly fee for the account
	private double monthlyFee;
	
	/**
	  * The default constructor for the ChequingAccount class. Calls the BankAccount constructor 
	  * and adds a randomized monthly fee(5.0-10.0) to the object's attributes.
	 */
	public ChequingAccount()
	{
		super();
		
		double minFee = 5.0;
		double maxFee = 10.0;
		
		monthlyFee = minFee + rnd.nextDouble() * (maxFee - minFee);
	}
	
	/**
	  * The parameterized constructor for the SavingAccount class. Calls the BankAccount's parameterized constructor 
	  * and instantiates the objects attributes with the received first name, last name, account number, email,
	  * balance, and monthly fee parameters.
	  * @param firstName
	  * 	The account holder's first name
	  * @param lastName
	  * 	The account holder's last name
	  * @param accNumber
	  * 	The account number
	  * @param	email
	  * 	The account holder's email
	  * @param balance
	  * 	the account's balance
	  * @param	monthlyFee
	  * 	The account's monthly fee
	 */
	public ChequingAccount(String firstName, String lastName, long accNumber, String email, double balance, double monthlyFee)
	{
		super(firstName, lastName, accNumber, email, balance);
		this.monthlyFee = monthlyFee;
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
		return "C " + super.toString() + " $" + dF.format(monthlyFee);
	}
	
	/**
	  * This method returns the value returned from BankAccount.addBankAccount()
	  * @return	
	  * 	A value of type boolean.
	 */
	protected boolean addBankAccount()
	{
		return super.addBankAccount();
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
