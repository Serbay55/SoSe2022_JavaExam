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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	@FXML
	public TextField vinp;
	public TextField nninp;
	public TextField coursesubmit;
	public Slider javaskill;
	public Label MyLabel;
	public TextField companyColumn;
	@FXML private TableView<Studenten> tableview;
	@FXML private TableColumn<Studenten, String> firstNameColumn;
	@FXML private TableColumn<Studenten, String> lastNameColumn;
	@FXML public TableColumn<Studenten, String> companyColumn2;
	@FXML private TableColumn<Studenten, Integer> jskill;
	@FXML private TableColumn<Studenten, Kursraum> roomColumn;
	
	private static List<Studenten> studentenliste;
	
	@FXML public void initialize() {
		if(studentenliste == null) {
			studentenliste = new ArrayList();
		}
		if(tableview != null) {
			tableview.getItems().addAll(studentenliste);
			firstNameColumn.setCellValueFactory(new PropertyValueFactory<Studenten, String>("vorname"));
			lastNameColumn.setCellValueFactory(new PropertyValueFactory<Studenten, String>("nachname"));
			companyColumn2.setCellValueFactory(new PropertyValueFactory<Studenten, String>("firma"));
			jskill.setCellValueFactory(new PropertyValueFactory<Studenten, Integer>("JSkill"));
		}
		
	}
	
	/*public static long nextidoftable(String tablename) throws SQLException {
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT value FROM enums WHERE id = \""+ tablename +"\"");
		res.next();
		return res.getLong("value");
	}*/
	public static List<Kursraum> scheiße() throws SQLException {
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Kursraeume");
		List<Kursraum> räume = new ArrayList<Kursraum>(); 
		while(res.next()) {
			räume.add(new Kursraum(res.getInt("room_id"), res.getString("raum")));
		}
		String[] rem = null;
		for(Kursraum raum: räume) {
			System.out.println(raum);
		}
		return räume;
	}
	public static List<Studenten> scheiße2() throws SQLException {
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT * FROM Kursraeume");
		List<Studenten> stdlist = new ArrayList<Studenten>(); 
		while(res.next()) {
			stdlist.add(new Studenten(null, null, null, 0));
		}
		return stdlist;
	}
	
	
	
	public void listCourse(ActionEvent e) throws SQLException {
		ResultSet res1 = Datenbankverbindung.runSQLquery("SELECT vorname FROM Studenten");
		List<Studenten> studies = new ArrayList<Studenten>();
		while(res1.next()) {
			studies.add(new Studenten(res1.getString("vorname"), res1.getString("nachname"), res1.getString("nachname"), res1.getInt("Java_Skill")));
		}
	}
	
	public void delCourse(ActionEvent e) {
		System.out.println("deleted");
	}
	
	
	public void addStudent(ActionEvent e) throws IOException, SQLException {
		//System.out.println("student added");
		Stage StageRegStud;
		Parent rooter = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("regstud.fxml"));
		Scene SceneRegStud = new Scene(rooter);
		StageRegStud = (Stage) ((Node)e.getSource()).getScene().getWindow();
		StageRegStud.setScene(SceneRegStud);
		StageRegStud.setTitle("Registering Student");
		StageRegStud.show();
		ArrayList List = new ArrayList();
		ResultSet s = Datenbankverbindung.runSQLquery("SELECT COUNT(course_id) FROM Kurs;");
		for (int i = 1; i< s.getRow(); i++) {
			List.add(Datenbankverbindung.runSQLquery("SELECT kurs_name FROM Kurs WHERE course_id = \""+i+"\""));
			System.out.println(List.get(i));
		}
		
		
		
	}
	

	

	
	

	
	public void listrows(ActionEvent e) {
		
	}
	
	public void addCourseRoomToCourse(ActionEvent e) {
		
	}


	public void addStudentNew(ActionEvent x) {
		int jskill = (int)javaskill.getValue();
		String vn;
		vn = vinp.getText();
		char[] ch = vn.toCharArray();
		StringBuilder strbuildregstud = new StringBuilder();
		for(char c : ch) {
			if(Character.isAlphabetic(c)) {
				strbuildregstud.append(c);
			}
		}
		vn = strbuildregstud.toString();
		
		String nn;
		nn = nninp.getText();
		char[] es = nn.toCharArray();
		StringBuilder strbuildregstud2 = new StringBuilder();
		for(char e : es) {
			if(Character.isAlphabetic(e)) {
				strbuildregstud2.append(e);
			}
		}
		nn = strbuildregstud2.toString();
		try {
			Datenbankverbindung.runSQL("INSERT INTO Studenten (vorname, nachname, Java_Skill) VALUES (\""+vn+"\", \""+nn+"\", \""+jskill+"\");");
		} catch (SQLException z) {
			throw new RuntimeException(z);
		}
		studentenliste.add(new Studenten(vn, nn, companyColumn.getText().toString(), jskill));

	}
	
	public void exmStudent(ActionEvent e) {
		System.out.println("exmatriculated");
	}
	
	

	
	public void addCourseNew(ActionEvent i) {
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
		try {
			Datenbankverbindung.runSQL("INSERT INTO Kurs (kurs_name) VALUES (\""+coursename+"\")");
		} catch (SQLException x) {
			throw new RuntimeException(x);
		}
	}
	
	
	
	public void addCourse(ActionEvent e) throws IOException, SQLException {
		Stage newStage;
		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("coursenamereq.fxml"));
		Scene newScene = new Scene(root);
		newStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
		newStage.setScene(newScene);
		newStage.setTitle("Input");
		newStage.show();
		/*String coursename;
		coursename = coursesubmit.getText();
		try {
			Datenbankverbindung.runSQL("INSERT INTO Kurs (kurs_name) VALUES (\""+coursename+"\")");
		} catch (SQLException x) {
			throw new RuntimeException(x);
		}*/
	}
	
	public void openHomescreen(ActionEvent e) throws IOException {
		Stage newStage;
		Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("gui.fxml"));
		Scene newScene = new Scene(root);
		newStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
		newStage.setScene(newScene);
		newStage.setTitle("Main Menu");
		newStage.show();
	}


}
