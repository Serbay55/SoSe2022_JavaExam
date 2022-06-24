package backend;

import datenbankpaket.Datenbankverbindung;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Kurs {
	String kursname;
	String raum;
	
	
	public Kurs(String kursname, String raum) {
		this.kursname = kursname;
		this.raum = raum;
	}


	public String checkKursname(char[] ch){

		StringBuilder strbuildregstud = new StringBuilder();
		for(char c : ch) {
			if(Character.isAlphabetic(c) || Character.isDigit(c)) {
				strbuildregstud.append(c);
			}
		}
		return strbuildregstud.toString();
	}


	public boolean kursNameBelegt() throws SQLException{
		ResultSet rs = Datenbankverbindung.runSQLquery("SELECT kurs_name FROM Kurs WHERE kurs_name = \""+this.getKursname()+"\"");
		List<String> belegtekursnamen = new ArrayList<String>();
		while(rs.next()) {
			belegtekursnamen.add(rs.getString("kurs_name"));
		}

		return belegtekursnamen.size() != 0;
	}


	public boolean kursRaumBelegt() throws SQLException {

		if (this.getRaum().equals("online")){
			return false;
		}
		else {
			ResultSet rs = Datenbankverbindung.runSQLquery("SELECT kurs_raum FROM Kurs WHERE kurs_raum = \"" + this.getRaum() + "\"");
			List<String> belegteKursraeume = new ArrayList<String>();
			while (rs.next()) {
				belegteKursraeume.add(rs.getString("kurs_raum"));
			}
			return belegteKursraeume.size() != 0;
		}
	}




	public String checkData(){

		this.kursname = this.checkKursname(this.getKursname().toCharArray());


		if(this.kursname.length() == 0){
			return "Kursname";
		}
		else if (this.raum == null) {
			return "Kursraum";
		}
		else {
			return "keine";
		}

	}



	public void anlegenDB(){
		try {
			Datenbankverbindung.runSQL("INSERT INTO Kurs (kurs_name, kurs_raum) VALUES (\""+this.getKursname()+"\", \""+this.getRaum()+"\")");
		} catch (SQLException x) {
			throw new RuntimeException(x);
		}
	}






	
	public String getKursname() {
		return kursname;
	}
	public void setKursname(String kursname) {
		this.kursname = kursname;
	}
	public String getRaum() {
		return raum;
	}
	public void setRaum(String raum) {
		this.raum = raum;
	}
}
