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
        super();
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

            if (student.getStudentID() == id && student.getStudentPassword().equals(pw)) {

                loggedIn = true;
                this.student = student;

                System.out.println("Login successful for student #" + this.student.getStudentID());
                new Database().log(new Date() + ": Student #" + this.student.getStudentID() + " logged in.");
                return true;
            }

        }

        System.out.println("Login unsuccessful. ID or password were incorrect.");
        return false;
    }


    public boolean searchBook(String bookName) {
        if (debug)
            System.out.println("searchBook()");

        if (!loggedIn)
            return false;
        ArrayList<Book> books = new TableController().getAllBooks();
        new Database().log(new Date() + ": Student #" + this.student.getStudentID() + " searchBook()");
        for (Book book : books) {
            if (book.getBookName().equalsIgnoreCase(bookName)) {
                book.toString();

                if (debug) {
                    System.out.println("book found!");
                    return true;
                }
            }
        }
        if (debug)
            System.out.println("book not found.");
        return false;
    }

    public void viewAllBooks() {
        if (debug)
            System.out.println("viewAllBooks");

        if (!loggedIn)
            return;

        TableController db = new TableController();
        db.viewBookTable();
        db.log(new Date() + ": Student #" + this.student.getStudentID() + " viewAllBooks()");
    }

    public void requestIssue(int id) {
        if (debug)
            System.out.println("requestIssue(int id)");

        if (!loggedIn)
            return;

        TableController controller = new TableController();

        Book requestedBook = controller.getBook(id);
        //TODO - make this interact with the librarian instead
        if (requestedBook.equals(new Book())) {
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

                controller.log(new Date() + ": Student #" + this.student.getStudentID() + " has been given a WaitTicket");

            } else {

                controller.addIssuedBook(new IssuedBook(controller.getFreeIndex(Database.getIssuedBookPath()), requestedBook.getBookID(), this.student.getStudentID(), new Date()));

                if (controller.issueBook(id)) {
                    System.out.println("Book is now issued");
                    controller.log(new Date() + ": Student #" + this.student.getStudentID() + " has requested to be issued Book: " + requestedBook.getBookName() + "(" + requestedBook.getBookID() + ")");
                } else {
                    System.out.println("Book could not be issued.");
                    controller.log(new Date() + ": Student #" + this.student.getStudentID() + " issue request unsuccessful.");

                }
            }
        }
    }

    public void requestIssue(String bookName) {
        if (debug)
            System.out.println("requestIssue(String bookName)");

        if (!loggedIn)
            return;

        if (bookName.equalsIgnoreCase(""))
            return;

        TableController controller = new TableController();

        ArrayList<Book> books = controller.getAllBooks();
        Book requestedBook = new Book();

        for (Book book : books) {
            if (book.getBookName().equalsIgnoreCase(bookName)) {
                requestedBook = book;
                break;
            }
        }
        if (requestedBook.equals(new Book())) {
            System.out.println("Book not found!");
            return;
        }
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

            controller.log(new Date() + ": Student #" + this.student.getStudentID() + " has been given a WaitTicket");

        } else {

            controller.addIssuedBook(new IssuedBook(controller.getFreeIndex(Database.getIssuedBookPath()), requestedBook.getBookID(), this.student.getStudentID(), new Date()));

            if (controller.issueBook(requestedBook.getBookID())) {
                System.out.println("Book is now issued");
                controller.log(new Date() + ": Student #" + this.student.getStudentID() + " has requested to be issued Book: " + requestedBook.getBookName() + "(" + requestedBook.getBookID() + ")");
            } else {
                System.out.println("Book could not be issued.");
                controller.log(new Date() + ": Student #" + this.student.getStudentID() + " issue request unsuccessful.");

            }
        }

    }

    public void viewBorrowedBooks() {
        if (debug)
            System.out.println("viewBorrowedBooks()");

        if (!loggedIn)
            return;

        new TableController().viewissuedBookTable();

        new Database().log(new Date() + ": Student #" + this.student.getStudentID() + " viewBorrowedBooks()");
    }
}
