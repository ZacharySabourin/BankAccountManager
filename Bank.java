import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Formatter;
import java.util.NoSuchElementException;

/**
 * The purpose of this class is to allow the initialization of a Bank object with a specified bank size or the default size of 1000 accounts.
 * This class holds an arraylist of bank accounts and holds the logic required to add a bank account of a specific type to the arraylist.
 * Allows the user to update an account in the arraylist, display information for every account in the arraylist or for a specific account,
 * find a specific account in the arraylist, and perform monthly updates on every account in the arraylist. This class also allows for the 
 * accessing of a specified input file, creating bank accounts based off the information from the file. Also allows for the accessing of a specified 
 * output file, outputting onto it the information of each bank account in the ArrayList.
 * This class also handles exceptions errors regarding user input and file usage.
 * @author Zachary Sabourin
 * @version 1.0
 * @since 1.8
*/
public class Bank 
{
	//Arraylist of BankAccount objects. Declared as static to allow being used in the static findAccount(int) method.
	private static ArrayList<BankAccount> accountList;
	
	//Default maximum size of the bank array. Can be changed using the parameterized constructor
	private int sizeBank = 1000;
	
	//Current number of accounts. Declared as static to allow being used in the static findAccount(int) method.
	private static int numAccounts = 0;
	
	//Scanner object to allow user input
	private Scanner input = new Scanner(System.in);
	
	//Scanner object to allow reading information from a txt file
	private Scanner fileInput;
	
	//Formatter object to allw the output of bank account information onto a txt file
	private static Formatter output;
	
	/**
	  * Default constructor for the Bank class. Instantiates an arraylist of BankAccount objects using the default size of 1000.
	 */
	public Bank()
	{
		accountList = new ArrayList<BankAccount>(sizeBank);
	}
	
	/**
	  * Overloaded constructor for the Bank class. Changes the value of the bank size with a desired amount.
	  * Instantiates an Arraylist of bankAccount objects with the desired size
	  * @param sizeBank
	  * 	The desired bank size.
	 */
	public Bank(int sizeBank)
	{
		this.sizeBank = sizeBank;
		accountList = new ArrayList<BankAccount>(sizeBank);
	}
	
	/**
	  * It displays the current number of accounts, prompts the user for a desired account type and calls the addBankAccount() method
	  * which instantiates the account object's attributes. Returns false if an account was not added.
	  * @return
	  * 	A value of type boolean
	 */
	public boolean addAccount()
	{		
		//Display amount of current accounts in the bank
		int currentAccIndex = numAccounts + 1;
		System.out.println("Enter details of account holder " + currentAccIndex
							+ "\n-------------------------");
		
		//Prompt for account type. Not continuing until valid input is received
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
			accountList.add(new SavingsAccount());
		}
		else
		{
			accountList.add(new ChequingAccount());
		}
		
		//If account added successful
		if(accountList.get(numAccounts).addBankAccount())
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
	  * This method allows the user to search for an account, prompts the user for a desired action (withdraw/deposit),
	  * then asks the user to input a value. This method handles the NumberFormatException if the number was not valid and the
	  * custom TransactionIllegalArgumentException if the user attempts to withdraw/deposit a negative value.
	  * Displays an error message otherwise
	 */
	public void updateAccount()
	{
		//Calls a method that attempts to find an account
		int accIndex = (int) findAccount();
		
		//If the method returns -1, displays error message. Otherwise allows the user to withdraw/deposit.
		if(accIndex == -1)
		{
			System.out.println("Account not found");
		}
		else
		{
			//Prompt user for deposit or withdraw
			System.out.println("Deposit or withdraw? (+ to deposit, - to withdraw):");
			String updateChoice = input.next();
			
			while(!updateChoice.equals("-") && !updateChoice.equals("+"))
			{
				System.out.println("Enter a valid choice: ");
				updateChoice = input.next();
			}
			
			String doubleCheck;
			double updateAmt = 0;
			
			//Loop control variable
			boolean isValid = false;
			
			if(updateChoice.equals("+"))
			{
				do
				{
					//Receive String and attempt to convert to double. Handle any exceptions that occur
					try
					{
						System.out.println("Enter an amount to deposit:");
						doubleCheck = input.next();	
						updateAmt = validateDouble(doubleCheck);
						isValid = true;
					}
					catch(NumberFormatException numberFormatException)
					{
						System.err.println("Invalid number");
					}
					
				}
				while(!isValid);
				
				//Attempt to deposit. Handle any exceptions that may occur.
				try
				{
					accountList.get(accIndex).deposit(updateAmt);
			
				}
				catch(TransactionIllegalArgumentException transactionException)
				{
					System.err.print(transactionException.getMessage());
				}
			}
				
			else
			{
				do
				{
					//Receive a String and attempt to convert to double. Handle any exceptions that occur
					try
					{
						System.out.println("Enter an amount to withdraw:");
						doubleCheck = input.next();	
						updateAmt = validateDouble(doubleCheck);
						isValid = true;
					}
					catch(NumberFormatException numberFormatException)
					{
						System.err.println("Invalid number");
					}
					
				}
				while(!isValid);
				
				//Attempt to withdraw. Handle any exceptions that occur
				try
				{
					accountList.get(accIndex).withdraw(updateAmt);
			
				}
				catch(TransactionIllegalArgumentException transactionException)
				{
					System.err.print(transactionException.getMessage());
				}
			}
		}
	}
	
	/**
	  * This method allows the user to search for an account and displays the account information if it is found.
	  * Displays an error message otherwise
	 */
	public void displayAccount()
	{	
		//Calls a method that attempts to find an account
		int accIndex = (int) findAccount();
		
		//If the method returns -1, displays error message. Otherwise displays account information.
		if(accIndex == -1)
		{
			System.out.println("Account not found");
		}
		else
		{
			System.out.println(accountList.get(accIndex).toString());
		}		
	}

	/**
	  * This method iterates through the arraylist of accounts, stopping at the last account added.
	  * It then displays each accounts information in the console and outputs it in text form into a txt file.
	  * It also handles any exceptions that occur.
	 */
	public void printAccountdetails()
	{
		//Attempt to open the output file, perform the desired actions and handle any exceptions
		try
		{
			openOutputFile();
			
			System.out.println("Number of account holders: " + numAccounts);
			
			//Display account information on indices with accounts
			for(int i = 0; i < numAccounts; i++)
			{
				//Print the accounts information into the file. Each on a new line
				output.format("%s %n", accountList.get(i).toString());
				
				//Print the accounts information onto the console, one line at a time.
				System.out.println(accountList.get(i).toString());
			}
			
			closeOutputFile();
		}
		catch(FileNotFoundException fileNotFoundException)
		{
			System.err.println("File not found");
		}		
	}
	
	//This method prompts the user for a valid account number and returns the value received
	//by calling findAccount(long accNumber)
	private long findAccount()
	{	
		//loop control variable
		boolean isValid = false;
		
		long accNumber = 0;
		//Receive String and convert to long. Handle any exceptions that occur.
		//Instantiate when successful
		do
		{
			try
			{
				System.out.println("Enter an account number:");
				String longCheck = input.next();
				accNumber = validateLong(longCheck);
				
				isValid = true;
			}
			catch(NumberFormatException numberFormatException)
			{
				System.err.println("Invalid number");
			}
		}
		while(!isValid);
		
		//Returns the value returned from findAccount(int accNumber)
		return findAccount(accNumber);	
	}
	
	/**
	  * This method receives an account number and iterates through the arraylist of accounts, stopping at the last account added.
	  * Returns the index of the desired account if found. Returns -1 otherwise.
	  * @param accNumber
	  * 	The desired account number
	  * @return
	  * 	a value of type int
	 */
	public static long findAccount(long accNumber)
	{	
		//Check only the indices with accounts and return the index number if account is found.
		for(int i = 0; i < numAccounts; i++)
		{
			if(accountList.get(i).accNumber == accNumber)
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
			accountList.get(i).monthlyAccountUpdate();
		}
	}

	//This method instantiates the fileInput Scanner object using the desired txt file as the input.
	//Throws an IOException if the file is not found.
	private void openInputFile() throws IOException
	{
		fileInput = new Scanner(Paths.get("bankinput.txt"));
	}
	
	/**
	  * This method opens the desired input file, reads each line of information and creates either a chequing or savings
	  * account based on the information provided.
	 */
	public void readRecords()
	{
		//Attempt to open and read the file. Catching any exceptions that occur
		try
		{
			//open the file
			openInputFile();
			
			while(fileInput.hasNext())
			{
				//Instantiates a String with the first value found on each line of the file.
				//C or S expected
				String accountType = fileInput.next();
				
				//If c, create chequing account. Otherwise S is assumed, create Savings
				if(accountType.equalsIgnoreCase("c"))
				{
					accountList.add(new ChequingAccount(fileInput.next(), fileInput.next(), (long)fileInput.nextInt(), 
											fileInput.next(), fileInput.nextDouble(), fileInput.nextDouble()));
					System.out.println(accountList.get(numAccounts).toString());
					
					//increment the number of accounts to keep it up to date
					numAccounts++;
				}
				else
				{
					accountList.add(new SavingsAccount(fileInput.next(), fileInput.next(), (long)fileInput.nextInt(),
											fileInput.next(), fileInput.nextDouble(), fileInput.nextDouble()));
					System.out.println(accountList.get(numAccounts).toString());
					
					//increment the number of accounts to keep it up to date
					numAccounts++;
				}
			}
			
			//close the file
			closeInputFile();
			
			System.out.println("Accounts added successfully");
		}
		catch(IOException iOException)
		{
			System.err.println("Error opening file");
		}
		catch(NoSuchElementException noSuchElementException)
		{
			System.err.println("File improperly formed");
		}
		catch(IllegalStateException illegalStateException)
		{
			System.err.println("Error reading from file"); 
		}
	}
	
	//This method checks the status of the Scanner object tasked with reading from a txt file
	//and closes it if it is not already closed
	private void closeInputFile()
	{
		if(fileInput != null)
		{
			fileInput.close();
		}
	}
	
	//This method creates a now Formatter object and uses it to output to a txt file.
	//Throws a FileNotFoundException if unable to locate the desired output vile.
	private void openOutputFile() throws FileNotFoundException
	{
		output = new Formatter("bankoutput.txt");
	}
	
	//This method checks the status of the Formatter object and closes it if it is not already closed
	private void closeOutputFile()
	{
		if(output != null)
		{
			output.close();
		}
		
	}
	
	/*
	 * This method receives a String and attempts to parse it to an double.
	 * Throws an exception if the String could not be parsed to a double. 
	 * Returns the value of the double if successful.
	*/
	private double validateDouble(String doubleCheck) throws NumberFormatException
	{	
		//Value to be returned once parsing is successful
		double convertedDouble = Double.parseDouble(doubleCheck);
		
		//return the converted value
		return convertedDouble;
	}
	
	/*
	 * This method receives a String and attempts to parse it to a long.
	 * Throws an exception if the String could not be parsed to a long. 
	 * Returns the value of the long if successful.
	*/
	private long validateLong(String longCheck) throws NumberFormatException
	{		
		//Value to be returned once parsing is successful
		long convertedLong = Long.parseLong(longCheck);
		
		//return the converted value
		return convertedLong;
	}
}
