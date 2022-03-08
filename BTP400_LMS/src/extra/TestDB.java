package ca.seneca.btp400.library;

import ca.seneca.btp400.library.table.Book;
import ca.seneca.btp400.library.table.Librarian;
import ca.seneca.btp400.library.table.Student;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Test class for Database
 */
public class TestDB {


    private static final Path studentPath = Paths.get("db", "students.txt");
    private static Student studentTestData[] = {
            new Student(1, "mark eco", new Date().toString(), "1234"),
            new Student(2, "tom ley", new Date().toString(), "1234"),
            new Student(3, "jim scott", new Date().toString(), "1234"),
            new Student(5, "raven solomon", new Date().toString(), "1234"),
            new Student(4, "tim patrick terry", new Date().toString(), "1234")
    };

    private static Librarian libTestData[] = {
        new Librarian(1, "1234", "Lib Rarian")
    };

    private static Book bookTestData[] = {
            new Book(1, "Test1", "Test Publisher", "Test Author", new Date().toString(), 356, "test book", true),
            new Book(2, "Test2", "Test Publisher", "Test Author", new Date().toString(), 184, "test book", false),
            new Book(3, "Test3", "Test Publisher", "Test Author", new Date().toString(), 732, "test book", true),
            new Book(4, "Test4", "Test Publisher", "Test Author", new Date().toString(), 221, "test book", false),
            new Book(5, "Test5", "Test Publisher", "Test Author", new Date().toString(), 631, "test book", true),
            new Book(6532, "bookbook", "Test Publisher", "Test Author", new Date().toString(), 523, "test book!!!", false),

    };//TODO

    public TestDB() {

        System.out.println("TestDB()!");
        Database d = new Database(true);

        //Set set = new HashSet<>();
        //set.addAll(List.of(studentTestData));
        //d.arrayToFile(set.toArray(new Object[0]), studentPath);
        //d.arrayToFile(bookTestData, Database.getBookPath());
        d.arrayToFile(libTestData, Database.getLibrarianPath());

    }

}
