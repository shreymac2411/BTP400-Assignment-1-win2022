package application.studentPackage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Represents the Student module's functionality within the DB with UI
 *
 * @author Shrey
 */
public class StudentUIController implements Initializable{
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
}
