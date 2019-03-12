package viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import database.entities.Class;
import database.entities.Student_Class;
import database.repository.ClassRepository;
import database.repository.Student_ClassRepository;

public class Student_ClassViewModel extends AndroidViewModel {

    private Student_ClassRepository repositoryC;
    private LiveData<List<Class>> allClassByFKStudent;

    public Student_ClassViewModel(@NonNull Application application) {
        super(application);
        repositoryC = new Student_ClassRepository(application);
    }

    public void insert(Student_Class studentClass) {
        repositoryC.insert(studentClass);
    }

    public void update(Student_Class studentClass) {
        repositoryC.update(studentClass);
    }

    public void delete(Student_Class studentClass) {
        repositoryC.delete(studentClass);
    }

    public LiveData<Integer> verifyExistance(int FKStudent, int FKClass) {
        return repositoryC.verifyExistance(FKStudent, FKClass);
    }

}
