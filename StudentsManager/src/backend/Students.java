package backend;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Students{
    Scanner sc = new Scanner(System.in);
    String kurse;
    String name;
    String surname;
    //Course kurs;
    JavaSkill capability;
    String hiredCompany;
    String id;

    //ArrayList<ArrayList<Student>> studentenliste = new ArrayList<ArrayList <Student>>();
    ArrayList<ArrayList<Students>> studlist = new ArrayList<ArrayList<Students>>();
    public List<String> kursnamen = new ArrayList<String>();


    public void createnewcourse(String s) {
        s = sc.nextLine();
        Kurse kurs = new Kurse(s);
        System.out.println(kurs.getStudents());
        //c.setKursname(sc.nextLine());
		/*ArrayList<Student> innerlist = new ArrayList<Student>();
		for(int i = 0; i<studlist.size();i++) {
			if(studlist.get(i) == null) {
				studlist.add(innerlist);
				break;
			} else {
				continue;
			}
		}*/
        /*
         * Um einen neuen Kurs zu erstellen, wollen wir wissen, ob an jeglicher Stelle unserer Liste ein Kurs bereits besteht und wenn nicht, dann soll
         * er ebenfalls an der ersten Stelle seiner Liste nichts beinhalten, da dieser immer unseren Kursnamen beinhalten soll.
         * */
        for(int i = 0; i < studlist.size(); i++) {
            //for(int j= 0; j< studlist.get(i).size(); j++) {
            if(studlist.get(i) == null && studlist.get(i).get(0) == null) {
                Students x = new Students();
                x.setName(sc.nextLine());
                studlist.get(i).set(0, x);
            }
            //}
        }


    }

    public void registerStudent(int course) {
        Students s = new Students();
        s.setName(sc.nextLine());
        s.secureStringsettername(s);
        s.setSurname(sc.nextLine());
        s.secureStringsettersurname(s);
        s.setCapability(capability); //soll noch per GUI gelöst werden, ich würde aber sagen, dass das Standardwert bei Beginner sein sollte.
        s.setHiredCompany(sc.nextLine());
        for(int j = 0; j < studlist.get(course).size(); j++) {
            if(studlist.get(course).get(0) == null) {
                System.err.println("Kursname wurde nicht deklariert, bitte Kursnamen deklarieren, bevor sie Studenten einfügen");
                break; //hier soll noch ein return to Main Menu geben.
            } else if(studlist.get(course).get(j) == null && j != 0){
                studlist.get(course).set(j, s);
            }
        }


		/*s.setName(sc.nextLine());
		s.setSurname(sc.nextLine());
		s.setKurs(kurs);
		s.setHiredCompany(sc.nextLine());
		s.setCapability(capability);
		studentenliste.add(s);*/

        //studentenliste.get(0).set(0, s);
    }

    public void secureStringsettername(Students s) {
        char[] ch = s.name.toCharArray();
        StringBuilder strbuild = new StringBuilder();
        for(char c : ch) {
            if(Character.isAlphabetic(c)) {
                strbuild.append(c);
            }
        }
        s.name = strbuild.toString();
    }
    public void secureStringsettersurname(Students s) {
        char[] ch = s.surname.toCharArray();
        StringBuilder strbuild = new StringBuilder();
        for(char c : ch) {
            if(Character.isAlphabetic(c)) {
                strbuild.append(c);
            }
        }
        s.surname = strbuild.toString();
    }


    public JavaSkill getCapability() {
        return capability;
    }

    public void setCapability(JavaSkill capability) {
        this.capability = capability;
    }

    public String getHiredCompany() {
        return hiredCompany;
    }

    public void setHiredCompany(String hiredCompany) {
        this.hiredCompany = hiredCompany;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}

class Course{
    String kursname; // Man sollte auch Kurse verwalten können, dies ist erstmal außer Acht zu lassen.

    public String setKursname(String kursname) {
        this.kursname = kursname;
        return kursname;
    }
}
enum JavaSkill{
    Beginner, Amateur, Mediocre, Experienced, Professional, Maniac;
}
enum CourseRoom{
    B048, B040;
}

