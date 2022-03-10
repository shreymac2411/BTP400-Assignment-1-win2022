package utill;

import application.table.Librarian;
import java.util.Date;
import java.util.Scanner;

/**
 * Represents the Admin module's functionality within the DB
 *
 * @author Mohammad
 */
public class AdminController {

	private boolean debug = false;
	private boolean loggedIn = false;

	/**
	 * Class constructor
	 */
	public AdminController() {
		super();
		//run(); //only used for console application
	}

	public boolean checkLogin() {
		return loggedIn;
	}
	
	/**
	 * Custom constructor
	 *
	 * @param debug Controls running in debug mode
	 */
	public AdminController(boolean debug) {
		super();
		this.debug = debug;
		if (debug)
			System.out.println("AdminController(boolean debug)");

	}

	/**
	 * Handles the user IO and method calls
	 */
	public void run() {
		if (debug)
			System.out.println("Admin::run()");

		System.out.println("please log in");
		Scanner scanner = new Scanner(System.in);
		String un, pw;

		while (!loggedIn) {
			try {
				System.out.println("Enter Administrator Username");
				un = scanner.next();
				System.out.println("Enter Administrator Password");
				pw = scanner.next();
				login(un, pw);
			} catch (NumberFormatException e) {
				System.out.println("Please make sure you enter valid information");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!this.loggedIn)
			return;

		String selection = "";

		while (!selection.equalsIgnoreCase("x")) {
			System.out.println("Enter '1' to view all Librarians. " + "\nEnter '2' to register a new Librarian. "
					+ "\nEnter '3' to remove a Librarian. " + "\nEnter '4' to find a Librarian by ID. "
					+ "\nEnter 'X' to Exit.");
			try {
				String temp = scanner.next();
				selection = temp;

				switch (selection) {
				case "1": {

					System.out.println("View all Librarians.");

					viewAll();

					break;
				}
				case "2": {

					System.out.println("Register a Librarian. Please fill out the following fields.");

					Librarian toAdd = new Librarian();
					try {

						System.out.println("Enter the Librarian's name");
						toAdd.setName(scanner.next());
						System.out.println("Enter the Librarian's ID");
						toAdd.setId(scanner.nextInt());
						System.out.println("Enter the Librarian's password");
						toAdd.setPassword(scanner.next());

						if (register(toAdd))
							System.out.println("Librarian #" + toAdd.getId() + " Registered.");

						break;

					} catch (NumberFormatException e) {
						System.out.println("Please enter valid information.");
					} catch (Exception e) {
						e.printStackTrace();
					}

					break;
				}
				case "3": {
					System.out.println(
							"Remove Librarian. Please enter the ID of the Librarian you would like to delete.");

					try {
						remove(scanner.nextInt());
					} catch (NumberFormatException e) {
						System.out.println("Please enter valid information.");
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
				case "4": {
					System.out.println("Find Librarian. Please enter the ID of the Librarian you would like to find.");

					try {
						find(scanner.nextInt());
					} catch (NumberFormatException e) {
						System.out.println("Please enter valid information.");
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
				case "x": {
					break;
				}
				default:
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Please enter a response listed above.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Logs the user in as an Admin
	 *
	 * @param id       The Admin login ID
	 * @param password The Admin login password
	 * @return If the user was logged in
	 */
	public boolean login(String id, String password) {
		if (debug)
			System.out.println("Admin::login()");

		Database db = new Database();
		if (id.equals(db.getADMIN_ID()) && password.equals(db.getADMIN_PW())) {
			loggedIn = true;
			System.out.println("Successfully Logged in as Admin");
			db.log(new Date() + ": Admin Login");
			return true;
		}

		System.out.println("Login unsuccessful. \nID or password were incorrect.");
		return false;
	}

	/**
	 * Prints all Librarians in the system to the screen
	 */
	public void viewAll() {
		if (debug)
			System.out.println("viewAll()");

		if (!loggedIn)
			return;

		TableController db = new TableController();

		db.viewLibrarianTable();
		db.log(new Date() + ": Admin Viewed all Librarians registered in database.");

	}

	/**
	 * Register a new Librarian to the system
	 *
	 * @param librarian The Librarian object to add
	 * @return If the Librarian was added
	 */
	public boolean register(Librarian librarian) {
		if (debug)
			System.out.println("register()");

		if (!loggedIn)
			return false;

		TableController db = new TableController();
		if (db.addLibrarian(librarian)) {
			db.log(new Date() + ": Librarian #" + librarian.getId() + " Added to System");
			return true;
		} else
			return false;
	}

	/**
	 * Remove a Librarian from the system
	 *
	 * @param id The ID of the Librarian to search for
	 * @return If the Librarian was removed
	 */
	public boolean remove(int id) {
		if (debug)
			System.out.println("remove()");

		if (!loggedIn)
			return false;

		TableController db = new TableController();

		if (db.deleteLibrarian(id)) {
			db.log(new Date() + ": Librarian #" + id + " deleted from database.");
			return true;

		} else
			return false;

	}

	/**
	 * Searches for a Librarian by ID and prints them to screen
	 *
	 * @param id The ID of the Librarian to search for
	 */
	public void find(int id) {
		if (debug)
			System.out.println("find()");

		if (!loggedIn)
			return;

		TableController db = new TableController();

		db.getLibrarian(id).toString();
		db.log(new Date() + ": Admin searched for Librarian #" + id);
	}
}
