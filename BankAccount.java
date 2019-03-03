//Imported package to allow the formatting of the account balance, monthly fee, minimum balance and interest rate.
//Used by sub-classes
import java.text.DecimalFormat;

//Imported package to allow user input. Used by sub-classes
import java.util.Scanner;

//Imported package to allow the creation of a pattern used to validate the inputed email address String
import java.util.regex.Pattern;

/**
  * The purpose of this class is to allow the initialization of a BankAccount object.
  * Provides the blueprint necessary to display account information and to perform monthly updates.
  * This class holds the logic needed to create a new bank account and to update/withdraw from the account.
  * It also handles error checking for itself and it's sub-classes. 
  * @author Zachary Sabourin
  * @version 1.0
  * @since 1.8
 */
public abstract class BankAccount 
{
	/** The unique account number **/
	protected int accNumber;
	
	/** The unique Person object **/
	protected Person accHolder;
	
	/** The current balance **/
	protected double balance;
	
	/** DecimalFormat object used to format the display of the balance, the monthly fee, minimum balance and interest rate.
	  * Regardless of zeros present, guarantees the display of at least one number before the decimal point and at least 2 afterwards. **/
	protected DecimalFormat dF = new DecimalFormat("###,##0.00");
	
	/** Scanner object used to receive user input. Also used by sub-classes. **/
	protected Scanner input = new Scanner(System.in);
	
	/**
	  * Default constructor for the BankAccount class. Instantiates a unique account number, object of type Person, and balance.
	  * Used by sub-classes.
	 */
	protected BankAccount()
	{
		accNumber = 0;
		accHolder = new Person("", "", 0, "");
		balance = 0;
	}
	
	/**
	  * Overrides the toString() method of the Object class. 
	  * This method returns a formatted String that contains the account number, 
	  * the account holder's full name, phone number, and email, as well as a formatted version of the balance. 
	  * @return	
	  * 	A value of type String.
	 */
	public String toString()
	{
		return "Account number:" + accNumber + ", Name:" + accHolder.getName() + ", Phone number:" + accHolder.getPhoneNumber()
				+ ", Email:" + accHolder.getEmail() + ", Balance:" + dF.format(balance);
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
		
		//Strings used to ensure correct user input in all situations
		String intCheck;
		String doubleCheck;
		String nameCheck;
		String emailCheck;
		
		do
		{
			System.out.println("Enter an account number: ");	
			
			//Receive a String value and attempt to convert to int by calling validateInt()
			intCheck = input.next();
			int accNumberCheck = validateInt(intCheck);
			
			//If account number is not found, instantiate the account number
			if(Bank.findAccount(accNumberCheck) == -1)
			{
				accNumber = accNumberCheck; 
				isValid = true;
			}
			else
			{
				System.out.println("This account number already exists");
			}
		}
		while(!isValid);
		
		//Receive Strings and ensure there are no unwanted symbols or numbers
		System.out.println("Enter a first name of account holder: ");
		nameCheck = input.next();
		String firstName = validateName(nameCheck);
		
		System.out.println("Enter a last name of account holder: ");
		nameCheck = input.next();
		String lastName = validateName(nameCheck);
				
		//Receive String and convert to int. Instantiate when successful
		System.out.println("Enter a phone number: ");
		intCheck = input.next();
		int phoneNum = validateInt(intCheck);
			
		//Receive String and ensure it is a valid email
		System.out.println("Enter an Email address: ");
		emailCheck = input.next();
		String email = validateEmail(emailCheck);
		
		//Receive String and attempt to convert to double. Instantiate when successful
		System.out.println("Enter an opening balance: ");
		doubleCheck = input.next();	
		balance = validateDouble(doubleCheck);
		
		accHolder = new Person(firstName, lastName, phoneNum, email);
		return true;
	}

	/**
	  * This method takes a parameter of type double and adds it to the balance. If a negative value is passed,
	  * it will subtract the value from the balance instead.
	  * If the balance drops below 0, the balance is reverted to it's original state and displays an error message.
	  * @param amt
	  * 	The desired deposit/withdraw amount.
	 */	
	protected void updateBalance(double amt)
	{
		balance += amt;
		
		//If balance drops below 0 with the transaction, revert the balance and display a warning
		//Otherwise display a confirmation message
		if(balance < 0)
		{
			balance -= amt;
			System.out.println("Account " + accNumber + " balance too low");
		}
		else
		{
			System.out.println("Account " + accNumber + " updated");
		}
	}
	
	/** Allows each sub-class to create their own version of a monthlyAccountUpdate **/
	protected abstract void monthlyAccountUpdate();
	
	
	/*
	 * This method receives a String as a parameter and compares it to a regex pattern.
	 * It prompts the user for a valid name until the inputed String is a match.
	 * Then ir returns the value of the valid name
	*/
	private String validateName(String name)
	{
		//Regex pattern created to ensure name contains only letter, hyphens and apostrophes
		String namePattern = "^[A-Za-z-']*$";
				
		//Creation of a Pattern object. Compiling it with the string above
		Pattern pat = Pattern.compile(namePattern);
		
		//loop control variable
		boolean isValid = false;
		
		//Attempt to match the inputed name with the pattern created above
		do
		{
			name = input.next();
			isValid = pat.matcher(name).matches();
			
			if(!isValid)
			{
				System.out.println("Enter a valid name:");
			}
		}//Continue to prompt user until name is valid
		while (!isValid);
		
		return name;
	}
	
	/*
	 * This method receives a String as a parameter and compares it to a regex pattern.
	 * It prompts the user for a valid email until the inputed String is a match.
	 * Then it returns the value of the valid email.
	*/
	private String validateEmail(String email)
	{
		//Regex pattern I found online. Ensures valid characters before and after the @ as well as a minimum of 2 characters after the period
		String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";	
		
		//Creation of a Pattern object. Compiling it with the string above
		Pattern pat = Pattern.compile(emailPattern);
		
		//loop control variable
		boolean isValid = false;
		
		//Attempt to match the inputed email with the pattern created above
		do
		{
			email = input.next();
			isValid = pat.matcher(email).matches();
			
			if(!isValid)
			{
				System.out.println("Enter a valid email:");
			}
		}//Continue to prompt user until email is valid
		while (!isValid);
		
		return email;
	}
	
	/**
	  * This method receives a String and attempts to parse it to an double.
	  * Prompts user until a valid double value is input. Returns the value of the double if it has successfully been converted and is not a negative.
	  * @param doubleCheck
	  * 	The String attempting to be converted to a double.
	  * @return	
	  * 	A value of type double.
	 */
	protected double validateDouble(String doubleCheck)
	{
		//loop control variable
		boolean isValid = false;
		
		//Value to be returned once parsing is successful
		double convertedDouble = 0;
		
		do
		{
			try
			{
				//Attempt to convert String to double
				convertedDouble = Double.parseDouble(doubleCheck);
				
				//Ensure value is greater than 0
				if(convertedDouble < 0)
				{
					System.out.println("Enter a valid value:");
					doubleCheck = input.next();
				}
				else
				{
					isValid = true;
				}
			}
			catch(NumberFormatException nfe)
			{
				System.out.println("Enter a valid value:");
				doubleCheck = input.next();
			}
		}//Continue to prompt until value is valid
		while(!isValid);
		
		//return the converted value
		return convertedDouble;
	}
	
	/**
	  *  This method receives a String and attempts to parse it to an int.
	  * Prompts user until a valid int value is input. Returns the value of the double if it has successfully been converted and is not a negative.
	  * @param intCheck
	  * 	The String attempting to be converted to an integer.
	  * @return	
	  * 	A value of type int.
	 */
	protected int validateInt(String intCheck)
	{
		//loop control variable
		boolean isValid = false;
		
		//Value to be returned once parsing is successful
		int convertedInt = 0;		
		
		do
		{
			try
			{
				//Attempt to convert String to int
				convertedInt = Integer.parseInt(intCheck);
				
				//Ensure value is greater than 0
				if(convertedInt < 0)
				{
					System.out.println("Enter a valid value:");
					intCheck = input.next();
				}
				else
				{
					isValid = true;
				}
			}
			catch(NumberFormatException nfe)
			{
				System.out.println("Enter a valid value:");
				intCheck = input.next();
			}
		}//Continue to prompt until value is valid
		while(!isValid);
		
		//return the converted value
		return convertedInt;
	}
}
