package backend;

public class Studenten {
	String vorname;
	String nachname;
	String firma;
	String kurs;
	String raumkurs;
	public String getRaumkurs() {
		return raumkurs;
	}

	public void setRaumkurs(String raumkurs) {
		this.raumkurs = raumkurs;
	}
	int identificator;
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
	public int getJSkill() {
		return JSkill;
	}
	public void setJSkill(int jSkill) {
		JSkill = jSkill;
	}
	

}
