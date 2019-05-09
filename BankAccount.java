/*
 * Name: Zachary Sabourin
 * Student ID: 040940295
 * Course & Section: CST8132 311
 * Assignment: Lab 07
 * Date: March 31, 2019
 */

//Imported package to allow the formatting of the account balance, monthly fee, minimum balance and interest rate.
//Used by sub-classes
import java.text.DecimalFormat;

//Imported package to allow user input. Used by sub-classes
import java.util.Scanner;

//Imported package to allow the creation of a pattern used to validate the inputed email address String
import java.util.regex.Pattern;

//Imported package to allow randomizing of variables.
import java.util.Random;

/**
  * The purpose of this class is to allow the initialization of a BankAccount object.
  * Provides the blueprint necessary to display account information and to perform monthly updates.
  * This class holds the logic needed to create a new bank account and to update/withdraw from the account.
  * It also handles all exception handling for itself and it's sub-classes. 
  * @author Zachary Sabourin
  * @version 1.0
  * @since 1.8
 */
public abstract class BankAccount 
{
	/** The unique account number **/
	protected long accNumber;
	
	/** The unique Person object **/
	protected Person accHolder;
	
	/** The current balance **/
	protected double balance;
	
	/** DecimalFormat object used to format the display of the balance, the monthly fee, minimum balance and interest rate.
	  * Regardless of zeros present, guarantees the display of at least one number before the decimal point and at least 2 afterwards. **/
	protected DecimalFormat dF = new DecimalFormat("###,##0.00");
	
	/** Scanner object used to receive user input. Also used by sub-classes. **/
	protected Scanner input = new Scanner(System.in);
	
	/** Random object used to randomize the monthly fee and the interest rate of the sub-classes. **/
	protected Random rnd = new Random();
	
	/**
	  * Default constructor for the BankAccount class. Instantiates a unique account number, object of type Person, and balance.
	  * Used by sub-classes.
	 */
	protected BankAccount()
	{
		accNumber = 0;
		accHolder = new Person("", "", "");
		balance = 0;
	}
	
	/**
	  * The parameterized constructor for the BankAccount class.  
	  * Instantiates the objects attributes with the received first name, last name, account number
	  * email and balance.
	  * @param firstname
	  * 	The account holder's first name
	  * @param lastName
	  * 	The account holder's last name
	  * @param accNumber
	  * 	The account number
	  * @param	email
	  * 	The account holder's email
	  * @param balance
	  * 	the account's balance
	 */
	protected BankAccount(String firstName, String lastName, long accNumber, String email, double balance)
	{
		this.accHolder = new Person(firstName, lastName, email);
		this.accNumber = accNumber;
		this.balance = balance;
	}
	
	/**
	  * Overrides the toString() method of the Object class. 
	  * This method returns a formatted String that contains the account number, 
	  * the account holder's full name and email, as well as a formatted version of the balance. 
	  * @return	
	  * 	A value of type String.
	 */
	public String toString()
	{
		return accHolder.getName() + " " + accNumber + " " + accHolder.getEmail() + " $" + dF.format(balance);
	}
	
	/**
	  * This method Prompts the user for a valid account number. It then prompts the user for a valid first name,
	  * last name, phone number, email and opening balance. It creates an object of type Person with the 
	  * names, phone number and email received. It then returns true.
	  * @return	
	  * 	A value of type boolean.
	 */
	protected boolean addBankAccount()
	{	
		//loop control value
		boolean isValid = false;	
	
		String firstName = "";
		String nameCheck;
		
		do
		{
			//Match the received String with a pattern created in validateName()
			//Catch any exceptions that may occur
			try
			{
				System.out.println("Enter a first name: ");
				nameCheck = input.next();
				firstName = validateName(nameCheck);
				isValid = true;
			}
			catch(IllegalArgumentException illegalArgumentException)
			{
				System.err.println("Invalid name");
			}
		}
		while(!isValid);
		
		//reset to false for the following loop
		isValid = false;
		String lastName = "";
		
		do
		{
			//Match the received String with a pattern created in validateName()
			//Catch any exceptions that may occur
			try
			{
				System.out.println("Enter a last name: ");
				nameCheck = input.next();
				lastName = validateName(nameCheck);
				isValid = true;
			}
			catch(IllegalArgumentException illegalArgumentException)
			{
				System.err.println("Invalid name");
			}
		}
		while(!isValid);
		
		//reset to false for the following loop
		isValid = false;
		String longCheck = "";
		long accNumberCheck = 0;
		
		do
		{
			do
			{
				//attempt to parse the received String with a validateLong()
				//Catch any errors that may occur
				try
				{
					System.out.println("Enter an account number:");
					longCheck = input.next();
					accNumberCheck = validateLong(longCheck);	
					isValid = true;
				}
				catch(NumberFormatException numberFormatException)
				{	
					System.err.println("Invalid number");
				}	
				catch(IllegalArgumentException illegalArgumentException) 
				{
					System.err.println("Invalid number");
				}
				
			}
			while(!isValid);
			
			//If account number is not found, instantiate the account number
			if(Bank.findAccount(accNumberCheck) == -1)
			{
				accNumber = accNumberCheck; 
			}
			else
			{
				//If the number exists, return false and go back to the main menu
				System.err.println("This account number already exists");
				return false;
			}
		}
		while(!isValid);
			
		isValid = false;
		String email = "";
		String emailCheck;
		
		//Receive String and ensure it is a valid email
		do
		{
			//Match the received String with a pattern created in validateName()
			//Catch any exceptions that may occur
			try
			{
				System.out.println("Enter an Email address: ");
				emailCheck = input.next();
				email = validateEmail(emailCheck);
				isValid = true;
			}
			catch(IllegalArgumentException illegalArgumentException)
			{
				System.err.println("Invalid email");
			}
		}
		while(!isValid);
		
		//Person object created with above information
		accHolder = new Person(firstName, lastName, email);
			
		isValid = false;
		String doubleCheck;
		
		//Receive String and attempt to convert to double. Instantiate when successful
		do
		{
			try
			{
				System.out.println("Enter an opening balance:");
				doubleCheck = input.next();
				balance = validateDouble(doubleCheck);
				isValid = true;
			}
			catch(NumberFormatException numberFormatException)
			{
				System.err.println("Invalid number");
			}	
			catch(IllegalArgumentException illegalArgumentException) 
			{
				System.err.println("Invalid number");
			}
		}
		while(!isValid);
		
		return true;
	}

	/**
	  * This method takes a parameter of type double and adds it to the balance. If a negative value is passed,
	  * the method will throw a custom TransactionIllegalArgumentException.
	  * If successful, displays a confirmation message.
	  * @param amt
	  * 	The desired deposit amount.
	  * @throws TransactionIllegalArgumentException
	  * 	A custom exception created for negative values
	 */	
	protected void deposit(double amt) throws TransactionIllegalArgumentException
	{
		if(amt < 0)
		{
			throw new TransactionIllegalArgumentException("Cannot deposit (Amount negative)\n");
		}
		else
		{
			balance += amt;
			System.out.println("Account " + accNumber + " updated");
		}
	}
	
	/**
	  * This method takes a parameter of type double and subtracts it from the balance. If the passed balue
	  * is negative or greater than the current balance of the account,
	  * the method will throw a custom TransactionIllegalArgumentException.
	  * If successful, displays a confirmation message.
	  * @param amt
	  * 	The desired withdraw amount.
	  * @throws TransactionIllegalArgumentException
	  * 	A custom exception created for negative values
	 */
	protected void withdraw(double amt) throws TransactionIllegalArgumentException
	{
		if(amt < 0 || amt > balance)
		{
			throw new TransactionIllegalArgumentException("Cannot withdraw (Amount negative/balance insufficient)\n");
		}
		else
		{
			balance -= amt;
			System.out.println("Account " + accNumber + " updated");
		}
	}
	
	/** Allows each sub-class to create their own version of a monthlyAccountUpdate **/
	protected abstract void monthlyAccountUpdate();
	
	
	/*
	 * This method receives a String as a parameter and compares it to a regex pattern.
	 * If the String does not match the pattern, it throws an IllegalArgumentException.
	 * If the String matches the pattern, it returns the value of the name.
	 * @param name
	 * 		The name String attempting to be validated.
	 * @return	
	 * 		A value of type String.
	 * @throws IllegalArgumentException
	 * 		Exception thrown for invalid name
	*/
	private String validateName(String name) throws IllegalArgumentException
	{
		//Regex pattern created to ensure name contains only letter, hyphens and apostrophes
		String namePattern = "^[A-Za-z-']*$";
				
		//Creation of a Pattern object. Compiling it with the string above
		Pattern pat = Pattern.compile(namePattern);
		
		//attempt to match the Strig with the created pattern
		if(!pat.matcher(name).matches())
		{
			throw new IllegalArgumentException();
		}
		else 
		{
			return name;
		}
	}
	
	/*
	 * This method receives a String as a parameter and compares it to a regex pattern.
	 * If the String does not match the pattern, it throws an IllegalArgumentException.
	 * If the String matches the pattern, it returns the value of the email.
	 * @param email
	 * 		The email String attempting to be validated.
	 * @return	
	 * 		A value of type String.
	 * @throws IllegalArgumentException
	 * 		Exception thrown for invalid email
	*/
	private String validateEmail(String email) throws IllegalArgumentException
	{		
		//Regex pattern I found online. Ensures valid characters before and after the @ as well as a minimum of 2 characters after the period
		String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";	
		
		//Creation of a Pattern object. Compiling it with the string above
		Pattern pat = Pattern.compile(emailPattern);
		
		//Attempt to match the inputed email with the created pattern
		if(!pat.matcher(email).matches())
		{
			throw new IllegalArgumentException();
		}
		else
		{
			return email;
		}
	}
	
	/**
	  * This method receives a String and attempts to parse it to an double.
	  * Throws an exception if the number could not be parsed or if the number is negative.
	  * Otherwise returns the parsed double value.
	  * @param doubleCheck
	  * 	The String attempting to be converted to a double.
	  * @return	
	  * 	A value of type double.
	  * @throws NumberFormatException
	  * 	Exception thrown if not a valid double value
	  * @throws IllegalArgumentException
	  * 	Exception thrown if value is negative
	 */
	protected double validateDouble(String doubleCheck) throws NumberFormatException, IllegalArgumentException
	{
		//Value to be returned once parsing is successful
		double convertedDouble = Double.parseDouble(doubleCheck);
		
		if(convertedDouble < 0)
		{
			throw new IllegalArgumentException();
		}
		
		//return the converted value
		else
		{
		return convertedDouble;
		}
	}
	
	/**
	  * This method receives a String and attempts to parse it to a long.
	  * Throws an exception if the number could not be parsed or if the number is negative.
	  * Otherwise returns the parsed long value.
	  * @param longCheck
	  * 	The String attempting to be converted to a long.
	  * @return	
	  * 	A value of type long.
	  * @throws NumberFormatException
	  * 	Exception thrown if not a valid long value
	  * @throws IllegalArgumentException
	  * 	Exception thrown if value is negative
	 */
	protected long validateLong(String longCheck) throws NumberFormatException, IllegalArgumentException
	{
		//Attempt to convert String to long
		long convertedLong = Integer.parseInt(longCheck);
		
		if(convertedLong < 0)
		{
			throw new IllegalArgumentException();
		}
				
		//return the converted value
		else
		{	
			return convertedLong;
		}
	}
}