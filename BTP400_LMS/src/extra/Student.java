package ca.seneca.btp400.library.table;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a Student DB object
 */
public class Student implements Serializable {

    private int studentID;
    private String studentName;
    private Date studentDOB;
    private int issuedBooksCount;
    private String studentPassword;

    /**
     * Custom class constructor
     *
     * @param studentID        The unique student id. Also acts as their login id
     * @param studentName      The student's name
     * @param studentDOB       The student's date of birth
     * @param issuedBooksCount The number of books the student has issued
     * @param studentPassword  The student's login password
     */
    public Student(int studentID, String studentName, Date studentDOB, int issuedBooksCount, String studentPassword) {
        super();
        this.studentID = studentID;
        this.studentDOB = studentDOB;
        this.issuedBooksCount = issuedBooksCount;
        this.studentName = studentName;
        this.studentPassword = studentPassword;
    }

    /**
     * Class Constructor
     */
    public Student() {
    new Student(0," ", new Date(), 0,  " ");
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getStudentDOB() {
        return studentDOB;
    }

    public void setStudentDOB(Date studentDOB) {
        this.studentDOB = studentDOB;
    }

    public int getIssuedBooksCount() {
        return issuedBooksCount;
    }

    public void setIssuedBooksCount(int issuedBooksCount) {
        this.issuedBooksCount = issuedBooksCount;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    @Override
    public String toString() {
        return
                studentID +
                        "," + studentName +
                        "," + studentDOB +
                        "," + issuedBooksCount +
                        "," + studentPassword + '\n';
    }
}
