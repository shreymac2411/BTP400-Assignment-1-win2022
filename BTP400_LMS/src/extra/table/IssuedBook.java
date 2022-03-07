package ca.seneca.btp400.library.table;

import java.util.Date;

/**
 * Represents an IssuedBook DB object
 */
public class IssuedBook {

    private int issueID;
    private int bookID;
    private int studentID;
    private Date dateIssued;

    /**
     * Custom class constructor
     *
     * @param issueID   The unique issue id
     * @param bookID    The issued book id
     * @param studentID The id of the student requesting the issue
     * @param date      The date of issuing
     */
    public IssuedBook(int issueID, int bookID, int studentID, Date date) {
        super();
        this.bookID = bookID;
        this.issueID = issueID;
        this.studentID = studentID;
        this.dateIssued = date;
    }

    public IssuedBook() {
    new IssuedBook(0, 0, 0, new Date());
    }

    public int getIssueID() {
        return issueID;
    }

    public void setIssueID(int issueID) {
        this.issueID = issueID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    @Override
    public String toString() {
        return issueID +
                "," + bookID +
                "," + studentID +
                "," + dateIssued + '\n';
    }
}
