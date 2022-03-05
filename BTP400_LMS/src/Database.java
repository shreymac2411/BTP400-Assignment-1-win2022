package ca.seneca.btp400.library;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * this will handle the .txt files used as a database, acting as the medium our Modules will use for their functionality
 * @version 0.0
 * @author Matthew Maghakian
 */
public class Database {

    //user paths
    private static Path studentPath = FileSystems.getDefault().getPath("table", "student.txt");
    private static Path librarianPath = FileSystems.getDefault().getPath("table", "book.txt");
    //db item paths
    private static Path bookPath = FileSystems.getDefault().getPath("table", "book.txt");
    private static Path itemPath = FileSystems.getDefault().getPath("table", "book.txt");
    private static Path waitTicketPath = FileSystems.getDefault().getPath("table", "book.txt");
    private static Path issuedBookPath = FileSystems.getDefault().getPath("table", "book.txt");
    //logs
    private static Path logsPath = FileSystems.getDefault().getPath("db", "log.txt");
    //config
    private static Path configPath = FileSystems.getDefault().getPath("db", "log.txt");
    //hold the admin login for this database
    private String adminID, adminPW;


    /**
     * class constructor
     */
    public Database (){
        adminID = adminPW = "admin";

    }

    //handle logins

    /**
     * handle Student login to db. Check against Student table data for auth.
     */
    public void studentLogin() {}

    /**
     * handle Admin login to db. Check against Admin table data for auth.
     */
    public void adminLogin() {}

    /**
     * handle Librarian login to db. Check against Librarian table data for auth.
     */
    public void libLogin() {}


    //only the view method is certain for the following, the rest of the management methods will only be implemented if necessary.

    //handle Student table
    public void viewStudentTable(){}
    public void createStudent(){}
    public void deleteStudent(){}
    public void updateStudent(){}

    //handle Librarian table
    public void viewLibrarianTable(){}
    public void createLibrarian(){}
    public void deleteLibrarian(){}
    public void updateLibrarian(){}

    //handle Book table
    public void viewBookTable(){}
    public void createBook(){}
    public void deleteBook(){}
    public void updateBook(){}

    //handle WaitTicket table
    public void viewWaitTicketTable(){}
    public void createWaitTicket(){}
    public void deleteWaitTicket(){}
    public void updateWaitTicket(){}

    //handle IssuedBook table
    public void viewissuedBookTable(){}
    public void createissuedBook(){}
    public void deleteissuedBook(){}
    public void updateissuedBook(){}

    //handle Item table
    public void viewItemTable(){}
    public void createItem(){}
    public void deleteItem(){}
    public void updateItem(){}

    //handle student table
    public void run() {}
}
