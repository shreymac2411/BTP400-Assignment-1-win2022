package ca.seneca.btp400.library;

import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * this will handle the .txt files used as a database, acting as the medium our Modules will use for their functionality
 *
 * @author Matthew Maghakian
 * @version 1.0
 */
public class Database {

    //debugging prompt
    private boolean debug;
    //private static final String curr_folder_path = "/src/ca/seneca.btp400.library/db/";
    //db path
    private static final Path dbPath = Paths.get("db");
    //user paths
    private static Path studentPath = Paths.get("db", "students.txt");
    private static Path librarianPath = Paths.get("db", "librarians.txt");
    //db table paths
    private static Path bookPath = Paths.get("db", "books.txt");
    private static Path itemPath = Paths.get("db", "items.txt");
    private static Path waitTicketPath = Paths.get("db", "wait_tickets.txt");
    private static Path issuedBookPath = Paths.get("db", "issued_books.txt");
    //log path
    private static final Path logPath = Paths.get("db", "log.txt");
    //config path
    private static final Path configPath = Paths.get("db", "config.txt");

    //hold the admin login for this database
    private String ADMIN_PW, ADMIN_ID;

    //private int MAX_STUDENTS, MAX_LIBRARIANS, MAX_ITEMS, MAX_ISSUED_BOOKS, MAX_WAIT_TICKETS, MAX_BOOKS;

    /**
     * Class constructor
     */
    public Database() {
        if (debug)
            System.out.println("Database()");
        ADMIN_PW = ADMIN_ID = "admin";

        init();
        run();
    }


    public Database(boolean debug) {
        super();
        if (debug)
            System.out.println("Database(boolean debug)");

        ADMIN_PW = ADMIN_ID = "admin";

        this.debug = debug;
        init();
        run();

    }

    //getter methods

    public static Path getStudentPath() {
        return studentPath;
    }

    public static Path getLibrarianPath() {
        return librarianPath;
    }

    public static Path getBookPath() {
        return bookPath;
    }

    public static Path getItemPath() {
        return itemPath;
    }

    public static Path getWaitTicketPath() {
        return waitTicketPath;
    }

    public static Path getIssuedBookPath() {
        return issuedBookPath;
    }

    public String getADMIN_PW() {
        return ADMIN_PW;
    }

    public String getADMIN_ID() {
        return ADMIN_ID;
    }

    //db methods

    /**
     * reads the contents of a table. Will include invalid data.
     *
     * @param pathname The path of the table file to read
     */
    public void tableToString(Path pathname) {
        if (debug)
            System.out.println("tableToString()");
        String line = "";
        ArrayList<String> lines = new ArrayList<>();

        try {

            BufferedReader freader
                    = new BufferedReader(new InputStreamReader(new FileInputStream(pathname.toString())));
            while ((line = freader.readLine()) != null) {
                lines.add(line);
            }

            for (String _line : lines) {
                System.out.println(_line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Initializes the file at the path given
     *
     * @param pathname The path of the file
     */
    private void initFile(Path pathname) {

        File file = new File(String.valueOf(pathname));
        if (!file.exists()) {
            if (debug)
                System.out.println(pathname.getFileName() + " does not exist @ " + file);
            try {
                new FileWriter(file.getAbsoluteFile());
                if (debug)
                    System.out.println("created file: " + pathname.getFileName() + " @ " + file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes a message to the log file
     *
     * @param message The message to be appended to write file
     */
    public void log(String message) {
        if (debug)
            System.out.println("log()");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(logPath), true));

            writer.write(message + '\n');

            writer.close();

            if (debug)
                System.out.println("log completed.");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the contents of an array of table objects to a file
     *
     * @param obj  The table object array
     * @param path The file path
     */
    public void arrayToFile(Object[] obj, Path path) {
        if (debug)
            System.out.println("arrayToFile()");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(path)));

            for (Object i : obj) {

                writer.write(i.toString());
            }

            writer.close();

            if (debug)
                System.out.println("arrayToFile completed.");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends a single table object to its text file
     *
     * @param obj  The table object to be added
     * @param path The path of the table file
     */
    public void objectToFile(Object obj, Path path) {
        if (debug)
            System.out.println("objectToFile()");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(path), true));

            writer.write(obj.toString());

            writer.close();

            if (debug)
                System.out.println("objectToFile completed.");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //handle Student table
    public void initStudentTable() {
        if (debug)
            System.out.println("initStudentTable()");

        initFile(getStudentPath());


    }

    public void viewStudentTable() {
        if (debug)
            System.out.println("viewStudentTable()");

        tableToString(getStudentPath());

    }

    //handle Librarian table
    public void initLibrarianTable() {

        if (debug)
            System.out.println("initLibrarianTable()");

        initFile(getLibrarianPath());

    }

    public void viewLibrarianTable() {

        if (debug)
            System.out.println("viewLibrarianTable()");

        tableToString(getLibrarianPath());
    }

    //handle Book table
    public void initBookTable() {

        if (debug)
            System.out.println("initBookTable()");

        initFile(bookPath);

    }

    public void viewBookTable() {

        if (debug)
            System.out.println("viewBookTable()");

        tableToString(getBookPath());
    }

    //handle WaitTicket table
    public void initWaitTicketTable() {

        if (debug)
            System.out.println("initWaitTicketTable()");

        initFile(getWaitTicketPath());

    }

    public void viewWaitTicketTable() {

        if (debug)
            System.out.println("viewWaitTicketTable()");

        tableToString(getWaitTicketPath());
    }

    //handle IssuedBook table
    public void initIssuedBookTable() {

        if (debug)
            System.out.println("initIssuedBookTable()");

        initFile(getIssuedBookPath());

    }

    public void viewissuedBookTable() {

        if (debug)
            System.out.println("viewissuedBookTable()");

        tableToString(getIssuedBookPath());
    }

    //handle Item table
    public void initItemTable() {

        if (debug)
            System.out.println("initItemTable()");

        initFile(getItemPath());

    }

    public void viewItemTable() {

        if (debug)
            System.out.println("viewItemTable()");

        tableToString(getItemPath());
    }

    public void initConfig() {

        if (debug)
            System.out.println("initConfig()");

        initFile(this.configPath);

    }

    public void initLog() {

        if (debug)
            System.out.println("initLog()");

        initFile(this.logPath);


        if (new File(String.valueOf(logPath)).exists()) {
            if (debug)
                System.out.println("log already exists!");
        }

        //TODO - read from log file

    }

    //handle console input
    public void run() {

        if (debug)
            System.out.println("run()");
        //TODO - this will be the console part of the application

    }

    //initialize data
    private void initDB() {

        File DIRECTORY = new File(dbPath.toString());
        if (!DIRECTORY.exists()) {
            DIRECTORY.mkdir();
            System.out.println("dir does not exist @ " + DIRECTORY);
            if (debug)
                System.out.println("made dir @ " + DIRECTORY);
        }
    }

    private void init() {
        if (debug)
            System.out.println("init()");

        //init our directory if necessary
        initDB();

        //init system files
        initConfig();
        initLog();

        //init record tables
        initItemTable();
        initBookTable();
        initIssuedBookTable();
        initWaitTicketTable();

        //init user tables
        initLibrarianTable();
        initStudentTable();
    }

}
