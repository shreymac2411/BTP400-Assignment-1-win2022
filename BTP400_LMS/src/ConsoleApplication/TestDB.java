package ConsoleApplication;

import application.table.Book;
import application.table.Librarian;
import application.table.Student;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Test class for Database
 */
public class TestDB {


    private static final Path studentPath = Paths.get("db", "students.txt");
    private static Student studentTestData[] = {
            new Student(1, "mark eco", new Date().toString(), "1234", "Undergrad"),
            new Student(2, "tom ley", new Date().toString(), "1234", "Undergrad"),
            new Student(3, "jim scott", new Date().toString(), "1234", "Undergrad"),
            new Student(5, "raven solomon", new Date().toString(), "1234", "Undergrad"),
            new Student(4, "tim patrick terry", new Date().toString(), "1234", "Alumni")
    };

    private static Librarian libTestData[] = {
            new Librarian(1, "1234", "Lib Rarian")
    };

    private static Book bookTestData[] = {
            new Book(1, "Test1", "Test Publisher", "Test Author", new Date().toString(), 356423, "test book", true),
            new Book(2, "Test2", "Test Publisher", "Test Author", new Date().toString(), 184234, "test book", false),
            new Book(3, "Test3", "Test Publisher", "Test Author", new Date().toString(), 732423, "test book", false),
            new Book(4, "Test4", "Test Publisher", "Test Author", new Date().toString(), 221523, "test book", false),
            new Book(7, "Test4", "Test Publisher", "Test Author", new Date().toString(), 221523, "test book", false),
            new Book(6532, "The Test Book", "Test Publisher", "Test Author", new Date().toString(), 523423, "test book!!!", false),

    };

    /**
     * Class constructor
     * updates DB files
     */
    public TestDB() {

        System.out.println("TestDB()!");
        Database d = new Database(true);

        d.arrayToFile(studentTestData, studentPath);
        d.arrayToFile(bookTestData, Database.getBookPath());
        d.arrayToFile(libTestData, Database.getLibrarianPath());

    }

}
