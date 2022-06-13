package GUI;

import java.nio.file.Files;
import java.nio.file.Paths;
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
            	Datenbankverbindung.runSQL("CREATE TABLE IF NOT EXISTS Kurs (kurs_name varchar(30));");
            	Datenbankverbindung.runSQL("CREATE TABLE IF NOT EXISTS Studenten (id varchar(30), vorname varchar(30), nachname varchar(30), kursid int );");
            	Datenbankverbindung.runSQL("CREATE TABLE IF NOT EXISTS enums ( id varchar(30), value int );");
            	Datenbankverbindung.runSQL("INSERT INTO enumsStud (id, value) VALUES (\"Studenten\", 1)");
                Datenbankverbindung.runSQL("INSERT INTO enums (id, value) VALUES (\"Kurs\", 1)");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        //System.out.println(Controller.nextidoftable("Kurs"));
    

        
        Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("gui.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("University Management");
        stage.show();
    }
}
