/**
  * The purpose of this class is to allow the initialization of a SavingsAccount object.
  * This class holds the logic needed to create a new savings account, displaying account information,
  * and performing monthly updates.  
  * @author Zachary Sabourin
  * @version 1.0
  * @since 1.8
 */
public class SavingsAccount extends BankAccount 
{	
	//The current interest rate for the account
	private double interestRate;
	
	/**
	  * The default constructor for the SavingAccount class. Calls the BankAccount constructor 
	  * and adds a randomized interest rate(0-0.1) to the object's attributes.
	 */
	public SavingsAccount()
	{
		super();
		
		double minInterest = 0;
		double maxInterest = 0.1;
		
		interestRate =  minInterest + rnd.nextDouble() * (maxInterest - minInterest);
	}
	
	/**
	  * The parameterized constructor for the SavingAccount class. Calls the BankAccount's parameterized constructor 
	  * and instantiates the objects attributes with the received first name, last name, account number, email
	  * balance and interest rate parameters.
	  * @param firstName
	  * 	The account holder's first name
	  * @param lastName
	  * 	The account holder's last name
	  * @param accNumber
	  * 	The account number
	  * @param	email
	  * 	The account holder's email
	  * @param blance
	  * 	the account's balance
	  * @param	interestRate
	  * 	The account's interest rate
	 */
	public SavingsAccount(String firstName, String lastName, long accNumber, String email, double balance, double interestRate)
	{
		super (firstName, lastName, accNumber, email, balance);
		this.interestRate = interestRate;
	}
	
	
	/**
	  * Overrides the toString() method of the BankAccount class. 
	  * This method returns a formatted String that contains the value returned from BankAccount.toString()
	  * as well as a formatted version of the the interest rate. 
	  * @return	
	  * 	A value of type String.
	 */
	public String toString()
	{
		return "S " + super.toString() + " %" + dF.format(interestRate);
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
	  * Overrides the monthlyAccountUpdate() method in the BankAccount class.
	  * Adds one month's worth of interest to the account balance.
	 */
	protected void monthlyAccountUpdate()
	{
		balance += ((interestRate / 12) * balance);		
	}
}
