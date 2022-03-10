package application.table;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a WaitTicket DB object
 */
public class WaitTicket implements Serializable {

    private int ticketID; //id for ticket
    private int studentID; //student who it belongs to
    private String dateCreated; //date created
    private int itemID; //item being ticketed

    /**
     * Custom class constructor
     *
     * @param ticketID  The unique ticket id
     * @param studentID The id of the student being issued the ticket
     * @param date      The creation date of the ticket
     * @param itemID    The id of the item being queued for issue
     */
    public WaitTicket(int ticketID, int studentID, String date, int itemID) {
        super();
        this.ticketID = ticketID;
        this.studentID = studentID;
        this.dateCreated = date;
        this.itemID = itemID;
    }

    /**
     * Class Constructor
     */
    public WaitTicket() {
        new WaitTicket(0, 0, new Date().toString(), 0);
    }

    /**
     * Get the Ticket ID
     *
     * @return Ticket ID
     */
    public int getTicketID() {
        return ticketID;
    }

    /**
     * Set the Ticket ID
     *
     * @param ticketID The Ticket ID
     */
    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    /**
     * Get the Student ID
     *
     * @return The Student ID
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * Set the Student ID
     *
     * @param studentID The Student ID
     */
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    /**
     * Get the date created
     *
     * @return The date created
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the date created
     *
     * @param dateCreated The date created
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Get the Item ID
     *
     * @return The Item ID
     */
    public int getItemID() {
        return itemID;
    }

    /**
     * Set the Item ID
     *
     * @param itemID The Item ID
     */
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    /**
     * Return a file-ready format of the Wait Ticket's fields
     *
     * @return String representation of the Wait Ticket
     */
    @Override
    public String toString() {
        return ticketID +
                "," + studentID +
                "," + dateCreated +
                "," + itemID + '\n';
    }
}
