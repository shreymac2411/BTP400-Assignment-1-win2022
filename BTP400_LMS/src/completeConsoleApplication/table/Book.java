package completeConsoleApplication.table;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a Book DB object
 */
public class Book implements Serializable {

    private int bookID;
    private String bookName;
    private String bookAuthor;
    private String bookPublisher;
    private String datePublished;
    private int ISBN;
    private String desc;
    private boolean isIssued;

    /**
     * Class custom constructor
     *
     * @param bookID        The unique book id
     * @param bookName      The book name
     * @param bookAuthor    The book author
     * @param bookPublisher The book publisher
     * @param datePublished The book's date of publishing
     * @param ISBN     The book's page count
     * @param desc          The book's description
     * @param isIssued      Boolean returning if the book has been issued or not
     */
    public Book(int bookID, String bookName, String bookAuthor, String bookPublisher, String datePublished, int ISBN, String desc, boolean isIssued) {
        super();
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.datePublished = datePublished;
        this.ISBN = ISBN;
        this.desc = desc;
        this.isIssued = isIssued;
    }

    public Book() {
    new Book(0," ", " ", " ", new Date().toString(), 0, " ", false );
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    @Override
    public String toString() {
        return bookID +
                "," + bookName +
                "," + bookAuthor +
                "," + bookPublisher +
                "," + datePublished +
                "," + ISBN +
                "," + desc +
                "," + isIssued + '\n';
    }
}
