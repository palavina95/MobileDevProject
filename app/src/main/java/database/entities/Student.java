package database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Date;

@Entity(tableName = "student_table")
public class Student implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int PK_ID_Student;

    private String Firstname;

    private String Lastname;

    private String Picture;

    private String Birthdate;

    public Student(String Firstname, String Lastname, String Picture, String Birthdate) {
        this.Firstname = Firstname;
        this.Lastname = Lastname;
        this.Picture = Picture;
        this.Birthdate = Birthdate;
    }

    public void setPK_ID_Student(int PK_ID_Student) {
        this.PK_ID_Student = PK_ID_Student;
    }

    public int getPK_ID_Student() {
        return PK_ID_Student;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public String getPicture() {
        return Picture;
    }

    public String getBirthdate() {
        return Birthdate;
    }
}
