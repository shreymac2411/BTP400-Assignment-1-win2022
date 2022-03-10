package ConsoleApplication;

import application.table.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Represents the student module's functionality within the DB
 *
 * @author Wilson Ho
 */
public class StudentController {

    private boolean debug = false;
    private boolean loggedIn = false;

    private Student student;

    /**
     * Class constructor
     */
    public StudentController() {
        super();

        student = new Student();
        run();
    }

    /**
     * Custom constructor
     *
     * @param debug Controls running in debug mode
     */
    public StudentController(boolean debug) {
        super();

        this.debug = debug;

        if (debug) {
            System.out.println("StudentController(boolean debug)");

            this.loggedIn = true;
            TableController t = new TableController();
            t.addStudent(new Student(1000, "test student", new Date().toString(), "test", "Undergrad"));
            this.student = t.getStudent(1000);

        } else {
            student = new Student();
        }
    }

    /**
     * Handles all user IO and method calls
     */
    public void run() {
        if (debug)
            System.out.println("StudentController::Run()");

        System.out.println("please log in");
        Scanner s = new Scanner(System.in);

        String pw = "";
        int id = 0;

        while (!loggedIn) {
            try {
                System.out.println("please enter your Student ID");
                id = Integer.parseInt(s.next());
                System.out.println("please enter your password");
                pw = s.next();
                login(id, pw);
            } catch (NumberFormatException e) {
                System.out.println("Please make sure you enter a valid password and ID");
            }
        }

        System.out.println("Logged in as: Student #" + this.student.getStudentID());
        if (loggedIn) {
            System.out.println(
                    "Enter '1' to search a book by its name. Enter '2' to view a report of all books. Enter '3' to Request an issue by name. Enter '4' to view a list of borrowed books. " +
                            "Enter 'X' to exit.");

        } else
            return;
        String query = " ";
        String selection = "";
        while (!selection.equalsIgnoreCase("X")) {
            try {

                String temp = s.next();
                selection = temp;

                switch (selection) {
                    case "1": {
                        System.out.println("Search a book: Please enter the name of the book you would like to search.");
                        while (!query.equalsIgnoreCase("x")) {
                            try {
                                query = s.next();
                                this.searchBook(query);
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter a valid query.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "2": {
                        viewAllBooks();
                        break;
                    }
                    case "3": {
                        System.out.println("Request an issue: Please enter the name of the book you would like to be issued.");
                        while (!query.equalsIgnoreCase("")) {
                            try {
                                query = s.next();
                                this.requestIssue(query);
                                query = "";
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter a valid query.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        break;
                    }
                    case "4": {
                        viewBorrowedBooks();
                        break;
                    }
                    case "X": {
                        break;
                    }
                    case "_logout": {
                        selection = "x"; //you can remove this. just breaks out...
                        break;
                    }
                    default:
                        System.out.println("Please enter a response listed above.");
                        selection = "";
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
     * Logs the user in as a Student
     *
     * @param id The ID of the Student being logged in to
     * @param pw The Student's password
     * @return If the user was logged in
     */
    public boolean login(int id, String pw) {

        if (debug)
            System.out.println("Student::login()");

        if (loggedIn)
            return true;

        TableController controller = new TableController();

        Student student = controller.getStudent(id);

        if (student.getStudentName() == null || student.getStudentID() == 0) {
            System.out.println("Login unsuccessful. ID or password were incorrect.");
            return false;

        } else if (student.getStudentPassword().equals(pw)) {
            loggedIn = true;
            this.student = student;
            System.out.println("Login successful for student #" + this.student.getStudentID());
            new Database().log(new Date() + ": Student #" + this.student.getStudentID() + " logged in.");
            return true;

        } else {

            System.out.println("Login unsuccessful. ID or password were incorrect.");
            return false;
        }
    }

    /**
     * Searches for a Book by name
     *
     * @param bookName The name of the Book to search for
     * @return If the Book was found
     */
    public boolean searchBook(String bookName) {
        if (debug)
            System.out.println("searchBook()");

        if (!loggedIn)
            return false;
        ArrayList<Book> books = new TableController().getAllBooks();
        new Database().log(new Date() + ": Student #" + this.student.getStudentID() + " searchBook()");
        for (Book book : books) {
            if (book.getBookName().equalsIgnoreCase(bookName)) {
                System.out.println(book);

                if (debug) {
                    System.out.println("book found!");
                    return true;
                }
            }
        }
        System.out.println("book not found.");
        return false;
    }

    /**
     * Print all Books in the system to the screen
     */
    public void viewAllBooks() {
        if (debug)
            System.out.println("viewAllBooks");

        if (!loggedIn)
            return;

        TableController db = new TableController();
        db.viewBookTable();
        db.log(new Date() + ": Student #" + this.student.getStudentID() + " viewAllBooks()");
    }

    /**
     * Request a Book to be issued to the Student by ID
     *
     * @param id The ID of the Book to search for
     */
    public void requestIssue(int id) {
        if (debug)
            System.out.println("requestIssue(int id)");

        if (!loggedIn)
            return;

        TableController controller = new TableController();

        Book requestedBook = controller.getBook(id);

        if (requestedBook.equals(new Book())) {
            System.out.println("Book not found!");
            return;
        } else {
            if (requestedBook.isIssued()) {

                ArrayList<IssuedBook> issued = controller.getAllIssuedBooks();
                ArrayList<WaitTicket> tickets = controller.getAllWaitTickets();

                for (IssuedBook issue : issued) {
                    if (issue.getBookID() == id && issue.getStudentID() == this.student.getStudentID()) {

                        System.out.println("This book is currently issued to you.");
                        controller.log(new Date() + ": Student #" + this.student.getStudentID() + " issue request unsuccessful.");
                        return;
                    }
                }

                int count = 0;

                for (WaitTicket ticket : tickets) {
                    //count wait tickets for book
                    if (ticket.getItemID() == requestedBook.getBookID()) {
                        count++;
                    }
                    //if the student has a wait ticket for the book

                    if (ticket.getItemID() == requestedBook.getBookID() && ticket.getStudentID() == this.student.getStudentID()) {
                        System.out.println("You are already on the waiting list for this book.");
                        controller.log(new Date() + ": Student #" + this.student.getStudentID() + " issue request unsuccessful.");
                        return;
                    }
                }

                System.out.println("Book is already issued. A waiting ticket has been made for your request.");
                System.out.println("Your ticket is #" + (count + 1) + " in line for: " + requestedBook.getBookName());
                controller.addWaitTicket(new WaitTicket(controller.getFreeIndex(Database.getWaitTicketPath()), this.student.getStudentID(), new Date().toString(), requestedBook.getBookID()));
                controller.log(new Date() + ": Student #" + this.student.getStudentID() + " has been given a WaitTicket");

            } else {

                controller.addIssuedBook(new IssuedBook(controller.getFreeIndex(Database.getIssuedBookPath()), requestedBook.getBookID(), this.student.getStudentID(), new Date().toString()));

                if (controller.issueBook(id)) {
                    //updates book in books.txt
                    requestedBook.setIssued(true);
                    controller.deleteBook(requestedBook.getBookID());
                    controller.addBook(requestedBook);

                    System.out.println("Book is now issued");
                    controller.log(new Date() + ": Student #" + this.student.getStudentID() + " has requested to be issued Book: " + requestedBook.getBookName() + "(" + requestedBook.getBookID() + ")");
                } else {
                    System.out.println("Book could not be issued.");
                    controller.log(new Date() + ": Student #" + this.student.getStudentID() + " issue request unsuccessful.");

                }
            }
        }
    }

    /**
     * Request a Book to be issued to the Student by name
     *
     * @param bookName The name of the Book to search for
     */
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
        ArrayList<Book> found = new ArrayList<>();

        for (Book book : books) {
            if (book.getBookName().equalsIgnoreCase(bookName)) {
                //requestedBook = book;
                found.add(book);
            }

        }
        if (found.size() < 0) {
            System.out.println("Book not found!");
            return;

        } else {
            for (Book foundbook : found) {
                requestedBook = foundbook;
                if (!foundbook.isIssued() && foundbook.getBookID() != 0 && foundbook.getISBN() != 0) {
                    break;
                }
            }
        }

        if (requestedBook.getBookName() == null || requestedBook.getBookID() == 0) {
            System.out.println("Book not found!");
            return;
        }
        if (requestedBook.isIssued()) {

            ArrayList<IssuedBook> issued = controller.getAllIssuedBooks();
            ArrayList<WaitTicket> tickets = controller.getAllWaitTickets();

            for (IssuedBook issue : issued) {
                if (issue.getBookID() == requestedBook.getBookID() && issue.getStudentID() == this.student.getStudentID()) {

                    System.out.println("This book is currently issued to you.");
                    controller.log(new Date() + ": Student #" + this.student.getStudentID() + " issue request unsuccessful.");
                    return;
                }
            }

            int count = 0;

            for (WaitTicket ticket : tickets) {
                if (ticket.getItemID() == requestedBook.getBookID()) {
                    count++;
                }
                if (ticket.getItemID() == requestedBook.getBookID() && ticket.getStudentID() == this.student.getStudentID()) {
                    System.out.println("You are already on the waiting list for this book.");
                    controller.log(new Date() + ": Student #" + this.student.getStudentID() + " issue request unsuccessful.");
                    return;
                }
            }
            System.out.println("Book is already issued. A waiting ticket has been made for your request.");
            System.out.println("Your ticket is #" + (count + 1) + " in line for: " + requestedBook.getBookName());
            controller.addWaitTicket(new WaitTicket(controller.getFreeIndex(Database.getWaitTicketPath()), this.student.getStudentID(), new Date().toString(), requestedBook.getBookID()));

            controller.log(new Date() + ": Student #" + this.student.getStudentID() + " has been given a WaitTicket");

        } else {

            controller.addIssuedBook(new IssuedBook(controller.getFreeIndex(Database.getIssuedBookPath()), requestedBook.getBookID(), this.student.getStudentID(), new Date().toString()));

            if (controller.issueBook(requestedBook.getBookID())) {
                //updates book in books.txt
                requestedBook.setIssued(true);
                controller.deleteBook(requestedBook.getBookID());
                controller.addBook(requestedBook);

                System.out.println("Book is now issued");
                controller.log(new Date() + ": Student #" + this.student.getStudentID() + " has requested to be issued Book: " + requestedBook.getBookName() + "(" + requestedBook.getBookID() + ")");
            } else {
                System.out.println("Book could not be issued.");
                controller.log(new Date() + ": Student #" + this.student.getStudentID() + " issue request unsuccessful.");

            }
        }

    }

    /**
     * print all Issued Books in the system to the screen
     */
    public void viewBorrowedBooks() {
        if (debug)
            System.out.println("viewBorrowedBooks()");

        if (!loggedIn)
            return;

        new TableController().viewissuedBookTable();

        new Database().log(new Date() + ": Student #" + this.student.getStudentID() + " viewBorrowedBooks()");
    }

}
