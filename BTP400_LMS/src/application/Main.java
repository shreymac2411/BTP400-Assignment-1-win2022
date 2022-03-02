package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;

//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		try {
			Group root = new Group();
			Scene scene = new Scene(root, 480, 360);
			stage.setScene(scene);
			Image icon = new Image("mainIcon.png");
			stage.setTitle("Library Management System");
			stage.getIcons().add(icon);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
