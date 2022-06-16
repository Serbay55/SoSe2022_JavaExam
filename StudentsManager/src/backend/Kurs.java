package backend;

public class Kurs {
	String kursname;
	String raum;
	
	
	public Kurs(String kursname, String raum) {
		this.kursname = kursname;
		this.raum = raum;
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
