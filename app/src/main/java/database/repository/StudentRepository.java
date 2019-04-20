package database.repository;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import database.entities.Student;
import database.firebase.ClassbyFKStudentListLiveData;
import database.firebase.StudentListLiveData;
import database.firebase.StudentbyFKClassLiveData;

public class StudentRepository {

    private LiveData<List<Student>> allStudents;

    public StudentRepository(Application application){
    }

    public void insert(Student student)
    {
        String id = FirebaseDatabase.getInstance()
                        .getReference("students").push().getKey();
        FirebaseDatabase.getInstance()
                        .getReference("students")
                        .child(id).setValue(student);
    }

    public void update(Student student)
    {
        FirebaseDatabase.getInstance()
                        .getReference("students")
                        .child(student.getId())
                        .updateChildren(student.toMap());
    }

    public void delete(Student student)
    {
        FirebaseDatabase.getInstance()
                .getReference("students")
                .child(student.getId())
                .removeValue();
    }

    public LiveData<List<Student>> getAllStudents(String valeurRecherche) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                                        .getReference("students");

        return new StudentListLiveData(reference,valeurRecherche);
    }

    public LiveData<List<Student>> getAllStudentsSimple(){

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("students");

        return new StudentListLiveData(reference, "");
    }

    public LiveData<List<Student>> getAllStudentsByFKClass (String FKClass, LifecycleOwner owner) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("students");

        DatabaseReference reference2 = FirebaseDatabase.getInstance()
                .getReference("manyToMany");

        return new StudentbyFKClassLiveData(reference,reference2,FKClass,owner);
    }

}