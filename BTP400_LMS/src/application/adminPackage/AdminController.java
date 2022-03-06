package application.adminPackage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminController implements Initializable {

	@FXML
	TextField txtId;

	@FXML
	TextField txtFirstName;

	@FXML
	TextField txtLastName;

	@FXML
	TextField txtGender;

	@FXML
	TextField txtPhoneNo;

	@FXML
	Button btnSave;

	@FXML
	Button btnRemove;

	@FXML
	Button btnSaveOnFile;

	@FXML
	ComboBox<String> CB_Librarians;

	@FXML
	TableView<Librarian> tblView;

	@FXML
	TableColumn id;

	@FXML
	TableColumn firstName;

	@FXML
	TableColumn lastName;

	@FXML
	TableColumn gender;

	@FXML
	TableColumn phoneNo;
	
	@FXML
	Tab tabShowReport;

	private ArrayList<Librarian> librarians = new ArrayList<Librarian>();

	public void initialize(URL location, ResourceBundle resources) {

		librarians = Utils.readLibrarians();

		// associating table column names with Librarian class members
		id.setCellValueFactory(new PropertyValueFactory("id"));
		firstName.setCellValueFactory(new PropertyValueFactory("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory("lastName"));
		gender.setCellValueFactory(new PropertyValueFactory("gender"));
		phoneNo.setCellValueFactory(new PropertyValueFactory("phoneNo"));
		
		for(Librarian librarian: librarians) {
			CB_Librarians.getItems().add(librarian.getFirstName() + " " + librarian.getLastName());
		}
		
		tabShowReport.setOnSelectionChanged(new EventHandler<Event>() {
			
			public void handle(Event event) {
				// settings librarians array as dataset to tableview
				tblView.getItems().setAll(librarians);
			
			}
		});
		
		btnSave.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				Librarian librarian = new Librarian(txtId.getText(), txtFirstName.getText(),
						txtLastName.getText(), txtGender.getText(), txtPhoneNo.getText());
				librarians.add(librarian);
				CB_Librarians.getItems().clear();
				for(Librarian lib: librarians) {
					CB_Librarians.getItems().add(lib.getFirstName() + " " + lib.getLastName());
				}
				new Alert(Alert.AlertType.CONFIRMATION, "Librarian Added!").showAndWait();
				Utils.writeDataOnFile(librarians);
				txtId.setText("");
				txtFirstName.setText("");
				txtLastName.setText("");
				txtGender.setText("");
				txtPhoneNo.setText("");


			}
		});

		btnSaveOnFile.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				Utils.writeDataOnFile(librarians);
				new Alert(Alert.AlertType.CONFIRMATION, "Saved on File!").showAndWait();
			}
		});
		
		btnRemove.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				String name = CB_Librarians.getSelectionModel().getSelectedItem();
				int index = -1;
				for(Librarian librarian :librarians) {
					if((librarian.getFirstName() + " " + librarian.getLastName()).equals(name)){
						index = librarians.indexOf(librarian);
					}
				}
				if(index!=-1) {
				librarians.remove(index);
				new Alert(Alert.AlertType.CONFIRMATION, "Librarian Removed!").showAndWait();
				}
				
				Utils.writeDataOnFile(librarians);
				CB_Librarians.getItems().clear();
				for(Librarian librarian: librarians) {
					CB_Librarians.getItems().add(librarian.getFirstName() + " " + librarian.getLastName());
				}
				
			}
		});

	}

	
}
