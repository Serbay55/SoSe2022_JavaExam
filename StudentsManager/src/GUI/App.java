package GUI;

import java.sql.SQLException;

import datenbankpaket.Datenbankverbindung;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	public void start(Stage stage) throws Exception{
        try{
            Datenbankverbindung.runSQL("CREATE TABLE Kurs ( id int primary key, kurs_name varchar(30));");
        } catch (SQLException e){
            e.printStackTrace();
        }


        Parent root = (Parent) FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("University Management");
        stage.show();
    }
}
