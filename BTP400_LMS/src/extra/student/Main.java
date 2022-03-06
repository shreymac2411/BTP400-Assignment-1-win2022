package extra.student;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        int login = 0;
        String id = "";
        String pass = "";
        String type = "";

        while(login == 0){
            System.out.println("Enter your student id");
            id = myObj.nextLine();  // Read user input

            System.out.println("Enter your password");
            pass = myObj.nextLine();  // Read user input

            if(Student.login(id, pass)){
                System.out.println("Login Successful!");
                login = 1;
            }
            else{
                System.out.println("Login invalid, Please try again");
            }
        }
        Student stu = new Student(id, pass);

        System.out.println("Enter a book you want to see: ");
        String searchedBook = myObj.nextLine();  // Read user input
        Student.searchBook(searchedBook);

        System.out.println("Enter a book you want to request: ");
        String requestBook = myObj.nextLine();  // Read user input
        stu.checkIfIssued(requestBook);

        System.out.println("Check book quantity? (Y/N)");
        String input = myObj.nextLine();  // Read user input
        String formattedInput = input.toUpperCase();
        if(formattedInput.equals("Y")){
            Student.viewBorrowed();
        }
        else if(formattedInput.equals("N")){
            System.out.println("You decided to not check the book quantity");
        }
        else{
            System.out.println("Please enter (Y/N)");
        }
    }
}
