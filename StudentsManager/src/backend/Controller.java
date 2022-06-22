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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Controller{
	/*
	 * 
	 * Sehr geehrter Herr Sebastian Damm,
	 * 
	 * falls Sie sich wundern, warum ich nur ein Controller habe,
	 * da habe ich nichts zu rechtfertigen. Ich bin nämlich heimlich
	 * Real Mitarbeiter. Bei uns heißt es "Einmal hin, alles drin"
	 * 
	 * viel Spaß beim lesen.
	 * 
	 * ne wirklich haben Sie verständnis ich musste so einiges Alleine machen
	 */
	
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
	@FXML public TableColumn<Studenten, String> companyColumn2;
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
			roomcb.getItems().addAll(courseroomslist());
		}
		if(MMCBOX != null) {
			MMCBOX.getItems().addAll(mmcboxcontent());
		}
		if(myChoiceBox != null) {
			myChoiceBox.getItems().addAll(mmcboxcontent());
		}
		
		if(studentenliste == null) {
			studentenliste = new ArrayList<Studenten>();
			tableview.getItems().addAll(studentenlister());
		}
		if(tableview != null) {
			tableview.getItems().addAll(studentenliste);
			firstNameColumn.setCellValueFactory(new PropertyValueFactory<Studenten, String>("vorname"));
			lastNameColumn.setCellValueFactory(new PropertyValueFactory<Studenten, String>("nachname"));
			companyColumn2.setCellValueFactory(new PropertyValueFactory<Studenten, String>("firma"));
			jskill.setCellValueFactory(new PropertyValueFactory<Studenten, Integer>("JSkill"));
			kursColumn.setCellValueFactory(new PropertyValueFactory<Studenten, String>("kurs"));
			identificator.setCellValueFactory(new PropertyValueFactory<Studenten, Integer>("identificator"));
			kursraumColumn.setCellValueFactory(new PropertyValueFactory<Studenten, String>("raumkurs"));
		}
		
		
	}
	

	
	public static List<String> courseroomslist() throws SQLException {
		ResultSet rese = Datenbankverbindung.runSQLquery("SELECT * FROM Kursraeume");
		List<String> rooms = new ArrayList<String>();
		while(rese.next()) {
			rooms.add(rese.getString("raum"));
		}
		return rooms;
		
	}
	
	public static List<String> mmcboxcontent() throws SQLException{
		ResultSet rese = Datenbankverbindung.runSQLquery("SELECT * FROM Kurs");
		List<String> courses = new ArrayList<String>();
		while(rese.next()) {
			courses.add(rese.getString("kurs_name"));
		}
		return courses;
		
	}

	
	public static String Kursgetter(String s) throws SQLException {
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Kurs WHERE kurs_name = \""+s+"\"");
		String k = res.getString("kurs_raum");
		return k;
		
	}
	
	public static List<Studenten> studentenlister() throws SQLException {
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Studenten");
		List<Studenten> stdlist = new ArrayList<Studenten>();
		String kursraum;
		while(res.next()) {
			kursraum = Kursgetter(res.getString("kurs"));
			stdlist.add(new Studenten(res.getString("vorname"), res.getString("nachname"), res.getString("firma"), res.getInt("Java_Skill"), res.getString("kurs"), res.getInt("person_id"), kursraum));
		}
		return stdlist;
	}
	
	public void renderdelStudent(ActionEvent e) throws IOException {
		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("studentdeleter.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Student exmatrikulieren");
		stage.showAndWait();
	}
	
	public void delStudent(ActionEvent e) throws SQLException {
		String id_selection = student_id_input.getText();
		char[] ch  = id_selection.toCharArray();
		StringBuilder strbuild = new StringBuilder();
		for(char c: ch) {
			if(Character.isDigit(c)) {
				strbuild.append(c);
			}
		}
		id_selection = strbuild.toString();
		if(id_selection.length() != 0) {
			Datenbankverbindung.runSQL("DELETE FROM Studenten WHERE person_id =\""+id_selection+"\"");
			Stage stage = (Stage) submitter.getScene().getWindow();
			stage.close();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText("Bitte geben Sie eine Studenten ID von der Tabelle ein");
			alert.showAndWait();
		}
		
	}
	
	public static List<Kurs> courseselect() throws SQLException {
		ResultSet res1 = Datenbankverbindung.runSQLquery("SELECT * FROM Kurs");
		List<Kurs> kurse = new ArrayList<Kurs>();
		while(res1.next()) {
			kurse.add(new Kurs(res1.getString("kurs_name"), res1.getString("kurs_raum")));
		}
		return kurse;
	}
	
	public void delCourse(ActionEvent e) throws IOException {
		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("coursedeleter.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Kurs loeschen");
		stage.showAndWait();
	}
	
	public void delCourseAction(ActionEvent e) throws SQLException {
		String courseselection = myChoiceBox.getValue();
		if(courseselection != null) {
			Datenbankverbindung.runSQL("DELETE FROM Studenten WHERE kurs = \""+courseselection+"\"");
			Datenbankverbindung.runSQL("DELETE FROM Kurs WHERE kurs_name = \""+courseselection+"\"");
			Stage stage = (Stage) cdeleterbutton.getScene().getWindow();
			stage.close();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText("Wählen Sie bitte ein Kurs aus");
			alert.showAndWait();
		}
	}
	
	public void addStudent(ActionEvent e) throws IOException, SQLException {
		
		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("regstud.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Student anlegen");
		stage.showAndWait();
		
	}
	
	public void starter(ActionEvent x) throws IOException {
		Stage newStage;
		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("gui.fxml"));
		Scene newScene = new Scene(root);
		newStage = (Stage) ((Node)x.getSource()).getScene().getWindow();
		newStage.setScene(newScene);
		newStage.setTitle("Main Menu");
		newStage.show();
		
	}
	

	
	public void stagecloser(ActionEvent e) {
		Stage stage = (Stage) submitter.getScene().getWindow();
		stage.close();
	}
	


	public void addStudentNew(ActionEvent x) throws Exception {
		String selection = myChoiceBox.getValue();
		String companyname = companyColumn.getText();
		int jskill = (int)javaskill.getValue();
		String vn;
		vn = vinp.getText();
		String nn;
		nn = nninp.getText();
		if(selection != null && companyname != null && vn != null && nn != null) {
			char[] ch = vn.toCharArray();
			StringBuilder strbuildregstud = new StringBuilder();
			for(char c : ch) {
				if(Character.isAlphabetic(c) || Character.isSpace(c)) {
					strbuildregstud.append(c);
				}
			}
			vn = strbuildregstud.toString();
			
			//
			char[] es = nn.toCharArray();
			StringBuilder strbuildregstud2 = new StringBuilder();
			for(char e : es) {
				if(Character.isAlphabetic(e) || Character.isSpace(e)) {
					strbuildregstud2.append(e);
				}
			}
			nn = strbuildregstud2.toString();
			char[] cname = companyname.toCharArray();
			StringBuilder strbuildcname = new StringBuilder();
			for(char y : cname) {
				if(Character.isAlphabetic(y) || Character.isDigit(y) || Character.isSpace(y)) {
					strbuildcname.append(y);
				}
			}
			companyname = strbuildcname.toString();
			if(vn.length() != 0 && nn.length() != 0 && companyname.length() != 0) {
				try {
					Datenbankverbindung.runSQL("INSERT INTO Studenten (vorname, nachname, Java_Skill, firma, kurs) VALUES (\""+vn+"\", \""+nn+"\", \""+jskill+"\", \""+companyname+"\", \""+selection+"\");");
				} catch (SQLException z) {
					throw new RuntimeException(z);
				}
				studentenliste.add(new Studenten(vn, nn, companyColumn.getText().toString(), jskill, selection));
				
				Stage stage = (Stage) submitter.getScene().getWindow();
				stage.close();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler!");
				alert.setContentText("Vorname / Nachname / Firmenname sind falsch eingegeben worden oder nur Zahlen.");
				alert.showAndWait();
						
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText("Keine leere Eingaben!");
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
			MMCBOX = new ChoiceBox<String>(FXCollections.observableArrayList(mmcboxcontent()));
		}
		
		
	}
	
	public List<Studenten> sortedList() throws SQLException{
		String mmcbox = MMCBOX.getValue();
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Studenten WHERE kurs = \""+mmcbox+"\"");
		List<Studenten> stdlist = new ArrayList<Studenten>();
		String kursraum;
		while(res.next()) {
			kursraum = Kursgetter(res.getString("kurs"));
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
	
	public static boolean räumecheck(String s) throws SQLException{
		ResultSet rs = Datenbankverbindung.runSQLquery("SELECT kurs_raum FROM Kurs WHERE kurs_raum = \""+s+"\"");
		List<String> belegteKursräume = new ArrayList<String>();
		while(rs.next()) {
			belegteKursräume.add(rs.getString("kurs_raum"));
		}
		boolean x = false;
		if(belegteKursräume.size() == 0) {
			x = true;
		}
		return x;
	}
	
	public static boolean kurscheck(String s) throws SQLException{
		ResultSet rs = Datenbankverbindung.runSQLquery("SELECT kurs_name FROM Kurs WHERE kurs_name = \""+s+"\"");
		List<String> belegtekursnamen = new ArrayList<String>();
		while(rs.next()) {
			belegtekursnamen.add(rs.getString("kurs_name"));
		}
		boolean x = false;
		if(belegtekursnamen.size() == 0) {
			x = true;
		}
		return x;
	}

	
	public void addCourseNew(ActionEvent i) throws IOException, SQLException {
		String kursraum = roomcb.getValue();
		String coursename;
		coursename = coursesubmit.getText();
		char[] ch = coursename.toCharArray();
		StringBuilder strbuild = new StringBuilder();
		for(char c : ch) {
			if(Character.isAlphabetic(c) || Character.isDigit(c)) {
				strbuild.append(c);
			}
		}
		coursename = strbuild.toString();
		boolean kursnamecheck = kurscheck(coursename);
		boolean dbcheck = räumecheck(kursraum);
		if(kursraum != null && coursename != null && dbcheck && kursnamecheck) {
			try {
				Datenbankverbindung.runSQL("INSERT INTO Kurs (kurs_name, kurs_raum) VALUES (\""+coursename+"\", \""+kursraum+"\")");
			} catch (SQLException x) {
				throw new RuntimeException(x);
			}
			Stage stag = (Stage) submitter.getScene().getWindow();
			stag.close();
		} else if(kursraum != null && coursename != null && dbcheck == false && kursnamecheck) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText("Der "+kursraum+" Kursraum ist schon belegt");
			alert.showAndWait();
		} else if(kursraum != null && coursename != null && dbcheck && kursnamecheck == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText("Es existiert bereits ein Kurs mit dieser Bezeichnung: \""+coursename+"\"");
			alert.showAndWait();
		} else if(kursraum != null && coursename != null && dbcheck == false && kursnamecheck == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText("Es existiert bereits ein Kurs mit der Bezeichnung \""+coursename+"\" und der Kursraum "+kursraum+" ist schon belegt");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler!");
			alert.setContentText("Keine Sonderzeichen oder leere Eingaben toleriert");
			alert.showAndWait();
		}
		
		
	}
	
	
	
	public void addCourse(ActionEvent e) throws IOException, SQLException {

		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("coursenamereq.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Kurs anlegen");
		stage.showAndWait();

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
