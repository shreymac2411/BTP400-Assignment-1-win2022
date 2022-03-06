package application.adminPackage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

	@FXML
	Button btnLogin;

	@FXML
	TextField txtUserName;

	@FXML
	TextField txtPassword;

	public void initialize(URL location, ResourceBundle resources) {
		final User user = Utils.getLoginData();

		btnLogin.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				if (txtUserName.getText().equals(user.getUserName())
						&& txtPassword.getText().equals(user.getPassword())) {
					Utils.changeScene(event, "Admin.fxml", "Admin Panel", 582, 436, null);
				} else {
					new Alert(Alert.AlertType.CONFIRMATION, "Username or Password incorrect!").showAndWait();
					txtUserName.setText("");
					txtPassword.setText("");
				}

			}
		});
	}

}
