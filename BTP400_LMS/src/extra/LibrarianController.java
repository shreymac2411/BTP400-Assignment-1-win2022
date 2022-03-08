package ca.seneca.btp400.library;

import ca.seneca.btp400.library.table.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents the Librarian module's functionality within the DB
 *
 * @author Matthew Maghakian
 */
public class LibrarianController {

    private boolean debug = false;
    private boolean loggedIn = false;
    private Librarian librarian;

    public LibrarianController() {
        super();

        librarian = new Librarian();
        run();
    }

    public LibrarianController(boolean debug) {

        if (debug)
            System.out.println("LibrarianController(boolean isLoggedIn)");
        this.debug = debug;
        librarian = new Librarian();
    }

    public void run() {
        if (debug)
            System.out.println("LibrarianController::run()");
        return;
    }

    public boolean login(int id, String pw) {
        if (debug)
            System.out.println("Librarian::login()");

        TableController controller = new TableController();

        Librarian librarian = controller.getLibrarian(id);

        if (librarian.getId() == 0 || librarian.getName() == null || librarian.getPassword() == null) {

            System.out.println("Login unsuccessful. ID or password were incorrect.");
            return false;

        } else if (librarian.getPassword().equals(pw)) {

            loggedIn = true;
            this.librarian = librarian;
            System.out.println("Librarian #" + id + " successfully logged in.");
            controller.log(new Date() + ": Librarian #" + id + " logged in.");
            return true;

        } else {

            System.out.println("Login unsuccessful. ID or password were incorrect.");
            return false;
        }
    }

    public boolean lib_addBook(Book book) {
        if (debug)
            System.out.println("lib_addBook()");

        if (!loggedIn)
            return false;

        TableController controller = new TableController();

        if (controller.addBook(book)) {
            controller.log(new Date() + ": Book '" + book.getBookName() + "' added to system.");
            return true;
        } else {
            return false;
        }
    }

    public boolean lib_issueBook(int student_id, int book_id) {
        if (debug)
            System.out.println("lib_issueBook()");

        if (!loggedIn)
            return false;

        TableController controller = new TableController();
        Book book = controller.getBook(book_id);
        if (book.equals(new Book())) {
            System.out.println("Book not found.");
            return false;
        } else if (book.isIssued()) {
            ArrayList<IssuedBook> issuedBooks = controller.getAllIssuedBooks();
            for (IssuedBook issuedBook : issuedBooks) {
                if (issuedBook.getBookID() == book_id && issuedBook.getStudentID() == student_id) {
                    System.out.println("This book is already issued to the student.");
                    return false;
                }
            }

        } else {
            if (controller.addIssuedBook(new IssuedBook(controller.getFreeIndex(Database.getIssuedBookPath()), book_id, student_id, new Date().toString()))) {
                book.setIssued(true);
                if (debug)
                    controller.viewBookTable();
                controller.deleteBook(book.getBookID());
                if (debug)
                    controller.viewBookTable();
                controller.addBook(book);
                if (debug)
                    controller.viewBookTable();
                System.out.println("Book has been issued to student #" + student_id + ".");

                controller.log(new Date() + ": Librarian #" + this.librarian.getId() + " issued book '" + book.getBookName() + "' (" + book_id + ") to student #" + student_id + ".");
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean returnBook(int student_id, int book_id) {
        if (debug)
            System.out.println("returnBook()");

        if (!loggedIn)
            return false;

        boolean return_val = false;

        TableController controller = new TableController();

        ArrayList<IssuedBook> issuedBooks = controller.getAllIssuedBooks();

        ArrayList<WaitTicket> waitTickets = controller.getAllWaitTickets();

        Book book = controller.getBook(book_id);

        for (IssuedBook issuedBook : issuedBooks) {
            if (issuedBook.getBookID() == book_id && issuedBook.getStudentID() == student_id) {
                controller.deleteIssuedBook(issuedBook.getIssueID());
                book.setIssued(false);
                controller.deleteBook(book_id);
                controller.addBook(book);
                return_val = true;
                controller.log(new Date() + ": Student #" + student_id + " has returned book #" + book_id + ".");

            }
        }
        if (waitTickets.size() > 0) {
            for (WaitTicket waitTicket : waitTickets) {
                if (waitTicket.getItemID() == book_id) {
                    controller.deleteWaitTicket(waitTicket.getTicketID());
                    lib_issueBook(waitTicket.getStudentID(), book_id);
                    controller.log(new Date() + ": Librarian #" + this.librarian.getId() + " has updated the latest wait ticket for book #" + book_id + ".");
                    return true;
                }
            }
        }

        return return_val;
    }

    public void lib_viewBooks() {
        if (debug)
            System.out.println("lib_viewBooks");

        if (!loggedIn)
            return;

        TableController controller = new TableController();

        controller.viewBookTable();

        controller.log(new Date() + ": Librarian #" + this.librarian.getId() + " has viewed all books");

    }
}