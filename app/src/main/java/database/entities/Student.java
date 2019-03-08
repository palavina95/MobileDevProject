package database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "student_table")
public class Student {

    @PrimaryKey(autoGenerate = true)
    private int PK_ID_Student;

    private String Firstname;

    private String Lastname;

    private String Picture;

    private String Birthdate;

    public Student(String firstname, String lastname, String picture, String birthdate) {
        Firstname = firstname;
        Lastname = lastname;
        Picture = picture;
        Birthdate = birthdate;
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
