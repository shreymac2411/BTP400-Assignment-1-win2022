package ca.seneca.btp400.library.table;

import java.util.Date;

/**
 * Represents an Item DB object
 */
public class Item {

    private int itemID;
    private String type;
    private String itemName;
    private Date dateAdded;
    private String desc;

    /**
     * Custom class constructor
     *
     * @param itemID   The unique item id
     * @param type     The type of item
     * @param itemName The item's name
     * @param date     The date of the item being added
     * @param desc     The item description
     */
    public Item(int itemID, String type, String itemName, Date date, String desc) {
        super();
        this.itemID = itemID;
        this.type = type;
        this.itemName = itemName;
        this.dateAdded = date;
        this.desc = desc;
    }

    public Item() {
    new Item(0," ", " ", new Date(), " ");
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return itemName;
    }

    public void setName(String name) {
        this.itemName = name;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return itemID +
                "," + type +
                "," + itemName +
                "," + dateAdded +
                "," + desc + '\n';
    }
}
