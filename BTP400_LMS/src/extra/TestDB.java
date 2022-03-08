package extra;

import extra.table.Book;
import extra.table.Librarian;
import extra.table.Student;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Test class for Database
 */
public class TestDB {


    private static final Path studentPath = Paths.get("db", "students.txt");
    private static Student studentTestData [] = {
            new Student(1, "mark eco", new Date(), 0, "1234"),
            new Student(2, "tom ley", new Date(), 9, "1234"),
            new Student(3, "jim scott", new Date(), 15, "1234"),
            new Student(5, "raven solomon", new Date(), 0, "1234"),
            new Student(4, "tim patrick terry", new Date(), 3, "1234")
    };
    private static Book bookTestData [] = {};//TODO
    private static Librarian librarianTestData [] = {};//TODO

    public TestDB(){

        System.out.println("TestDB()!");
        Database d = new Database(true);

        Set set = new HashSet<>();
        set.addAll(List.of(studentTestData));
        d.arrayToFile(set.toArray(new Object[0]), studentPath);
        d.tableToString(studentPath);

    }

    public TestDB(Database db) {
        super();

        db.viewItemTable();
    }
}
