package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		try {
			GridPane root = new GridPane();
			root.setHgap(10);
			root.setVgap(10);
			root.setAlignment(Pos.CENTER);
			Scene scene = new Scene(root, 480, 360);
			stage.setScene(scene);
			Image icon = new Image("mainIcon.png");
			stage.setTitle("Library Management System");
			stage.getIcons().add(icon);
			stage.setResizable(false);

			Label FL = new Label("Are you a :");
			
			Button adminButton = new Button("Admin");
			
			Button librarianButton = new Button("Librarian");
			
			Button studentButton = new Button("Student");
			
			root.addRow(0, FL);
			root.addRow(1, adminButton);
			root.addRow(2, librarianButton);
			root.addRow(3, studentButton);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
