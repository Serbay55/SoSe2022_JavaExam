package backend;

import java.io.IOException;
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
	
	public void listCourse(ActionEvent e) {
		System.out.println("Hello World");
		
	}
	
	public void delCourse(ActionEvent e) {
		System.out.println("deleted");
	}
	
	public void addStudent(ActionEvent e) {
		System.out.println("student added");
	}
	
	public void exmStudent(ActionEvent e) {
		System.out.println("exmatriculated");
	}
	
	@FXML
	private TextField coursesubmit;
	
	public void addCourseNew(ActionEvent i) {
		String coursename;
		coursename = coursesubmit.getText();
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
