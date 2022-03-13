package applicationTester;

import completeConsoleApplication.*;

import java.util.Date;

import completeConsoleApplication.table.Book;
import completeConsoleApplication.table.Librarian;

public class TesterModule {

    public static void main(String[] args) {

        TableController db = new TableController(true);

        new TestDB();

        StudentController st_1 = new StudentController(false);
        StudentController st_2 = new StudentController(false);
        StudentController st_3 = new StudentController(false);
        StudentController st_4 = new StudentController(false);

        LibrarianController lib = new LibrarianController(false);

        AdminController admin = new AdminController(false);

        st_1.login(1, "1234");
        st_2.login(2, "1234");
        st_3.login(12, "2222"); //wrong login
        st_3.login(3, "1234");

        lib.login(3, "34");//bad login
        lib.login(1, "1234");

        st_1.viewBorrowedBooks();
        st_1.viewAllBooks();
        st_1.searchBook("h31"); //not in library

        st_2.searchBook("test1");
        st_2.requestIssue("test1");
        st_2.viewAllBooks(); //should show now issued book "test1"

        st_2.requestIssue("test1"); //already issued
        st_1.requestIssue("test1"); //puts on waitlist
        st_1.requestIssue("test3");
        st_1.requestIssue("test1"); //already on waitlist

        st_3.requestIssue("test1"); //puts on waitlist
        st_3.requestIssue("test3"); //puts on waitlist

        st_4.viewBorrowedBooks();
        st_4.viewAllBooks();

        admin.remove(3);//wont run, not logged in
        admin.login("1admin", "1234"); //bad login
        admin.login("admin", "admin");
        admin.login("admin", "admin"); //wont run, already logged in
        admin.remove(3);//not in db
        admin.remove(1);
        admin.remove(1); //already deleted
        admin.viewAll();
        admin.register(new Librarian(-1, "1234", "Test Librarian")); //bad ID
        admin.register(new Librarian(2, "1234", "Test Librarian"));
        admin.register(new Librarian(2, "1234", "Librarian Librarian")); //already in
        admin.register(new Librarian(3, "1234", "Ms. Librarian"));
        admin.viewAll();
        admin.remove(2);
        admin.viewAll();
        admin.find(3); //not in
        admin.find(2);

        lib.returnBook(3, 35); //not in system
        lib.lib_viewBooks();
        lib.lib_addBook(new Book(10, "Test4", "Test Publisher", "Test Author", new Date().toString(), 221523, "test book", false));//already in
        lib.lib_addBook(new Book(db.getFreeIndex(Database.getBookPath()), "Test4", "Test Publisher", "Test Author", new Date().toString(), 221523, "test book", false));

        lib.lib_issueBook(4, 110); //book not in system
        lib.lib_issueBook(4, 4);
        lib.lib_searchBook(4); //not in system
        lib.lib_searchBook(221523);
        lib.lib_issueBook(4, 4); //already issued
        lib.lib_issueBook(4, 7);
        lib.lib_issueBook(3, 10);
        lib.lib_issueBook(5, 7); //wait list

        st_4.viewAllBooks();
        st_4.viewBorrowedBooks();

        db.viewWaitTicketTable();

        lib.returnBook(43, 2); //not in system
        lib.returnBook(4, 4);
        lib.returnBook(4, 4); //already returned
        lib.lib_issueBook(4, 4); //

        st_3.viewBorrowedBooks();

        st_1.searchBook("Test4");
        st_2.requestIssue("Test4");
        st_3.requestIssue("Test4");
        st_4.requestIssue("Test4");

        st_3.viewBorrowedBooks();

        //view tables
        db.viewWaitTicketTable();
        db.viewBookTable();
        db.viewStudentTable();
        db.viewLibrarianTable();
    }
}
