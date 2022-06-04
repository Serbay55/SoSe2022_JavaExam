package backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kurse {
    Scanner sc = new Scanner(System.in);
    public Kurse(String name) {
        this.name = name;
    }

    private String name; // setter & getter
    private List<Students> students = new ArrayList<Students>(); // getter

    public void addStudent(Students s) {
        students.add(s);
    }
    public List<Students> getStudents() {
        return students;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void removeStudent(Students s) {
        String checker;
        checker = sc.nextLine();
        for(int i = 0; i < students.size(); i++) {
            if(students.get(i).id.toLowerCase() == checker.toLowerCase()) {
                students.remove(i);
                break;
            } else {
                System.err.println("Der angegebene Identifikator für den gesuchten Studenten ist nicht existent bitte schauen Sie in die Studentenliste");
            }
        }
    }
    public void registerStudent(int course){
        Students s = new Students();
        s.setName(sc.nextLine());
        s.secureStringsettername(s);
        s.setSurname(sc.nextLine());
        s.secureStringsettersurname(s);
        s.setHiredCompany(sc.nextLine());
        int identCounter = 0;
        for(int i = 0; i < students.size(); i++) {
            if(students.get(i).id != null) {
                identCounter++;
            } else if(students.get(i).id == null) {
                String newID;
                newID = name +":"+ identCounter;
                students.get(i).id = newID;
            }
        }

    }

}
