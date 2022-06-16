package backend;

public class Studenten {
	String vorname;
	String nachname;
	String firma;
	String kurs;
	int JSkill;
	
	public Studenten(String vorname, String nachname, String firma, int JSkill, String kurs) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.firma = firma;
		this.JSkill = JSkill;
		this.kurs = kurs;
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
	public int getJSkill() {
		return JSkill;
	}
	public void setJSkill(int jSkill) {
		JSkill = jSkill;
	}
	

}
