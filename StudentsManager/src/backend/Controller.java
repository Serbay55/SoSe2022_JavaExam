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

	/**
	 * Initialisiert die Tabellen und Daten beim Start der Anwendung
	 * @throws SQLException
	 */
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


	/**
	 * Erzeugt eine Liste mit allen in der DB gespeicherten Räumen
	 * @return StringListe mit allen Räumen aus der DB
	 * @throws SQLException
	 */
	public static List<String> roomsList() throws SQLException {
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Kursraeume");
		List<String> rooms = new ArrayList<String>();
		while(res.next()) {
			rooms.add(res.getString("raum"));
		}
		return rooms;
		
	}


	/**
	 * Erzeugt eine Liste mit allen in der DB gespeicherten Kursen
	 * @return StringListe mit allen Kursen aus der DB
	 * @throws SQLException
	 */
	public static List<String> coursesList() throws SQLException{
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Kurs");
		List<String> courses = new ArrayList<String>();
		while(res.next()) {
			courses.add(res.getString("kurs_name"));
		}
		return courses;
		
	}


	/**
	 * Gibt den aktuellen Raum für einen bestimmten Kurs an
	 * @param kursname String: Kursname, der ausgewertet werden soll
	 * @return String: Kursraum, der aktuell in der DB gespeichert ist
	 * @throws SQLException
	 */
	public static String raumGetter(String kursname) throws SQLException {
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Kurs WHERE kurs_name = \""+kursname+"\"");
		String raum = res.getString("kurs_raum");
		return raum;

	}


	/**
	 * Gibt eine Liste mit allen Studenten aus der DB an
	 * @return StringListe mit allen Studenten, die in der DB gespeichert sind
	 * @throws SQLException
	 */
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


	/**
	 * Öffnet das Menü zum Anlegen eines Studenten
	 * @param e ActionEvent, das durch Klicken auf button 'Student hinzufügen' ausgelöst wird
	 * @throws IOException
	 * @throws SQLException
	 */
	public void showStudentCreator(ActionEvent e) throws IOException, SQLException {

		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("studentCreator.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Student anlegen");
		stage.showAndWait();

	}



	/**
	 * Legt einen neuen Studenten an und gibt eine Erfolgs- oder Fehlermeldung aus
	 * @param x ActionEvent, das durch Klicken auf button 'Student anlegen' ausgelöst wird
	 * @throws IOException
	 * @throws SQLException
	 */
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

	/**
	 * Öffnet das Menü zum Löschen eines Studenten
	 * @param e ActionEvent, das durch Klicken auf button 'Student exmatrikulieren' ausgelöst wird
	 * @throws IOException
	 * @throws SQLException
	 */
	public void showStudentDeleter(ActionEvent e) throws IOException {
		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("studentdeleter.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Student exmatrikulieren");
		stage.showAndWait();
	}

	/**
	 * Löscht einen Studenten und gibt Erfogls- oder Fehlermeldungen aus
	 * @param e Int: ActionEvent, das durch Klicken auf button 'Student exmatrikulieren' ausgelöst wird
	 * @throws SQLException
	 */
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

	/**
	 * Öffnet das Menü zum Anlegen eines Kurses
	 * @param e ActionEvent, das durch Klicken auf button 'Kurs anlegen' ausgelöst wird
	 * @throws IOException
	 * @throws SQLException
	 */
	public void showCourseCreator(ActionEvent e) throws IOException, SQLException {

		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("courseCreator.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Kurs anlegen");
		stage.showAndWait();

	}

	/**
	 * Legt einen neuen Kurs an und gibt eine Erfolgs- oder Fehlermeldung aus
	 * @param i ActionEvent, das durch Klicken auf button 'Kurs anlegen' ausgelöst wird
	 * @throws IOException
	 * @throws SQLException
	 */
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

	/**
	 * Öffnet das Menü zum Löschen eines Kursen
	 * @param e ActionEvent, das durch Klicken auf button 'Kurs löschen' ausgelöst wird
	 * @throws IOException
	 * @throws SQLException
	 */
	public void showCourseDeleter(ActionEvent e) throws IOException {
		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("coursedeleter.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Kurs loeschen");
		stage.showAndWait();
	}



	/**
	 * Löscht den Kurs und gibt eine Erfogls- oder Fehlermeldungen aus
	 * @param e ActionEvent, das durch Klicken auf button 'Kurs löschen' ausgelöst wird
	 * @throws SQLException
	 */
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


	/**
	 * Aktualisiert die in der GUI angezeigten Studenten anhand der aktuellen Daten in der DB
	 * @throws Exception
	 */
	public void refreshTable() throws Exception {
		studentenliste.removeAll(studentenliste);

		tableview.getItems().clear();
		tableview.getItems().addAll(studentenlister());

		MMCBOX.setValue(null);

	}


	/**
	 * Erzeugt eine nach Kursen gefilterte Studtenliste anhand des Wertes in der GUI
	 * @return StudentenListe mit nach Kursen gefilterten Studenten
	 * @throws SQLException
	 */
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


	/**
	 * Zeigt die gefilterten Studenten in der GUI an
	 * @param e ActionEvent, das durch Klicken auf button 'Kurs filtern' ausgelöst wird
	 * @throws SQLException
	 */
	public void sortList(ActionEvent e) throws SQLException {
		studentenliste.removeAll(studentenliste);

		tableview.getItems().clear();
		tableview.getItems().addAll(sortedList());
	}


	/**
	 * Öffnet das Hilfe-Menü

	 * @throws IOException
	 * @throws SQLException
	 */
	public void showHelp() throws IOException {
		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("information.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Hilfe");
		stage.showAndWait();
	}


}
