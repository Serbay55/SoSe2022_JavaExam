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

	/**
	 * Entfernt Sonderzeichen
	 * @param ch char Array, aus dem die Sonderzeichen entfernt werden sollen
	 * @return String ohne Sonderzeichen
	 */
	public String checkKursname(char[] ch){

		StringBuilder strbuildregstud = new StringBuilder();
		for(char c : ch) {
			if(Character.isAlphabetic(c) || Character.isDigit(c)) {
				strbuildregstud.append(c);
			}
		}
		return strbuildregstud.toString();
	}


	/**
	 * Prüft ob der Kursname bereits existiert.
	 * @return false, falls Kursname noch nicht existiert und true, falls Kursname bereits existiert
	 * @throws SQLException
	 */
	public boolean kursNameBelegt() throws SQLException{
		ResultSet rs = Datenbankverbindung.runSQLquery("SELECT kurs_name FROM Kurs WHERE kurs_name = \""+this.getKursname()+"\"");
		List<String> belegtekursnamen = new ArrayList<String>();
		while(rs.next()) {
			belegtekursnamen.add(rs.getString("kurs_name"));
		}

		return belegtekursnamen.size() != 0;
	}


	/**
	 * Prüft, ob der Kursraum schon von einem anderen Kurs belegt ist
	 * @return false, falls der Kursraum noch nicht belegt ist und true, falls der Kursraum schon belegt ist
	 * @throws SQLException
	 */
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


	/**
	 * Entfernt falsche Zeichen aus Kursname und Kursraum
	 * Prüft außerdem ob Länge von Kursname oder Kursraum 0 ist
	 * @return String: Name des Attributes mit der Länge 0. Falls kein Problem vorliegt wird "keine" zurückgegeben
	 */
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


	/**
	 * Speichert den Kurs in der Datenbank ab
	 */
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
