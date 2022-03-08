package ca.seneca.btp400.library;

import ca.seneca.btp400.library.table.*;

public class Module {
    public static void main(String[] args) {

        TableController db
                = new TableController(true);

        // new TestDB(); //creates test data (will overwrite!)
        StudentController student = new StudentController(true);
        //   student.viewAllBooks();

        StudentController st2 = new StudentController(false);
        st2.login(1, "1234");


        LibrarianController lib = new LibrarianController();
        lib.login(1, "1234");
        StudentController st3 = new StudentController(false);
        st3.login(2, "1234");

        student.requestIssue(2);
        st3.requestIssue(2);
        st2.requestIssue(2);
        lib.returnBook(2);
        StudentController std = new StudentController();

    }

}
