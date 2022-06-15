package GUI;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import backend.Controller;
import datenbankpaket.Datenbankverbindung;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage stage) throws Exception{
		boolean is_new = !Files.exists(Paths.get("login.db"));
        try{
            if(is_new) {
            	Datenbankverbindung.runSQL("CREATE TABLE IF NOT EXISTS Kurs (course_id INTEGER PRIMARY KEY, kurs_name varchar(30), kurs_raum varchar(30));");
            	Datenbankverbindung.runSQL("CREATE TABLE IF NOT EXISTS Studenten (person_id INTEGER PRIMARY KEY, vorname varchar(30), nachname varchar(30), course_id int, Java_Skill int );");
            	Datenbankverbindung.runSQL("CREATE TABLE IF NOT EXISTS Kursraeume (room_id INTEGER PRIMARY KEY, raum varchar(5));");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"B048\");");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"B040\");");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        //System.out.println(Controller.nextidoftable("Kurs"));
        
    

        Controller.scheiße();
        Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("gui.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("University Management");
        stage.show();
    }
}
