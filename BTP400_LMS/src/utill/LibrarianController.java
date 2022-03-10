package utill;

import application.table.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Represents the Librarian module's functionality within the DB
 *
 * @author Matthew Maghakian
 */
public class LibrarianController {

    private boolean debug = false;
    private boolean loggedIn = false;

    private Librarian librarian;

    /**
     * Class constructor
     */
    public LibrarianController() {
        super();

        librarian = new Librarian();
        run();
    }

    /**
     * Custom constructor
     *
     * @param debug Controls debug mode
     */
    public LibrarianController(boolean debug) {

        if (debug)
            System.out.println("LibrarianController(boolean isLoggedIn)");
        this.debug = debug;
        librarian = new Librarian();

        if (!debug)
            librarian = new Librarian();
    }

    /**
     * Handles the user IO and use of methods
     */
    public void run() {
        if (debug)
            System.out.println("LibrarianController::run()");

        System.out.println("please log in");
        Scanner s = new Scanner(System.in);
        TableController controller = new TableController();

        String pw = "";
        int id = 0;

        while (!loggedIn) {
            try {
                System.out.println("please enter your ID");
                id = Integer.parseInt(s.next());
                System.out.println("please enter your password");
                pw = s.next();
                login(id, pw);
            } catch (NumberFormatException e) {
                System.out.println("Please make sure you enter a valid password and ID");
            }
        }

        if (loggedIn) {
            System.out.println(
                    "Enter '1' to add a book. " +
                            "Enter '2' to issue a book. " +
                            "Enter '3' return a book. " +
                            "Enter '4' to search a book by ISBN. " +
                            "Enter '5' to view all books" +
                            "Enter 'X' to exit.");

        } else
            return;

        String selection = "";

        while (!selection.equalsIgnoreCase("X")) {
            try {

                String temp = s.next();
                selection = temp;

                switch (selection) {
                    case "1": {
                        System.out.println("Add a Book. Please fill out the following book information.");

                        Book toAdd = new Book();
                        try {

                            System.out.println("Enter the book ISBN");
                            toAdd.setISBN(s.nextInt());

                            ArrayList<Book> temp_array = controller.getBooksByISBN(toAdd.getISBN());
                            if (!temp_array.isEmpty()) {
                                toAdd = temp_array.get(0);
                                toAdd.setBookID(controller.getFreeIndex(Database.getBookPath()));
                                toAdd.setIssued(false);
                                if (lib_addBook(toAdd)) {
                                    System.out.println("book added.");

                                } else {
                                    System.out.println("book could not be added.");

                                }

                                break;
                            } else {
                                System.out.println("Enter the book Name");
                                toAdd.setBookName(s.next());
                                System.out.println("Enter the book Author");
                                toAdd.setBookAuthor(s.next());
                                System.out.println("Enter the book Publisher");
                                toAdd.setBookPublisher(s.next());
                                System.out.println("Enter the date published");
                                toAdd.setDatePublished(s.next());
                                System.out.println("Enter the book's description");
                                toAdd.setDesc(s.next());
                                toAdd.setIssued(false);
                                toAdd.setBookID(controller.getFreeIndex(Database.getBookPath()));

                                if (lib_addBook(toAdd)) {
                                    System.out.println("book added.");

                                } else {
                                    System.out.println("book could not be added.");

                                }

                                break;
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a response listed above.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                    case "2": {

                        int std_id = 0;
                        int book_id = 0;

                        System.out.println("Issue a Book. Please fill out the following information");

                        try {
                            System.out.println("Enter Student ID");
                            std_id = s.nextInt();

                            System.out.println("Enter Book ID");
                            book_id = s.nextInt();

                            if (lib_issueBook(std_id, book_id)) {
                                System.out.println("Book issued.");

                            } else {
                                System.out.println("Book could not be issued.");

                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a response listed above.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "3": {
                        int book_id = 0;
                        int std_id = 0;

                        System.out.println("Return a Book. Please fill out the following information.");

                        try {

                            System.out.println("Enter Book ID");
                            book_id = s.nextInt();

                            System.out.println("Enter Student ID");
                            std_id = s.nextInt();

                            if (returnBook(book_id, std_id)) {
                                System.out.println("Book issued.");

                            } else {
                                System.out.println("Book could not be issued.");

                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a response listed above.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "4": {

                        System.out.println("Search By ISBN. Please fill out the following information.");

                        try {
                            System.out.println("Enter ISBN");

                            lib_searchBook(s.nextInt());

                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a response listed above.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "5": {

                        System.out.println("View all books.");

                        lib_viewBooks();

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
     * Logs the user into the system as a Librarian
     *
     * @param id The ID of the Librarian to log in to
     * @param pw The Librarian's password
     * @return If the Librarian was logged in
     */
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
            System.out.println("Logged in as: Librarian #" + this.librarian.getId() + ".");
            controller.log(new Date() + ": Librarian #" + id + " logged in.");
            return true;

        } else {

            System.out.println("Login unsuccessful. ID or password were incorrect.");
            return false;
        }
    }

    /**
     * Adds a Book to the system
     *
     * @param book The Book object to add
     * @return If the Book was added
     */
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

    /**
     * Issued a Book and updates the system
     *
     * @param student_id The ID of the Student being issued the Book
     * @param book_id    The ID of the Book being issued
     * @return If the Book was issued
     */
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

                controller.addIssuedBook(new IssuedBook(controller.getFreeIndex(Database.getIssuedBookPath()), requestedBook.getBookID(), student_id, new Date().toString()));

                if (controller.issueBook(book_id)) {
                    //updates book in books.txt
                    requestedBook.setIssued(true);
                    controller.deleteBook(requestedBook.getBookID());
                    controller.addBook(requestedBook);

                    System.out.println("Book is now issued");
                    controller.log(new Date() + ": Student #" + student_id + " has been issued Book: " + requestedBook.getBookName() + "(" + requestedBook.getBookID() + ")");
                    return true;
                } else {
                    System.out.println("Book could not be issued.");
                    controller.log(new Date() + ": Student #" + student_id + " issue request unsuccessful.");
                    return false;
                }
            }
        }
    }

    /**
     * Returns a Book to the Library
     *
     * @param book_id    The ID of the Book being returned
     * @param student_id The ID of the Student returning the book
     * @return If the Book was returned
     */
    public boolean returnBook(int book_id, int student_id) {
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
                System.out.println("yo");
                controller.viewissuedBookTable();

                controller.deleteIssuedBook(issuedBook.getIssueID());

                controller.viewissuedBookTable();

                book.setIssued(false);
                controller.deleteBook(book_id);
                controller.addBook(book);
                return_val = true;
                System.out.println("Student #" + student_id + " has returned book #" + book_id + ".");
                controller.log(new Date() + ": Student #" + student_id + " has returned book #" + book_id + ".");
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

    /**
     * Searches for a Book and displays the result to the screen
     *
     * @param isbn The ISBN of the Book to search for
     */
    public void lib_searchBook(int isbn) {
        if (debug)
            System.out.println("lib_searchBook()");

        if (!loggedIn)
            return;

        TableController controller = new TableController();
        ArrayList<Book> books = controller.getBooksByISBN(isbn);
        if (books.isEmpty()) {
            System.out.println("No books with ISBN: " + isbn + " were found.");
        } else {
            controller.log(new Date() + ": Librarian #" + this.librarian.getId() + " viewed book(s) " + isbn + ".");
            System.out.println(books);
        }

    }

    /**
     * Prints all Books in the system to the screen
     */
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