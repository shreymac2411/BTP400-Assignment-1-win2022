package extra;

import extra.table.*;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * @author Matthew Maghakian
 * Holds all table controller methods and data
 */
public class TableController extends Database {

    private boolean debug;

    public TableController() {
        super();

        if (debug)
            System.out.println("TableController()");
    }

    public TableController(boolean debug) {
        super();
        this.debug = debug;
        if (debug)
            System.out.println("TableController(boolean debug)");
    }

    /**
     * Creates a duplicate free copy of the given file, and then makes the replacement
     *
     * @param path The path to the file
     */
    public void removeDuplicateLines(Path path) {
        ArrayList<String> lines = new ArrayList<>();
        Set set = new HashSet<>();
        String line = "";

        try {
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())));

            while ((line = reader.readLine()) != null) {
                lines.add(line + '\n');
            }

            for (String _line : lines) {
                set.add(_line);
            }

            arrayToFile(set.toArray(), path);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getFreeIndex(Path path) {
        if (debug)
            System.out.println("getFreeIndex()");
        String line = "";
        ArrayList<Integer> ids = new ArrayList<>();

        try {
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())));
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                if (split.length > 0)
                    ids.add(Integer.valueOf(split[0]));
                else break;
            }

            int index = 0;
            while (index < 500) {
                if (ids.contains(index)) {
                    if (debug)
                        System.out.println("index: " + index + " found in arrayList.");
                    index++;
                }
                ;
                if (!ids.contains(index)) {
                    if (debug)
                        System.out.println("index: " + index + " is free.");
                    return index;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * Get all Students stored in the database in an ArrayList
     *
     * @return An array list containing all Student objects in the database
     */
    public ArrayList<Student> getAllStudents() {
        if (debug)
            System.out.println("getAllStudents()");

        Path path = getStudentPath();
        int fields = Student.class.getDeclaredFields().length;
        String line = "";
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();

        try {

            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())));
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }


            for (String _line : lines) {
                Student studentToAdd = new Student();

                String[] splitString = _line.split(",");

                if (splitString.length < fields) {
                    System.err.println("error. Invalid Student passed");

                } else {

                    studentToAdd.setStudentID(Integer.parseInt(splitString[0]));

                    studentToAdd.setStudentName(splitString[1]);

                    studentToAdd.setStudentDOB(new Date());

                    studentToAdd.setIssuedBooksCount(Integer.parseInt(splitString[3]));

                    studentToAdd.setStudentPassword(splitString[4]);

                    students.add(studentToAdd);
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (debug)
            System.out.println("getAllStudents Complete \n" + students);
        return students;
    }

    public boolean addStudent(Student student) {
        if (debug)
            System.out.println("addStudent()");

        //if not in array
        if (getIndexOfStudent(student.getStudentID()) == -1) {
            objectToFile(student, getStudentPath());
            System.out.println("Student Added.");
            return true;
        }
        System.out.println("Student could not be added. Student ID already exists in database.");
        return false;

    }

    public int getIndexOfStudent(int id) {
        if (debug)
            System.out.println("getIndexOfStudent()");

        ArrayList<Student> list = getAllStudents();
        int index = 0;
        for (Student student : list) {
            if (student.getStudentID() == id)
                return index;

            index++;
        }
        System.out.println("Student not Found.");
        return -1;
    }

    public boolean deleteStudent(int id) {
        if (debug)
            System.out.println("deleteStudent()");

        ArrayList<Student> curr_students = getAllStudents();

        try {
            int index = getIndexOfStudent(id);
            if (index == -1) {
                System.out.println("Student was not deleted. ID Could not be found.");
                return false;
            }

            curr_students.remove(index);
            System.out.println("Student #" + id + " has been deleted!");
            arrayToFile(curr_students.toArray(), getStudentPath());
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Index out of bounds!");
        }
        return false;
    }

    public Student getStudent(int id) {
        if (debug)
            System.out.println("getStudent()");

        ArrayList<Student> list = getAllStudents();
        for (Student student : list) {
            if (student.getStudentID() == id) {
                System.out.println("Student found");

                if (debug)
                    System.out.println("getStudent() -> " + student);

                return student;
            }
        }
        System.out.println(id + " not Found");
        return new Student();
    }

    public ArrayList<Book> getAllBooks() {
        if (debug)
            System.out.println("getAllBooks()");

        int fields = Book.class.getDeclaredFields().length;
        Path path = getBookPath();
        String line = "";
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Book> books = new ArrayList<>();

        try {

            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())));
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }


            for (String _line : lines) {
                Book bookToAdd = new Book();

                String[] splitString = _line.split(",");

                if (splitString.length < fields) {
                    System.err.println("error. Invalid Book passed");
                    break;
                }

                bookToAdd.setBookID(Integer.parseInt(splitString[0]));

                bookToAdd.setBookName(splitString[1]);

                bookToAdd.setBookAuthor(splitString[2]);

                bookToAdd.setBookPublisher(splitString[3]);

                bookToAdd.setDatePublished(new Date());

                bookToAdd.setPageCount(Integer.parseInt(splitString[5]));

                bookToAdd.setDesc(splitString[6]);

                bookToAdd.setIssued(Boolean.parseBoolean(splitString[7]));

                books.add(bookToAdd);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (debug) {
            System.out.println("getAllBooks complete");
        }

        return books;
    }

    public boolean addBook(Book book) {
        if (debug)
            System.out.println("addBook()");

        //if not in array
        if (getIndexOfBook(book.getBookID()) == -1) {
            objectToFile(book, getBookPath());
            System.out.println("Book Added.");
            return true;
        }
        System.out.println("Book could not be added. Book ID already exists in database.");
        return false;
    }

    public int getIndexOfBook(int id) {
        if (debug)
            System.out.println("getIndexOfBook()");

        ArrayList<Book> list = getAllBooks();
        int index = 0;
        for (Book book : list) {
            if (book.getBookID() == id)
                return index;

            index++;
        }
        System.out.println("Book not Found");
        return -1;
    }

    public boolean deleteBook(int id) {
        if (debug)
            System.out.println("deleteBook()");

        ArrayList<Book> curr = getAllBooks();

        try {
            int index = getIndexOfBook(id);
            if (index == -1) {
                System.out.println("Book was not deleted. ID Could not be found.");
                return false;
            }

            curr.remove(index);
            System.out.println("Book #" + id + " has been deleted!");
            arrayToFile(curr.toArray(), getBookPath());
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Index out of bounds!");
        }
        return false;
    }

    public Book getBook(int id) {
        if (debug)
            System.out.println("getBook()");

        ArrayList<Book> list = getAllBooks();
        for (Book book : list) {
            if (book.getBookID() == id) {
                System.out.println("Book found");

                if (debug)
                    System.out.println("getLibrarian() -> " + book);

                return book;
            }
        }
        System.out.println(id + " not Found");
        return new Book();
    }

    public ArrayList<Librarian> getAllLibrarians() {
        if (debug)
            System.out.println("getAllLibrarians()");

        int fields = Librarian.class.getDeclaredFields().length;
        Path path = getLibrarianPath();
        String line = "";
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Librarian> librarians = new ArrayList<>();

        try {

            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())));
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }


            for (String _line : lines) {
                Librarian librarianToAdd = new Librarian();

                String[] splitString = _line.split(",");

                if (splitString.length < fields) {
                    System.err.println("error. Invalid Librarian passed");
                    break;
                }

                librarianToAdd.setId(Integer.parseInt(splitString[0]));

                librarianToAdd.setPassword(splitString[1]);

                librarianToAdd.setName(splitString[2]);

                librarians.add(librarianToAdd);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (debug) {
            System.out.println("getAllLibrarians complete");
        }

        return librarians;
    }

    public boolean addLibrarian(Librarian librarian) {
        if (debug)
            System.out.println("addLibrarian()");

        //if not in array
        if (getIndexOfLibrarian(librarian.getId()) == -1) {
            objectToFile(librarian, getLibrarianPath());
            System.out.println("Librarian Added.");
            return true;
        }
        System.out.println("Librarian could not be added. Librarian ID already exists in database.");
        return false;
    }

    public int getIndexOfLibrarian(int id) {
        if (debug)
            System.out.println("getIndexOfLibrarian()");

        ArrayList<Librarian> list = getAllLibrarians();
        int index = 0;
        for (Librarian librarian : list) {
            if (librarian.getId() == id)
                return index;

            index++;
        }
        System.out.println("Librarian not Found");
        return -1;
    }

    public boolean deleteLibrarian(int id) {
        if (debug)
            System.out.println("deleteLibrarian()");

        ArrayList<Librarian> curr = getAllLibrarians();

        try {
            int index = getIndexOfLibrarian(id);
            if (index == -1) {
                System.out.println("Librarian was not deleted. ID Could not be found.");
                return false;
            }

            curr.remove(index);
            System.out.println("Librarian #" + id + " has been deleted!");
            arrayToFile(curr.toArray(), getLibrarianPath());
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Index out of bounds!");
        }
        return false;
    }

    public Librarian getLibrarian(int id) {
        if (debug)
            System.out.println("getLibrarian()");

        ArrayList<Librarian> list = getAllLibrarians();
        for (Librarian librarian : list) {
            if (librarian.getId() == id) {
                System.out.println("Librarian found");

                if (debug)
                    System.out.println("getLibrarian() -> " + librarian);

                return librarian;
            }
        }
        System.out.println(id + " not Found");
        return new Librarian();
    }

    public ArrayList<Item> getAllItems() {
        if (debug)
            System.out.println("getAllItems()");

        int fields = Item.class.getDeclaredFields().length;
        Path path = getItemPath();
        String line = "";
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();

        try {

            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())));
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }


            for (String _line : lines) {
                Item item = new Item();

                String[] splitString = _line.split(",");

                if (splitString.length < fields) {
                    System.err.println("error. Invalid Item passed");
                    break;
                }

                item.setItemID(Integer.parseInt(splitString[0]));

                item.setType(splitString[1]);

                item.setName(splitString[2]);

                item.setDateAdded(new Date());

                item.setDesc(splitString[4]);

                items.add(item);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (debug) {
            System.out.println("getAllItems complete");
        }

        return items;
    }

    public boolean addItem(Item item) {
        if (debug)
            System.out.println("addItem()");

        //if not in array
        if (getIndexOfItem(item.getItemID()) == -1) {
            objectToFile(item, getItemPath());
            System.out.println("Item Added.");
            return true;
        }
        System.out.println("Item could not be added. Item ID already exists in database.");
        return false;
    }

    public int getIndexOfItem(int id) {
        if (debug)
            System.out.println("getIndexOfItem()");

        ArrayList<Item> list = getAllItems();
        int index = 0;
        for (Item item : list) {
            if (item.getItemID() == id)
                return index;

            index++;
        }
        System.out.println("Item not Found");
        return -1;
    }

    public boolean deleteItem(int id) {
        if (debug)
            System.out.println("deleteItem()");

        ArrayList<Item> curr = getAllItems();

        try {
            int index = getIndexOfItem(id);
            if (index == -1) {
                System.out.println("Item was not deleted. ID Could not be found.");
                return false;
            }

            curr.remove(index);
            System.out.println("Item #" + id + " has been deleted!");
            arrayToFile(curr.toArray(), getItemPath());
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Index out of bounds!");
        }
        return false;
    }

    public Item getItem(int id) {
        if (debug)
            System.out.println("getItem()");

        ArrayList<Item> list = getAllItems();
        for (Item item : list) {
            if (item.getItemID() == id) {
                System.out.println("Item found");

                if (debug)
                    System.out.println("getItem() -> " + item);

                return item;
            }
        }
        System.out.println(id + " not Found");
        return new Item();
    }

    public ArrayList<IssuedBook> getAllIssuedBooks() {
        if (debug)
            System.out.println("getAllIssuedBooks()");

        int fields = IssuedBook.class.getDeclaredFields().length;
        Path path = getIssuedBookPath();
        String line = "";
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<IssuedBook> issuedBooks = new ArrayList<>();

        try {

            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())));
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }


            for (String _line : lines) {
                IssuedBook issuedBook = new IssuedBook();

                String[] splitString = _line.split(",");

                if (splitString.length < fields) {
                    System.err.println("error. Invalid IssuedBook passed");
                    break;
                }

                issuedBook.setIssueID(Integer.parseInt(splitString[0]));

                issuedBook.setBookID(Integer.parseInt(splitString[1]));

                issuedBook.setStudentID(Integer.parseInt(splitString[2]));

                issuedBook.setDateIssued(new Date());

                issuedBooks.add(issuedBook);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (debug) {
            System.out.println("getAllIssuedBooks complete");
        }

        return issuedBooks;
    }

    public boolean addIssuedBook(IssuedBook issuedBook) {
        if (debug)
            System.out.println("addIssuedBook()");

        //if not in array
        if (getIndexOfIssuedBook(issuedBook.getIssueID()) == -1) {
            objectToFile(issuedBook, getIssuedBookPath());
            System.out.println("IssuedBook Added.");
            return true;
        }
        System.out.println("IssuedBook could not be added. IssuedBook ID already exists in database.");
        return false;
    }

    public int getIndexOfIssuedBook(int id) {
        if (debug)
            System.out.println("getIndexOfIssuedBook()");

        ArrayList<IssuedBook> list = getAllIssuedBooks();
        int index = 0;
        for (IssuedBook issuedBook : list) {
            if (issuedBook.getIssueID() == id)
                return index;

            index++;
        }
        System.out.println("IssuedBook not Found");
        return -1;
    }

    public boolean deleteIssuedBook(int id) {
        if (debug)
            System.out.println("deleteIssuedBook()");

        ArrayList<IssuedBook> curr = getAllIssuedBooks();

        try {
            int index = getIndexOfIssuedBook(id);
            if (index == -1) {
                System.out.println("IssuedBook was not deleted. ID Could not be found.");
                return false;
            }

            curr.remove(index);
            System.out.println("IssuedBook #" + id + " has been deleted!");
            arrayToFile(curr.toArray(), getIssuedBookPath());
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Index out of bounds!");
        }
        return false;
    }

    public IssuedBook getIssuedBook(int id) {
        if (debug)
            System.out.println("getIssuedBook()");

        ArrayList<IssuedBook> list = getAllIssuedBooks();
        for (IssuedBook issuedBook : list) {
            if (issuedBook.getIssueID() == id) {
                System.out.println("IssuedBook found");

                if (debug)
                    System.out.println("getIssuedBook() -> " + issuedBook);

                return issuedBook;
            }
        }
        System.out.println(id + " not Found");
        return new IssuedBook();
    }

    public ArrayList<WaitTicket> getAllWaitTickets() {
        if (debug)
            System.out.println("getAllWaitTickets()");

        int fields = WaitTicket.class.getDeclaredFields().length;
        Path path = Database.getWaitTicketPath();
        String line = "";
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<WaitTicket> waitTickets = new ArrayList<>();

        try {

            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())));
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }


            for (String _line : lines) {
                WaitTicket waitTicket = new WaitTicket();

                String[] splitString = _line.split(",");

                if (splitString.length < fields) {
                    System.err.println("error. Invalid WaitTicket passed");
                    break;
                }


                waitTicket.setTicketID(Integer.parseInt(splitString[0]));

                waitTicket.setStudentID(Integer.parseInt(splitString[1]));

                waitTicket.setDateCreated(new Date());

                waitTicket.setItemID(Integer.parseInt(splitString[3]));

                waitTickets.add(waitTicket);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (debug) {
            System.out.println("getAllWaitTickets complete");
        }

        return waitTickets;
    }

    public boolean addWaitTicket(WaitTicket waitTicket) {
        if (debug)
            System.out.println("addWaitTicket()");

        //if not in array
        if (getIndexOfWaitTicket(waitTicket.getTicketID()) == -1) {
            objectToFile(waitTicket, getWaitTicketPath());
            System.out.println("WaitTicket Added.");
            return true;
        }
        System.out.println("WaitTicket could not be added. WaitTicket ID already exists in database.");
        return false;
    }

    public int getIndexOfWaitTicket(int id) {
        if (debug)
            System.out.println("getIndexOfWaitTicket()");

        ArrayList<WaitTicket> list = getAllWaitTickets();
        int index = 0;
        for (WaitTicket waitTicket : list) {
            if (waitTicket.getTicketID() == id)
                return index;

            index++;
        }
        System.out.println("WaitTicket not Found");
        return -1;
    }

    public boolean deleteWaitTicket(int id) {
        if (debug)
            System.out.println("deleteWaitTicket()");

        ArrayList<WaitTicket> curr = getAllWaitTickets();

        try {
            int index = getIndexOfWaitTicket(id);
            if (index == -1) {
                System.out.println("WaitTicket was not deleted. ID Could not be found.");
                return false;
            }

            curr.remove(index);
            System.out.println("WaitTicket #" + id + " has been deleted!");
            arrayToFile(curr.toArray(), getWaitTicketPath());
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Index out of bounds!");
        }
        return false;
    }

    public WaitTicket getWaitTicket(int id) {
        if (debug)
            System.out.println("getWaitTicket()");

        ArrayList<WaitTicket> list = getAllWaitTickets();
        for (WaitTicket waitTicket : list) {
            if (waitTicket.getTicketID() == id) {
                System.out.println("waitTicket found");

                if (debug)
                    System.out.println("getWaitTicket() -> " + waitTicket);

                return waitTicket;
            }
        }
        System.out.println(id + " not Found");
        return new WaitTicket();
    }
}
