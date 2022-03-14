package application.studentPackage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utill.Database;

/**
 * Represents the Student module's functionality within the DB with UI
 *
 * @author Shrey
 */
public class StudentUIController implements Initializable {
	private Stage stage;
	private Scene scene;
	private Parent root;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public void changeScene(String location, ActionEvent e) {
		try {
			root = FXMLLoader.load(getClass().getResource(location));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception error) {
			// TODO: handle exception
			System.out.println(error);
		}
	}

	public void BackButtonLoginpage(ActionEvent e) throws IOException {
		changeScene("../mainScreen.fxml", e);
	}

	public void LoginBtnLoginPage(ActionEvent e) throws IOException {
		changeScene("Student.fxml", e);
		Database db = new Database();
		db.log(new Date() + ": Student Login");
	}

	public void LogoutButtonStudent(ActionEvent e) throws IOException {
		changeScene("../mainScreen.fxml", e);
		Database db = new Database();
		db.log(new Date() + ": Student Logout");
	}
}
