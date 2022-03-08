package extra.table;

import java.util.Date;

/**
 * Represents a WaitTicket DB object
 */
public class WaitTicket {

    private int ticketID; //id for ticket
    private int studentID; //student who it belongs to
    private Date dateCreated; //date created
    private int itemID; //item being ticketed

    /**
     * Custom class constructor
     *
     * @param ticketID  The unique ticket id
     * @param studentID The id of the student being issued the ticket
     * @param date      The creation date of the ticket
     * @param itemID    The id of the item being queued for issue
     */
    public WaitTicket(int ticketID, int studentID, Date date, int itemID) {
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
    new WaitTicket(0,0,new Date(), 0);
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public String toString() {
        return ticketID +
                "," + studentID +
                "," + dateCreated +
                "," + itemID + '\n';
    }
}
