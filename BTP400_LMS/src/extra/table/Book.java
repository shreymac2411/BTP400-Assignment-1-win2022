package extra.table;

import java.util.Date;

/**
 * Represents a Book DB object
 */
public class Book {

    private int bookID;
    private String bookName;
    private String bookAuthor;
    private String bookPublisher;
    private Date datePublished;
    private int pageCount;
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
     * @param pageCount     The book's page count
     * @param desc          The book's description
     * @param isIssued      Boolean returning if the book has been issued or not
     */
    public Book(int bookID, String bookName, String bookAuthor, String bookPublisher, Date datePublished, int pageCount, String desc, boolean isIssued) {
        super();
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.datePublished = datePublished;
        this.pageCount = pageCount;
        this.desc = desc;
        this.isIssued = isIssued;
    }

    public Book() {
    new Book(0," ", " ", " ", new Date(), 0, " ", false );
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

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
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
                "," + pageCount +
                "," + desc +
                "," + isIssued + '\n';
    }
}
