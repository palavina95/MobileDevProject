package database.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import database.entities.Student_Class;
import database.firebase.verifyExistanceLiveData;

public class Student_ClassRepository {

    private LiveData<List<Student_Class>> allStudent_Classes;

    public Student_ClassRepository(Application application){
    }

    public void insert(Student_Class student_class)
    {
        String id = FirebaseDatabase.getInstance()
                .getReference("manyToMany").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("manyToMany")
                .child(id).setValue(student_class);
    }

    public void update(Student_Class student_class)
    {
        FirebaseDatabase.getInstance()
                .getReference("manyToMany")
                .child(student_class.getId())
                .updateChildren(student_class.toMap());
    }

    public void delete(Student_Class student_class)
    {
        FirebaseDatabase.getInstance()
                .getReference("manyToMany")
                .child(student_class.getId())
                .removeValue();
    }

    public LiveData<Integer> verifyExistance(String FKStudent, String FKClass)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("manyToMany");

        return new verifyExistanceLiveData(reference,FKStudent,FKClass);
    }

}