/*
 * Name: Zachary Sabourin
 * Student ID: 040940295
 * Course & Section: CST8132 311
 * Assignment: Assingment 1
 * Date: February 17, 2019
 */

//Imported package to allow user input.
import java.util.Scanner;

/**
 * The purpose of this class is to allow the initialization of a Bank object with a specified bank size or the default size of 1000 accounts.
 * This class holds an array of bank accounts and holds the logic required to add a bank account of a specific type to the array.
 * Allows the user to update an account in the array, display information for every account in the array or for a specific account,
 * find a specific account in the array, and perform monthly updates on every account in the array.
 * This class also handles any errors regarding user input.
 * @author Zachary Sabourin
 * @version 1.0
 * @since 1.8
*/
public class Bank 
{
	//Array of BankAccount objects. Declared as static to allow being used in the static findAccount(int) method.
	private static BankAccount[] accounts;
	
	//Default maximum size of the bank array. Can be changed using the parameterized constructor
	private int sizeBank = 1000;
	
	//Current number of accounts. Declared as static to allow being used in the static findAccount(int) method.
	private static int numAccounts = 0;
	
	//Scanner object to allow user input
	private Scanner input = new Scanner(System.in);
	
	/**
	  * Default constructor for the Bank class. Instantiates an array of BankAccount objects using the default size of 1000.
	 */
	public Bank()
	{
		accounts = new BankAccount[sizeBank];
	}
	
	/**
	  * Overloaded constructor for the Bank class. Changes the value of the bank size with a desired amount.
	  * Instantiates an array of bankAccount objects with the desired size
	  * @param sizeBank
	  * 	The desired bank size.
	 */
	public Bank(int sizeBank)
	{
		this.sizeBank = sizeBank;
		accounts = new BankAccount[sizeBank];
	}
	
	/**
	  * This method verifies whether or not the maximum size of the bank has been reached and returns false if so.
	  * It displays the current number of accounts, prompts the user for a desired account type and calls the addBankAccount() method
	  * which instantiates the account object's attributes. Returns false if an account was not added.
	  * @return
	  * 	A value of type boolean
	 */
	public boolean addAccount()
	{
		//Return false if the bank is full
		if(numAccounts >= sizeBank)
		{
			System.out.println("Bank full - The bank can only hold " + accounts.length + " accounts.");
			return false;
		}
		
		//Display amount of current accounts in the bank
		int currentAccIndex = numAccounts + 1;
		System.out.println("Enter details of account holder " + currentAccIndex
							+ "\n-------------------------");
		
		//PPrompt for account type. Not continuing until valid input is received
		System.out.println("Enter account type (c for chequing, s for savings): ");
		String accType = input.next();
		while(!accType.equalsIgnoreCase("c") && !accType.equalsIgnoreCase("s"))
		{
			System.out.print("Select a valid account type: ");
			accType = input.next();
		}
		
		//If s, add savings account. Otherwise add chequing account
		if(accType.equalsIgnoreCase("s"))
		{
			accounts[numAccounts] = new SavingsAccount();
		}
		else
		{
			accounts[numAccounts] = new ChequingAccount();
		}
		
		//If account added successful
		if(accounts[numAccounts].addBankAccount())
		{
			//Increase the number of accounts by 1 and display confirmation
			numAccounts++;
			System.out.println("Account " + numAccounts + " added");
			return true;
		}
		
		//If not added, display error
		System.out.print("Account not added");
		return false;
	}
	
	/**
	  * This method allows the user to search for an account and prompts the user for a desired withdraw/deposit value.
	  * Displays an error message otherwise
	 */
	public void updateAccount()
	{
		//Calls a method that attempts to find an account
		int accIndex = findAccount();
		
		//If the method returns -1, displays error message. Otherwise allows the user to withdraw/deposit.
		if(accIndex == -1)
		{
			System.out.println("Account not found");
		}
		else
		{
			System.out.println("Enter a number to deposit/withdraw (positive number to deposit, negative number to withdraw):");
			String doubleCheck = input.next();	
			double updateAmt = validateDouble(doubleCheck);
			
			accounts[accIndex].updateBalance(updateAmt);
		}
	}
	
	/**
	  * This method allows the user to search for an account and displays the account information if it is found.
	  * Displays an error message otherwise
	 */
	public void displayAccount()
	{	
		//Calls a method that attempts to find an account
		int accIndex = findAccount();
		
		//If the method returns -1, displays error message. Otherwise displays account information.
		if(accIndex == -1)
		{
			System.out.println("Account not found");
		}
		else
		{
			System.out.println(accounts[accIndex].toString());
		}		
	}

	/**
	  * This method iterates through the array of accounts, stopping at the last account added.
	  * It then displays each accounts information.
	 */
	public void printAccountdetails()
	{
		System.out.println("Number of account holders: " + numAccounts);
		
		//Display account information on indices with accounts
		for(int i = 0; i < numAccounts; i++)
		{
			System.out.println(accounts[i]);
		}
	}
	
	//This method prompts the user for a valid account number and returns the value received
	//by calling findAccount(int accNumber)
	private int findAccount()
	{	
		//Receive String and convert to int. Instantiate when successful
		System.out.println("Enter an account number:");
		String intCheck = input.next();
		int accNumber = validateInt(intCheck);
		
		//Returns the value returned from findAccount(int accNumber)
		return findAccount(accNumber);	
	}
	
	/**
	  * This method receives an account number and iterates through the array of accounts, stopping at the last account added.
	  * Returns the index of the desired account if found. Returns -1 otherwise.
	  * @param accNumber
	  * 	The desired account number
	  * @return
	  * 	a value of type int
	 */
	public static int findAccount(int accNumber)
	{	
		//Check only the indices with accounts and return the index number if account is found.
		for(int i = 0; i < numAccounts; i++)
		{
			if(accounts[i].accNumber == accNumber)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	  * This method iterates through the array of accounts, stopping at the last account added.
	  * It then performs a monthly update on each account. Applying a monthly fee on chequing accounts,
	  * or accruing interest on savings accounts.
	 */
	public void monthlyUpdate()
	{
		//Apply the monthly update only on indices with accounts
		for(int i = 0; i < numAccounts; i++)
		{
			accounts[i].monthlyAccountUpdate();
		}
	}
	
	/*
	 *  This method receives a String and attempts to parse it to an double.
	 * Prompts user until a valid double value is input. Returns the value of the double if it has successfully been converted.
	*/
	private double validateDouble(String doubleCheck)
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
				isValid = true;
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
	
	/*
	 * This method receives a String and attempts to parse it to an int.
	 * Prompts user until a valid int value is input. Returns the value of the double if it has successfully been converted and is not a negative.
	*/
	private int validateInt(String intCheck)
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
