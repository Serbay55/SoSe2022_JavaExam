package backend;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import datenbankpaket.Datenbankverbindung;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Controller {
	
	public static long nextidoftable(String tablename) throws SQLException {
		ResultSet res = Datenbankverbindung.runSQLquery("SELECT value FROM enums WHERE id = \""+ tablename +"\"");
		res.next();
		return res.getLong("value");
	}
	
	public void listCourse(ActionEvent e) {
		System.out.println("Hello World");
		
	}
	
	public void delCourse(ActionEvent e) {
		System.out.println("deleted");
	}
	
	
	public void addStudent(ActionEvent e) throws IOException {
		//System.out.println("student added");
		Stage StageRegStud;
		Parent rooter = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("regstud.fxml"));
		Scene SceneRegStud = new Scene(rooter);
		StageRegStud = (Stage) ((Node)e.getSource()).getScene().getWindow();
		StageRegStud.setScene(SceneRegStud);
		StageRegStud.setTitle("Registering Student");
		StageRegStud.show();
		
	}
	
	@FXML
	public TextField vinp;
	public TextField nninp;
	public TextField coursesubmit;
	
	public void addStudentNew(ActionEvent x) {
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
			Datenbankverbindung.runSQL("INSERT INTO Studenten (vorname, nachname) VALUES (\""+vn+"\", \""+nn+"\")");
		} catch (SQLException z) {
			throw new RuntimeException(z);
		}
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
	
	
	
	public void addCourse(ActionEvent e) throws IOException {
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

}
