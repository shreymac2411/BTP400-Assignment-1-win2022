package application.adminPackage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utill.AdminController;
import utill.Database;

/**
 * Represents the Admin module's functionality within the DB with UI
 *
 * @author Shrey
 */
public class AdminUIController implements Initializable {
	AdminController admin;
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	Label errorLogin;
	@FXML
	TextField userNameLogin;
	@FXML
	PasswordField passwordLogin;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		admin = new AdminController();
		System.out.println("admin panel accesed");
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
		if (admin.login(userNameLogin.getText(), passwordLogin.getText())) {
			changeScene("Admin.fxml", e);
		} else {
			userNameLogin.setText("");
			passwordLogin.setText("");
			errorLogin.setOpacity(1);
		}
	}
	
	public void LogoutButtonAdmin(ActionEvent e) throws IOException {
		changeScene("../mainScreen.fxml", e);
		Database db = new Database();
		db.log(new Date() + ": Admin Logout");
	}
	
}
