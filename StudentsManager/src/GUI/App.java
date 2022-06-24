package GUI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import backend.Controller;
import backend.Kursraum;
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

				/* Hier nur einige Beispielräume, da wir nicht genau wissen welche/ wie viele Räume die DHBW hat*/
				Kursraum[] kursraeume = new Kursraum[]{
						new Kursraum("online"),
						new Kursraum("Audimax"),
						new Kursraum("Carl-Benz-Hörsaal"),
						new Kursraum("B040"),
						new Kursraum("B041"),
						new Kursraum("B042"),
						new Kursraum("B043"),
						new Kursraum("B044"),
						new Kursraum("B045"),
						new Kursraum("B046"),
						new Kursraum("B047"),
						new Kursraum("B048")
				};

				for(int i=0;i<kursraeume.length;i++) {
					kursraeume[i].anlegenDB();
				}

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        
        Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("gui.fxml"));
        backend.Controller.rootNew = root;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("University Management");
        stage.show();
    }
}
