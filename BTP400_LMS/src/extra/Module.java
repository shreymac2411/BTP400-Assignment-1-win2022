package extra;

import extra.table.*;

public class Module {
    public static void main(String[] args) {

        TableController db
                = new TableController(true);

        // new TestDB();

        db.addBook(new Book());

        db.deleteStudent(5);




    }

}
