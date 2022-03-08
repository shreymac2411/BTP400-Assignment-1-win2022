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

        if (!debug)
            run();
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

        Book requestedBook = controller.getBook(book_id);

        if (requestedBook.equals(new Book())) {
            System.out.println("Book not found!");
            return false;
        } else {
            if (requestedBook.isIssued()) {

                ArrayList<IssuedBook> issued = controller.getAllIssuedBooks();
                ArrayList<WaitTicket> tickets = controller.getAllWaitTickets();

                for (IssuedBook issue : issued) {
                    if (issue.getBookID() == book_id && issue.getStudentID() == student_id) {

                        System.out.println("This book is currently issued to you.");
                        controller.log(new Date() + ": Student #" + student_id + " issue request unsuccessful.");
                        return false;
                    }
                }

                int count = 0;

                for (WaitTicket ticket : tickets) {
                    //count wait tickets for book
                    if (ticket.getItemID() == requestedBook.getBookID()) {
                        count++;
                    }

                    //if the student has a wait ticket for the book
                    if (ticket.getItemID() == requestedBook.getBookID() && ticket.getStudentID() == student_id) {
                        System.out.println("You are already on the waiting list for this book.");
                        controller.log(new Date() + ": Student #" + student_id + " issue request unsuccessful.");
                        return false;
                    }
                }

                System.out.println("Book is already issued. A waiting ticket has been made for your request.");
                System.out.println("Your ticket is #" + (count + 1) + " in line for: " + requestedBook.getBookName());
                controller.addWaitTicket(new WaitTicket(controller.getFreeIndex(Database.getWaitTicketPath()), student_id, new Date().toString(), requestedBook.getBookID()));
                controller.log(new Date() + ": Student #" + student_id + " has been given a WaitTicket");
                return true;

            } else {

                controller.addIssuedBook(new IssuedBook(controller.getFreeIndex(Database.getIssuedBookPath()), requestedBook.getBookID(),student_id, new Date().toString()));

                if (controller.issueBook(book_id)) {
                    //updates book in books.txt
                    requestedBook.setIssued(true);
                    controller.deleteBook(requestedBook.getBookID());
                    controller.addBook(requestedBook);

                    System.out.println("Book is now issued");
                    controller.log(new Date() + ": Student #" + student_id + " has requested to be issued Book: " + requestedBook.getBookName() + "(" + requestedBook.getBookID() + ")");
                    return true;
                } else {
                    System.out.println("Book could not be issued.");
                    controller.log(new Date() + ": Student #" + student_id + " issue request unsuccessful.");
                    return false;
                }
            }
        }
    }

    public boolean returnBook(int book_id) {
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
            System.out.println();
            System.out.println(issuedBook);
            System.out.println(book_id);
            System.out.println();
            if (issuedBook.getBookID() == book_id) {
                System.out.println("SAJDHSAKJHSADKHJASDHKJSADKHJA");
                controller.viewissuedBookTable();

                controller.deleteIssuedBook(issuedBook.getIssueID());

                controller.viewissuedBookTable();

                book.setIssued(false);
                controller.deleteBook(book_id);
                controller.addBook(book);
                return_val = true;
                controller.log(new Date() + ": Student #" + issuedBook.getStudentID() + " has returned book #" + book_id + ".");
                break;
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