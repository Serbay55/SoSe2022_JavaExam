package GUI;

import java.sql.SQLException;

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
        try{
            Datenbankverbindung.runSQL("CREATE TABLE IF NOT EXISTS Kurs ( id int primary key, kurs_name varchar(30));");
        } catch (SQLException e){
            e.printStackTrace();
        }


        Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("gui.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("University Management");
        stage.show();
    }
}
