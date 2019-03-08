package viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import database.entities.Student;
import database.repository.StudentRepository;

public class StudentViewModel extends AndroidViewModel {

    private StudentRepository repository;
    private LiveData<List<Student>> allStudents;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        repository = new StudentRepository(application);
        allStudents = repository.getAllStudents();
    }

    public void insert(Student student){
        repository.insert(student);
    }

    public void update(Student student){
        repository.update(student);
    }

    public void delete(Student student){
        repository.delete(student);
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }
}
