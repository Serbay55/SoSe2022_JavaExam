package backend;

import java.sql.ResultSet;

public class Kursraum {
	int room_id;
	String room;
	
	public Kursraum(int room_id, String room) {
		this.room_id = room_id;
		this.room = room;
	}
	public String toString() {
		return room;
	}
}
