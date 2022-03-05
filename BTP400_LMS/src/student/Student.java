package com.company;
import java.io.*;
import java.util.*;

public class Student {
    private String studentID;
    private String studentPass;
    private String studentType;


    public Student(String id, String pass, String type){
        this.studentID = id;
        this.studentPass = pass;
        this.studentType = type;
    }
    public Student(String id, String pass){
        this.studentID = id;
        this.studentPass = pass;
        this.studentType = "";
    }
    public String getStudentID() {
        return studentID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public String getStudentPass() {
        return studentPass;
    }
    public void setStudentPass(String studentPass) {
        this.studentPass = studentPass;
    }
    public String getStudentType() {
        return studentType;
    }
    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    /*
    This method logs the user in, comparing input to the txt file containing the login info
     */
    public static boolean login(String id, String pass){
        FileInputStream read = null;
        boolean isValid = false;
        try {
            read = new FileInputStream("student.txt");
            Scanner reader = new Scanner(read);

            while(reader.hasNextLine()){
                String user = reader.nextLine();
                String[] token = user.split(",");
                if(token[0].equals(id) && token[1].equals(pass)){
                    isValid = true;
                }
            }

            reader.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return isValid;
    }

    /*
    Formats the input of the user to change spaces into underscores and uncapitalized letters into
    capitalized letters.
     */
    public static String format(String bookName){
        String[] splitWord = bookName.split(" ");
        String capitalizedWord = "";
        for (int i = 0; i < splitWord.length; i++){
            capitalizedWord += splitWord[i].substring(0,1).toUpperCase() + splitWord[i].substring(1) + " ";
        }
        String trimName = capitalizedWord.trim();
        String formatBookName = trimName.replace(" ", "_");
        return formatBookName;
    }

    /*
    Searches a book through user input, first formatting the input to match the txt file format.
    It then displays general information that is common among the book information (barcode, issued status, etc... are
    not included because they are unique to the specified book
     */
    public static void searchBook(String bookName){
        FileInputStream read = null;

        String[] bookList = new String [100];
        String[] allBooks = new String [100];

        String formattedBookName = format(bookName);

        try {

            read = new FileInputStream("item.txt");
            Scanner reader = new Scanner(read);

            //read each line of the txt file, and save the first token (the title of the book) to the array
            int counter = 0;
            while(reader.hasNextLine()) {
                String book = reader.nextLine();
                //add book into an array, for later use
                allBooks[counter] = book;
                String[] token = book.split(",");
                //counts how many books there are
                bookList[counter] = token[3];
                counter++;
            }
            boolean stop = false;
            //compare the BookName and BookList, if there is a match, display
            for(int i = 0; i < bookList.length; i++){
                if(formattedBookName.equals(bookList[i]) && !stop){
                    String[] bookInfo = allBooks[i].split(",");
                    System.out.println(
                            "Format: " + bookInfo[1] + "\n" +
                            "Title: " + bookInfo[3] + "\n" +
                            "Publisher: " + bookInfo[4] + "\n" +
                            "Date Published: " + bookInfo[5]
                    );
                    stop = true;
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /*
    Checks if there is an available book to issue, if not, the method creates a txt file showing the user
    their position on the waitlist. Issued.txt displays the book's name and how many people are waiting for a copy of
    the book. Ticket.txt is merely a message to display to the user. The method also reads item.txt to check how many
    books have been taken out
    */
    public boolean checkIfIssued(String bookName){
        FileInputStream readItem = null;
        FileInputStream readIssued = null;

        try{
            readItem = new FileInputStream("item.txt");
            Scanner item = new Scanner(readItem);
            readIssued = new FileInputStream("issued.txt");
            Scanner issued = new Scanner(readIssued);

            String formattedBookName = format(bookName);
            int issuedCounter = 0;

            while(item.hasNextLine()) {
                String book = item.nextLine();

                String[] bookToken = book.split(",");
                String isIssued = bookToken [0];
                String title = bookToken[3];


                if(title.equals(formattedBookName) && isIssued.equals("N")){
                    issuedCounter++;
                }
            }
            if(issuedCounter == 0){
                System.out.println("There are currently no available books of that title");
                String writeToIssued = "";
                String waitPos = "";
                while(issued.hasNextLine()) {
                    String book = issued.nextLine();
                    String[] bookToken = book.split(",");
                    String title = bookToken[0];
                    String waitNum = bookToken[1];

                    if(title.equals(formattedBookName)){
                        int convertedNum = Integer.parseInt(waitNum) + 1;
                        waitNum = Integer.toString(convertedNum);
                        waitPos = waitNum;
                    }
                    writeToIssued += bookToken[0] + "," + waitNum + "\n";

                }
                try {
                    FileWriter writer = new FileWriter("issued.txt");
                    BufferedWriter writeIssued = new BufferedWriter(writer);
                    writeIssued.write(writeToIssued);
                    writeIssued.close();
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                try {
                    File ticket = new File("ticket.txt");
                    if(!ticket.exists()){
                        ticket.createNewFile();
                    }

                    FileWriter writer = new FileWriter("ticket.txt");
                    BufferedWriter writeIssued = new BufferedWriter(writer);
                    System.out.println("You were placed in queue for a copy of the book");
                    writeIssued.write("Student " + this.studentID + " is in position: " + waitPos +
                            " for the book " + formattedBookName + "\n");
                    writeIssued.close();
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("There exists a copy of that book that is available");
                return false;
                //add the Librarian function to this space
            }
            readItem.close();
            readIssued.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //function displays the current position that the person is in.
        return true;
    }
    /*
    This method shows the user all the books, how many are issued, and the total amount of books. It reads from the
    items.txt file and creates a set to hold only unique values. The set is then converted into a list for its .get()
    method. A 2d array holds the title, amount of books issued, and the total amount of books, then eventually writes
    to borrowedBooks to display this information.
     */
    public static void viewBorrowed(){
        FileInputStream readIssued = null;
        FileInputStream readIssued2 = null;

        Set set = new HashSet();

        try {
            readIssued = new FileInputStream("item.txt");
            Scanner reader = new Scanner(readIssued);

            //find unique titles so they can be counted later
            while (reader.hasNextLine()) {
                String book = reader.nextLine();
                String[] bookToken = book.split(",");
                set.add(bookToken[3]);
            }
            //convert set into list so .get() can retrieve elements
            String[] array = new String[set.size()];
            List<String> list = new ArrayList<>();
            list.addAll(set);

            //allocate list to array
            for (int i = 0; i < array.length; i++){
                array[i] = list.get(i);
            }

            //create 2d array for storing name along with the amount of books
            String[][] bookAmount = new String[array.length][3];
            for (int i = 0; i < array.length; i++){
                bookAmount[i][0] = array[i]; //title
                bookAmount[i][1] = Integer.toString(0); //amount issued
                bookAmount[i][2] = Integer.toString(0); //total amount
            }

            //read the file from the top and allocate books to title
            readIssued2 = new FileInputStream("item.txt");
            Scanner reader2 = new Scanner(readIssued2);
            while (reader2.hasNextLine()) {
                String book = reader2.nextLine();
                String[] bookToken = book.split(",");
                for(int i = 0; i < bookAmount.length; i++){
                    if(bookToken[3].equals(bookAmount[i][0]) && bookToken[0].equals("Y")){
                        int plus = Integer.parseInt(bookAmount[i][1]);
                        plus+=1;
                        bookAmount[i][1] = Integer.toString(plus);
                    }
                    if(bookToken[3].equals(bookAmount[i][0])){
                        int plus = Integer.parseInt(bookAmount[i][2]);
                        plus+=1;
                        bookAmount[i][2] = Integer.toString(plus);
                    }
                }
            }
            //write to the borrowedBooks file, which is the output file
            try {
                File borrowed = new File("borrowedBooks.txt");
                if(!borrowed.exists()){
                    borrowed.createNewFile();
                }
                FileWriter writer = new FileWriter("borrowedBooks.txt");
                BufferedWriter writeIssued = new BufferedWriter(writer);
                String bookInfo = "";
                for(int i = 0; i< bookAmount.length; i++){
                    bookInfo += bookAmount[i][0] + "," + bookAmount[i][1] + "," + bookAmount[i][2] + "\n";
                }
                writeIssued.write(bookInfo);
                writeIssued.close();

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            //print the results
            for(int i = 0; i < bookAmount.length; i++){
                System.out.println(bookAmount[i][0] + " - " + bookAmount[i][1] + "/" + bookAmount[i][2]);
            }
            readIssued.close();
            readIssued.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
