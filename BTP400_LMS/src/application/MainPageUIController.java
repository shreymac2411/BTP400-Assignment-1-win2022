package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPageUIController {
	private Stage stage;
	private Scene scene;
	private Parent root;

	public void AdminLogin(ActionEvent e) throws IOException {
		try {
			root = FXMLLoader.load(getClass().getResource("adminPackage/LoginPage.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception error) {
			// TODO: handle exception
			System.out.println(error);
		}
	}

	public void LibrarianLogin(ActionEvent e) throws IOException {
		try {
			root = FXMLLoader.load(getClass().getResource("librarianPackage/LoginPage.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception error) {
			// TODO: handle exception
			System.out.println(error);
		}
	}
	
	public void StudentLogin(ActionEvent e) throws IOException {
		try {
			root = FXMLLoader.load(getClass().getResource("studentPackage/LoginPage.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception error) {
			// TODO: handle exception
			System.out.println(error);
		}
	}
}
