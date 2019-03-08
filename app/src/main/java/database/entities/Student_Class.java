package database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "student/class_table")
public class Student_Class {

    @PrimaryKey()
    private int FK_Student;

    @PrimaryKey()
    private int FK_Class;

    public Student_Class(int FK_Student, int FK_Class) {
        this.FK_Student = FK_Student;
        this.FK_Class = FK_Class;
    }

    public int getFK_Student() {
        return FK_Student;
    }

    public int getFK_Class() {
        return FK_Class;
    }
}
