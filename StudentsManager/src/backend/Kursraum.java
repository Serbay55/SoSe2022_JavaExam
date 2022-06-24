package backend;

import datenbankpaket.Datenbankverbindung;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Kursraum {
	String room;
	
	public Kursraum(String room) {
		this.room = room;
	}

	/**
	 * Legt den Raum in der Datenvb
	 * @throws SQLException
	 */
	public void anlegenDB() throws SQLException {
		Datenbankverbindung.runSQL("INSERT INTO Kursraeume (raum) VALUES (\""+this.getRoom()+"\");");

	}

	public String toString() {
		return room;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
}
