package backend;

import datenbankpaket.Datenbankverbindung;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class Studenten {
	int identificator;
	String vorname;
	String nachname;
	String firma;
	String kurs;
	String raumkurs;
	int JSkill;



	
	public Studenten(String vorname, String nachname, String firma, int JSkill, String kurs) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.firma = firma;
		this.JSkill = JSkill;
		this.kurs = kurs;
	}
	
	public Studenten(String vorname, String nachname, String firma, int JSkill, String kurs, int identificator) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.firma = firma;
		this.JSkill = JSkill;
		this.kurs = kurs;
		this.identificator = identificator;
	}
	
	public Studenten(String vorname, String nachname, String firma, int JSkill, String kurs, int identificator, String raumkurs) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.firma = firma;
		this.JSkill = JSkill;
		this.kurs = kurs;
		this.identificator = identificator;
		this.raumkurs = raumkurs;
	}


	/**
	 * Entfernt Sonderzeichen und Ziffern
	 * @param ch char Array, aus dem die Sonderzeichen/Ziffern entfernt werden sollen
	 * @return String ohne Sonderzeichen/Ziffern
	 */
	public String checkName(char[] ch){

		StringBuilder strbuildregstud = new StringBuilder();
		for(char c : ch) {
			if(Character.isAlphabetic(c) || Character.isSpace(c)) {
				strbuildregstud.append(c);
			}
		}
		return strbuildregstud.toString();
	}


	/**
	 * Entfernt Sonderzeichen
	 * @param ch char Array, aus dem die Sonderzeichen entfernt werden sollen
	 * @return String ohne Sonderzeichen
	 */
	public String checkCompany(char[] ch){

		StringBuilder strbuildregstud = new StringBuilder();
		for(char c : ch) {
			if(Character.isAlphabetic(c) || Character.isDigit(c) || Character.isSpace(c)) {
				strbuildregstud.append(c);
			}
		}
		return strbuildregstud.toString();
	}


	/**
	 *
	 * @param ch
	 * @return
	 */
	public String checkID(char[] ch){

		StringBuilder strbuildregstud = new StringBuilder();
		for(char c : ch) {
			if(Character.isDigit(c)) {
				strbuildregstud.append(c);
			}
		}
		return strbuildregstud.toString();
	}


	/**
	 * Entfernt falsche Zeichen aus Vorname, Nachname und Firma.
	 * Prüft außerdem ob Länge von Vorname, Nachname oder Firma 0 ist
	 * @return String: Name des Attributes mit der Länge 0. Falls kein Problem vorliegt wird "keine" zurückgegeben
	 */
	public String checkData(){

		this.vorname = this.checkName(this.getVorname().toCharArray());
		this.nachname = this.checkName(this.getNachname().toCharArray());
		this.firma = this.checkCompany(this.getFirma().toCharArray());

		if(this.vorname.length() == 0){
			return "Vorname";
		}
		else if (this.nachname.length() == 0){
			return "Nachname";
		}
		else if (this.firma.length() == 0) {
			return "Firma";
		}
		else if (this.kurs == null) {
			return "Kurs";
		}
		else {
			return "keine";
		}

	}


	/**
	 * Speichert den Studenten in der Datenbank ab
	 */
	public void anlegenDB(){
		try {
			Datenbankverbindung.runSQL("INSERT INTO Studenten (vorname, nachname, Java_Skill, firma, kurs) VALUES (\""+this.getVorname()+"\", \""+this.getNachname()+"\", \""+this.getJSkill()+"\", \""+this.getFirma()+"\", \""+this.getKurs()+"\");");
		} catch (SQLException z) {
			throw new RuntimeException(z);
		}
	}
	
	public int getIdentificator() {
		return identificator;
	}
	public void setIdentificator(int identificator) {
		this.identificator = identificator;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getKurs() {
		return kurs;
	}
	public void setKurs(String kurs) {
		this.kurs = kurs;
	}
	public String getRaumkurs() {
		return raumkurs;
	}
	public void setRaumkurs(String raumkurs) {
		this.raumkurs = raumkurs;
	}
	public int getJSkill() {
		return JSkill;
	}
	public void setJSkill(int jSkill) {
		JSkill = jSkill;
	}
	

}
