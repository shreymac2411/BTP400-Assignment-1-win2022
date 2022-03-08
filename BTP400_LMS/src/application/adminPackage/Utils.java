package application.adminPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Utils {
	public static ArrayList<Librarian> readLibrarians() {

		ArrayList<Librarian> librarians = new ArrayList<Librarian>();
		Scanner input = null;
		try {
			input = new Scanner(new File("src/application/adminPackage/librarian.txt"));
			input.useDelimiter("\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (input.hasNext()) {
			String id = input.next();
			String firstName = input.next();
			String lastName = input.next();
			String gender = input.next();
			String phoneNo = input.next();
			Librarian librarian = new Librarian(id, firstName, lastName, gender, phoneNo);
			librarians.add(librarian);

		}

		return librarians;

	}

	public static void writeDataOnFile(ArrayList<Librarian> librarians) {
		File file = new File("src/application/adminPackage/librarian.txt");
		FileWriter fr = null;
		try {
			fr = new FileWriter(file, false);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		for (Librarian librarian : librarians) {
			// formatted string to write on the file, each recipe is written in the same
			// format
			String data = librarian.getId() + "\n" + librarian.getFirstName() + "\n" + librarian.getLastName() + "\n"
					+ librarian.getGender() + "\n" + librarian.getPhoneNo() + "\n";
			try {
				fr.write(data);

			} catch (IOException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}

		}
		try {
			fr.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static User getLoginData() {
		User user = null;
		Scanner input = null;
		try {

			input = new Scanner(new File("src/application/adminPackage/user.txt"));
			input.useDelimiter("\r\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (input.hasNext()) {
			String userName = input.next();
			String password = input.next();
			user = new User(userName, password);
		}

		return user;
	}

	// method to change scenes by loading different fxml files
	public static void changeScene(ActionEvent event, String fxmlFile, String title, int width, int height,
			String data) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(Utils.class.getResource(fxmlFile));
			root = loader.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle(title);
		stage.setUserData(data);// this method transfer player name between scenes
		stage.setScene(new Scene(root, width, height));

	}

}
