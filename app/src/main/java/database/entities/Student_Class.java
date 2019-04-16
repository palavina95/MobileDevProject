package database.entities;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Student_Class {

    private String FK_Student;

    private String FK_Class;

    @Exclude
    private String id;

    public Student_Class(){

    }

    public Student_Class(String FK_Student, String FK_Class) {
        this.FK_Student = FK_Student;
        this.FK_Class = FK_Class;
    }

    public String getFK_Student() {
        return FK_Student;
    }

    public String getFK_Class() {
        return FK_Class;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setFK_Student(String FK_Student) {
        this.FK_Student = FK_Student;
    }

    public void setFK_Class(String FK_Class) {
        this.FK_Class = FK_Class;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fK_Student", FK_Student);
        result.put("fK_Class", FK_Class);
        ;return result;
    }
}