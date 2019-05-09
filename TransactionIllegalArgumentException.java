/*
 * Name: Zachary Sabourin
 * Student ID: 040940295
 * Course & Section: CST8132 311
 * Assignment: Lab 07
 * Date: March 31, 2019
 */

/**
  * The purpose of this class is to allow the instantiating of a custom exception object.
  * This class holds the logic behind the creation of the error message as well as a method to retrieve it.
  * @author Zachary Sabourin
  * @version 1.0
  * @since 1.8
 */
public class TransactionIllegalArgumentException extends IllegalArgumentException
{
	//Auto-generated serial user ID
	private static final long serialVersionUID = -2985622583207153481L;
	
	/**
	  * The unique error message for the exception. instantiated when a new exception is created and thrown
	 */
	public String errorMessage;
	
	/**
	  * The parameterized constructor for the TransactionIllegalArgument class.
	  * Receives a string as a parameter and instantiates the errorMessage variable with the value passed
	  * through the constructor.  
	  * @param errorMessage
	 */
	public TransactionIllegalArgumentException(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	
	/**
	  * This method retrieves the error message created when the exception is created and thrown
	  * @return
	  * 	A value of type String
	 */
	public String getMessage()
	{
		return errorMessage;
	}
	
}
