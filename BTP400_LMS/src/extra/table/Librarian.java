package ca.seneca.btp400.library.table;

import java.io.Serializable;

/**
 * Represents a Librarian DB object
 */
public class Librarian implements Serializable {

    private int id;
    private String password;
    private String name;

    /**
     * Custom class constructor
     *
     * @param id       The unique librarian ID. Also acts as their login id
     * @param password The librarian's password
     * @param name     The librarian's name
     */
    public Librarian(int id, String password, String name) {
        super();
        this.id = id;
        this.password = password;
        this.name = name;
    }

    /**
     * Class Constructor
     */
    public Librarian() {
    new Librarian(0," "," ");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id +
                "," + password +
                "," + name + '\n';
    }
}
