package ca.seneca.btp400.library;


import ca.seneca.btp400.library.table.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Represents the student module's functionality within the GUI
 *
 * @author Wilson Ho
 */
public class StudentController {
    private boolean debug = false;

    //provides access to methods
    private boolean loggedIn = false;

    private Student student = new Student();

    public StudentController() {

    }

    public StudentController(boolean debug) {
        super();

        this.debug = debug;

        if (debug) {
            System.out.println("StudentController(boolean debug)");
            loggedIn = true;

            TableController t = new TableController();
            t.addStudent(new Student(1000, "test student", new Date(), 100, "test"));
            this.student = t.getStudent(1000);
        }
    }

    public void run() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please log In");

    }

    public boolean login(int id, String pw) {
        if (debug)
            System.out.println("login()");
        if (loggedIn)
            return true;

        ArrayList<Student> students = new TableController().getAllStudents();
        for (Student student : students) {

            if (student.getStudentID() == id && student.getStudentPassword() == pw) {
                loggedIn = true;
                this.student = student;
                return true;
            }

        }
        return false;
    }

    public String format(String bookName) {
        if (debug)
            System.out.println("format()");

        String[] splitWord = bookName.split(" ");
        String capitalizedWord = "";
        for (int i = 0; i < splitWord.length; i++) {
            capitalizedWord += splitWord[i].substring(0, 1).toUpperCase() + splitWord[i].substring(1) + " ";
        }
        String trimName = capitalizedWord.trim();
        String formatBookName = trimName.replace(" ", "_");
        return formatBookName;
    }

    public boolean searchBook(String bookName) {
        if (debug)
            System.out.println("searchBook()");
        if (!loggedIn)
            return false;

        ArrayList<Book> books = new TableController().getAllBooks();

        for (Book book : books) {
            if (book.getBookName() == bookName) {
                book.toString();
                return true;
            }
        }
        return false;

    }


    public void viewAllBooks() {
        new TableController().viewBookTable();
    }

    public void requestIssue(int id) {
        if (debug)
            System.out.println("requestIssue(int id)");

        if (!loggedIn)
            return;

        TableController controller = new TableController();

        Book requestedBook = controller.getBook(id);

        if (requestedBook == (new Book())) {
            System.out.println("Book not found!");
            return;
        } else {
            if (requestedBook.isIssued()) {
                System.out.println("Book is already issued. A waiting ticket has been made for your request.");

                ArrayList<WaitTicket> tickets = controller.getAllWaitTickets();
                controller.addWaitTicket(new WaitTicket(controller.getFreeIndex(Database.getWaitTicketPath()), this.student.getStudentID(), new Date(), requestedBook.getBookID()));
                int count = 0;

                for (WaitTicket ticket : tickets) {
                    if (ticket.getItemID() == requestedBook.getBookID()) {
                        count++;
                    }
                }

                System.out.println("Your ticket is " + (count + 1) + " in line for: " + requestedBook.getBookName());
            }
        }
    }

    public void requestIssue(String bookName) {
        if (debug)
            System.out.println("requestIssue(String bookName)");

        if (!loggedIn)
            return;


    }


}
