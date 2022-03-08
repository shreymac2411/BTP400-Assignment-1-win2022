package ca.seneca.btp400.library;

import ca.seneca.btp400.library.table.Librarian;

import java.util.Date;

/**
 * Represents the Admin module's functionality within the DB
 *
 * @author Mohammad
 */
public class AdminController {

    private boolean debug = false;
    private boolean loggedIn = false;

    public AdminController() {
        super();
    }

    public AdminController(boolean debug) {
        super();
        this.debug = debug;
        if (debug)
            System.out.println("AdminController(boolean debug)");
    }
    public void run(){
        if (debug)
            System.out.println("Admin::run()");

        return;
    }

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

        System.out.println("Login unsuccessful. ID or password were incorrect.");
        return false;
    }

    public void viewAll() {
        if (debug)
            System.out.println("viewAll()");

        if (!loggedIn)
            return;

        TableController db = new TableController();

        db.viewLibrarianTable();
        db.log(new Date() + ": Admin Viewed all Librarians registered in database.");

    }

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
