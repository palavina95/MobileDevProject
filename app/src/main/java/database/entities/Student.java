package database.entities;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Student implements Serializable {

    @Exclude
    private int PK_ID_Student;

    private String Firstname;

    private String Lastname;

    private String Birthdate;

    @Exclude
    private String id;

    public Student(){

    }


    public Student(String Firstname, String Lastname, String Birthdate) {
        this.Firstname = Firstname;
        this.Lastname = Lastname;
        this.Birthdate = Birthdate;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setID(String id){this.id = id;}

    public void setPK_ID_Student(int PK_ID_Student) {
        this.PK_ID_Student = PK_ID_Student;
    }

    @Exclude
    public int getPK_ID_Student() {
        return PK_ID_Student;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstname", Firstname);
        result.put("lastname", Lastname);
        result.put("birthdate", Birthdate)
        ;return result;
    }

}