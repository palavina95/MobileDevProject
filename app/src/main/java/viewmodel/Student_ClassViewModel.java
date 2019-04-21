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

    public LiveData<String> getIdStudent_Class(String FKStudent, String FKClass){
        return repositoryC.getIdStudent_Class(FKStudent, FKClass);
    }

    public LiveData<Integer> verifyExistance(String FKStudent, String FKClass) {
        return repositoryC.verifyExistance(FKStudent, FKClass);
    }

    public LiveData<Integer> verifyExistanceStudent(String FKStudent){
        return repositoryC.verifyExistanceStudent(FKStudent);
    }

    public LiveData<Integer> verifyExistanceClass(String FKClass){
        return repositoryC.verifyExistanceClass(FKClass);
    }

    public LiveData<String> getIdStudent_ClassByOneID(String id){
        return repositoryC.getIdStudent_ClassByOneId(id);
    }


}