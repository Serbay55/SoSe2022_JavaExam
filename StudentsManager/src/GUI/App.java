package GUI;

import java.io.IOException;
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
            	Datenbankverbindung.runSQL("CREATE TABLE IF NOT EXISTS Studenten (person_id INTEGER PRIMARY KEY, vorname varchar(30), nachname varchar(30), firma varchar(30), Java_Skill int, kurs varchar(30) );");
            	Datenbankverbindung.runSQL("CREATE TABLE IF NOT EXISTS Kursraeume (room_id INTEGER PRIMARY KEY, raum varchar(25));");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"B048\");");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"online\");");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"Audimax\");");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"Carl-Benz-Hörsaal\");");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"B040\");");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"B041\");");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"B042\");");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"B043\");");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"B044\");");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"B045\");");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"B046\");");
            	Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\"B047\");");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        //System.out.println(Controller.nextidoftable("Kurs"));
        
        

        //Controller.scheiße();
        
        Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("gui.fxml"));
        backend.Controller.rootNew = root;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("University Management");
        stage.show();
    }
}
