package database.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import database.SchoolManagementDatabase;
import database.entities.Class;
import database.entities.Student;
import database.firebase.StudentListLiveData;

public class StudentRepository {

    private LiveData<List<Student>> allStudents;

    public StudentRepository(Application application){
        SchoolManagementDatabase database = SchoolManagementDatabase.getInstance(application);
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
        return allStudents;
    }

    public LiveData<List<Student>> getAllStudentsByFKClass (int FKClass) {
        return null;
    }

}