package database.entities;

import android.arch.persistence.room.Entity;

@Entity(tableName = "student/class_table", primaryKeys = {"FK_Student","FK_Class"})
public class Student_Class {

    private int FK_Student;

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