package backend;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import datenbankpaket.Datenbankverbindung;
import datenbankpaket.PersistentQueries;
import GUI.App;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Controller{

	
	@FXML

	public TextField vinp;
	public TextField nninp;
	public TextField coursesubmit;
	public Slider javaskill;
	public Label MyLabel;
	public TextField companyColumn;
	@FXML private TextField student_id_input;
	@FXML public TableView<Studenten> tableview;
	@FXML private TableColumn<Studenten, String> firstNameColumn;
	@FXML private TableColumn<Studenten, String> lastNameColumn;
	@FXML private TableColumn<Studenten, String> companyColumn2;
	@FXML private TableColumn<Studenten, Integer> jskill;
	@FXML private TableColumn<Studenten, String> kursColumn;
	@FXML private TableColumn<Studenten, Integer> identificator;
	@FXML private TableColumn<Studenten, String> kursraumColumn;
	@FXML private ChoiceBox<String> myChoiceBox;
	@FXML private ChoiceBox<String> MMCBOX;
	@FXML private ChoiceBox<String> roomcb;
	@FXML private javafx.scene.control.Button submitter;
	@FXML private javafx.scene.control.Button cdeleterbutton;
	
	private static List<Studenten> studentenliste;
	public static Parent rootNew = null;
	
	@FXML public void initialize() throws SQLException {
		ResultSet kurse = Datenbankverbindung.runSQLquery("SELECT kurs_name FROM Kurs");
		if(roomcb != null) {
			roomcb.getItems().addAll(roomsList());
		}
		if(MMCBOX != null) {
			MMCBOX.getItems().addAll(coursesList());
		}
		if(myChoiceBox != null) {
			myChoiceBox.getItems().addAll(coursesList());
		}
		
		if(studentenliste == null) {
			studentenliste = new ArrayList<Studenten>();
			tableview.getItems().addAll(studentenlister());
		}
		if(tableview != null) {
			tableview.getItems().addAll(studentenliste);
			identificator.setCellValueFactory(new PropertyValueFactory<Studenten, Integer>("identificator"));
			firstNameColumn.setCellValueFactory(new PropertyValueFactory<Studenten, String>("vorname"));
			lastNameColumn.setCellValueFactory(new PropertyValueFactory<Studenten, String>("nachname"));
			companyColumn2.setCellValueFactory(new PropertyValueFactory<Studenten, String>("firma"));
			jskill.setCellValueFactory(new PropertyValueFactory<Studenten, Integer>("JSkill"));
			kursColumn.setCellValueFactory(new PropertyValueFactory<Studenten, String>("kurs"));
			kursraumColumn.setCellValueFactory(new PropertyValueFactory<Studenten, String>("raumkurs"));
		}
		
		
	}
	

	
	public static List<String> roomsList() throws SQLException {
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Kursraeume");
		List<String> rooms = new ArrayList<String>();
		while(res.next()) {
			rooms.add(res.getString("raum"));
		}
		return rooms;
		
	}
	
	public static List<String> coursesList() throws SQLException{
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Kurs");
		List<String> courses = new ArrayList<String>();
		while(res.next()) {
			courses.add(res.getString("kurs_name"));
		}
		return courses;
		
	}

	
	public static String raumGetter(String s) throws SQLException {
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Kurs WHERE kurs_name = \""+s+"\"");
		String raum = res.getString("kurs_raum");
		return raum;
		
	}
	
	public static List<Studenten> studentenlister() throws SQLException {
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Studenten");
		List<Studenten> studentenList = new ArrayList<Studenten>();
		String kursraum;
		while(res.next()) {
			kursraum = raumGetter(res.getString("kurs"));
			studentenList.add(new Studenten(res.getString("vorname"), res.getString("nachname"), res.getString("firma"), res.getInt("Java_Skill"), res.getString("kurs"), res.getInt("person_id"), kursraum));
		}
		return studentenList;
	}



	public void showStudentCreator(ActionEvent e) throws IOException, SQLException {

		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("studentCreator.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Student anlegen");
		stage.showAndWait();

	}



	public void createStudent(ActionEvent x) throws Exception {

		String firstname = vinp.getText();
		String lastname = nninp.getText();
		String companyname = companyColumn.getText();
		String kurs = myChoiceBox.getValue();
		int jskill = (int)javaskill.getValue();


		Studenten student = new Studenten(firstname,lastname,companyname,jskill,kurs);
		String fehlerhafteDaten = student.checkData();

		if(fehlerhafteDaten.equals("keine")) {

			student.anlegenDB();

			studentenliste.add(new Studenten(firstname, lastname, companyColumn.getText(), jskill, kurs));

			Stage stage = (Stage) submitter.getScene().getWindow();
			stage.close();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Anlage Erfolgreich!");
			alert.setContentText("Der Student '" + firstname + " " + lastname + "' wurde erfolgreich angelegt!");
			alert.showAndWait();

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText(fehlerhafteDaten + " ist leer oder besteht nur aus falschen Zeichen.");
			alert.showAndWait();

		}

	}

	
	public void showStudentDeleter(ActionEvent e) throws IOException {
		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("studentdeleter.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Student exmatrikulieren");
		stage.showAndWait();
	}
	
	public void deleteStudent(ActionEvent e) throws SQLException {
		String id_selection = student_id_input.getText();
		char[] ch  = id_selection.toCharArray();
		StringBuilder strbuild = new StringBuilder();
		for(char c: ch) {
			if(Character.isDigit(c)) {
				strbuild.append(c);
			}
		}
		id_selection = strbuild.toString();
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Studenten WHERE person_id =\""+id_selection+"\"");
		if(id_selection.length() != 0 && res.next()) {
			Datenbankverbindung.runSQL("DELETE FROM Studenten WHERE person_id =\""+id_selection+"\"");

			Stage stage = (Stage) submitter.getScene().getWindow();
			stage.close();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Löschung Erfolgreich!");
			alert.setContentText("Der Student '" + res.getString("vorname") + " " + res.getString("vorname") + "' wurde erfolgreich gelöscht!");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText("Bitte geben Sie eine existierende Studenten-ID ein");
			alert.showAndWait();
		}
		
	}


	public void showCourseCreator(ActionEvent e) throws IOException, SQLException {

		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("courseCreator.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Kurs anlegen");
		stage.showAndWait();

	}


	public void createCourse(ActionEvent i) throws IOException, SQLException {
		String kursraum = roomcb.getValue();
		String kursname = coursesubmit.getText();

		Kurs kurs = new Kurs(kursname,kursraum);
		String fehlerhafteDaten = kurs.checkData();


		if(fehlerhafteDaten.equals("keine") && !kurs.kursNameBelegt() && !kurs.kursRaumBelegt()) {

			kurs.anlegenDB();

			Stage stag = (Stage) submitter.getScene().getWindow();
			stag.close();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Anlage Erfolgreich!");
			alert.setContentText("Der Kurs '" + kursname + "' wurde erfolgreich angelegt!");
			alert.showAndWait();

		}else if(!fehlerhafteDaten.equals("keine")){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText(fehlerhafteDaten + " ist leer oder besteht nur aus falschen Zeichen!");
			alert.showAndWait();
		}
		else if(kurs.kursRaumBelegt()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText("Der Kursraum '" + kursraum + "' ist schon belegt!");
			alert.showAndWait();
		}
		else if(kurs.kursNameBelegt()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText("Es existiert bereits ein Kurs mit dieser Bezeichnung: \""+kursname+"\"");
			alert.showAndWait();
		}


	}


	public void showCourseDeleter(ActionEvent e) throws IOException {
		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("coursedeleter.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Kurs loeschen");
		stage.showAndWait();
	}
	
	public void deleteCourse(ActionEvent e) throws SQLException {
		String courseselection = myChoiceBox.getValue();
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Kurs WHERE kurs_name =\""+courseselection+"\"");



		if (courseselection != null && res.next()) {

			Alert confirmation = new Alert(AlertType.CONFIRMATION, "Yes" + "no", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			confirmation.setTitle("Bestätigung");
			confirmation.setContentText("Achtung, wenn der Kurs gelöscht wird, werden alle Studenten dieses Kurses exmatrikuliert! \nWollen Sie den Kurs '" + courseselection + "' wirklich löschen?");
			confirmation.getDialogPane().setPrefSize(350, 200);
			confirmation.showAndWait();

			if (confirmation.getResult() == ButtonType.YES) {
				Datenbankverbindung.runSQL("DELETE FROM Studenten WHERE kurs = \"" + courseselection + "\"");
				Datenbankverbindung.runSQL("DELETE FROM Kurs WHERE kurs_name = \"" + courseselection + "\"");


				Stage stage = (Stage) cdeleterbutton.getScene().getWindow();
				stage.close();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Löschung Erfolgreich!");
				alert.setContentText("Der Kurs '" + courseselection + "' wurde erfolgreich gelöscht! \nAlle Studenten des Kurses wurden exmatrikuliert!");
				alert.showAndWait();
			}

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText("Wählen Sie bitte einen existierenden Kurs aus");
			alert.showAndWait();
		}

	}
	


	
	public void refreshTable(ActionEvent e) throws Exception {
		studentenliste.removeAll(studentenliste);
		studentenliste = null;
		if(studentenliste == null) {
			tableview.getItems().clear();
			studentenliste = new ArrayList<Studenten>();
			tableview.getItems().addAll(studentenlister());
		}
		
		MMCBOX = null;
		if(MMCBOX == null) {
			MMCBOX = new ChoiceBox<String>(FXCollections.observableArrayList(coursesList()));
		}
		
		
	}
	
	public List<Studenten> sortedList() throws SQLException{
		String mmcbox = MMCBOX.getValue();
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Studenten WHERE kurs = \""+mmcbox+"\"");
		List<Studenten> stdlist = new ArrayList<Studenten>();
		while(res.next()) {
			String kursraum;
			kursraum = raumGetter(res.getString("kurs"));
			stdlist.add(new Studenten(res.getString("vorname"), res.getString("nachname"), res.getString("firma"), res.getInt("Java_Skill"), res.getString("kurs"), res.getInt("person_id"), kursraum));
		}
		return stdlist;
		
		
	}
	
	public void sortList(ActionEvent e) throws SQLException {
		studentenliste.removeAll(studentenliste);
		studentenliste = null;
		if(studentenliste == null) {
			tableview.getItems().clear();
			studentenliste = new ArrayList<Studenten>();
			tableview.getItems().addAll(sortedList());
		}
	}
	


	

	
	public void openHomescreen(ActionEvent e) throws Exception {
		Stage newStage;
		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("gui.fxml"));
		Scene newScene = new Scene(root);
		newStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
		newStage.setScene(newScene);
		newStage.setTitle("University Management");
		newStage.show();
		refreshTable(e);
		
		
	}
	


}
