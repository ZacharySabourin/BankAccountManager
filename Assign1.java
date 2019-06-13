//Imported package to allow user input.
import java.util.Scanner;

/**
  * This class contains the main method where the entirety of the program executes. The program creates a bank and allows
  * the user to create chequing and savings accounts, withdraw/deposit money, run a monthly update, display account information, and read records from a txt file.
  * @author Zachary Sabourin
  * @version 1.0
  * @since 1.8
 */
public class Assign1 
{
	/**
	  * This method contains the main menu of the bank system. It initializes a new bank object, ensures the user's
	  * correct input, and executes specific actions based on the input. 
	  * Calls the addAccount(), updateAccount(), displayAccount(), printAccountDetails(), monthlyUpdate(), and readRecords() methods of the Bank class.
	  * @param args
	  * 	Unused
	 */
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		
		//Using the parameterized constructor to set an arraylist size of 10
		Bank bank = new Bank(10);
		
		//Variable used to keep the program in a state of activity.
		boolean programOn = true;
		
		//Until the user chooses to exit, continue to return to the menu.
		do
		{
			System.out.println("========================="
							+ "\na: Add new account"
							+ "\nu: Update an account"
							+ "\nd: Display an account"
							+ "\np: Print all accounts"
							+ "\nm: Run monthly update"
							+ "\nr: Read records"
							+ "\nq: Quit"
							+ "\n=========================");
			System.out.println("Enter your option: ");
			String menuChoice = input.next();
			
			//Allow upper and lower-case versions of the menu options.
			while(!menuChoice.equalsIgnoreCase("a") && !menuChoice.equalsIgnoreCase("u") && !menuChoice.equalsIgnoreCase("d") &&
					!menuChoice.equalsIgnoreCase("p") && !menuChoice.equalsIgnoreCase("m") && !menuChoice.equalsIgnoreCase("r") && !menuChoice.equalsIgnoreCase("q"))
			{
				System.out.println("Invalid - Select a valid option");
				menuChoice = input.next();
			}
			
			switch(menuChoice)
			{
				case "a":
				case "A":
					bank.addAccount();
					break;
					
				case "u":
				case "U":
					bank.updateAccount();
					break;
					
				case "d":
				case "D":
					bank.displayAccount();
					break;
					
				case "p":
				case "P":
					bank.printAccountdetails();
					break;
					
				case "m":
				case "M":
					bank.monthlyUpdate();
					break;
					
				case "r":
				case "R":
					bank.readRecords();
					break;
					
				case "q":
				case "Q":
					programOn = false;
					break;
			}
		}while(programOn);
		
		input.close();
	}
}
