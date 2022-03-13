package completeConsoleApplication;

import completeConsoleApplication.table.Book;
import completeConsoleApplication.table.Librarian;
import utill.AdminController;
import utill.LibrarianController;
import utill.StudentController;
import utill.TableController;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Module {
	/**
	 * Main method
	 *
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		TableController db = new TableController(false);

		Scanner scan = new Scanner(System.in);

		String input = "";

		while (!input.equalsIgnoreCase("quit")) {
			System.out.println("Would you like to log in as: \n'1' - Admin, \n'2' - Student, \n'3' - Librarian?");
			System.out.println("Enter 'quit' to exit the application. \nEnter 'x' within the modules to logout.");

			try {
				input = scan.next();

				switch (input) {
				case "1": {
					AdminController admin = new AdminController();
					break;
				}
				case "2": {
					StudentController student = new StudentController();
					break;
				}
				case "3": {
					LibrarianController librarian = new LibrarianController();
					break;
				}
				case "quit": {
					System.out.println("Goodbye!");
					break;
				}
				default:
					break;
				}

			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid selection.");
			} catch (InputMismatchException e) {
				System.out.println("Please enter a valid selection.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
