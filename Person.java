/*
 * Name: Zachary Sabourin
 * Student ID: 040940295
 * Course & Section: CST8132 311
 * Assignment: Assingment 1
 * Date: February 17, 2019
 */

/**
  * The purpose of this class is allow the initialization of a Person object that uses a first name, last name
  * phone number and email as parameters. This class allows the retrieval of the user's information through a few getter methods.
  * @author Zachary Sabourin
  * @version 1.0
  * @since 1.8
 */
public class Person 
{
	private String firstName;
	private String lastName;
	private int phoneNumber;
	private String email;	
	
	/**
	  * The parameterized constructor for the Person class. Takes 3 Strings and an integer as parameters.
	  * @param firstName
	  * 	The account holder's first name
	  * @param lastName
	  * 	The account holder's last name
	  * @param phoneNumber
	  * 	The account holder's phone number
	  * @param email
	  * 	The account holder's email
	 */
	public Person(String firstName, String lastName, int phoneNumber, String email)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
	
	/**
	  * This method retrieves the person's full name.
	  * @return
	  * 	String value that includes the firstName and lastName variables. 
	  * 	Concatenated with a space.
	 */
	public String getName()
	{
		return firstName + " " + lastName;
	}
	
	/**
	  * This method retrieves the person's phone number.
	  * @return
	  * 	Value of type int.
	 */
	public int getPhoneNumber()
	{
		return phoneNumber;
	}
	
	/**
	  * This method retrieves the person's email address.
	  * @return
	  * 	Value of type String.
	 */
	public String getEmail()
	{
		return email;
	}
}