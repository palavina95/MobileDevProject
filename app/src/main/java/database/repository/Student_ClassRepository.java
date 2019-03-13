package database.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import database.SchoolManagementDatabase;
import database.async.student_class.DeleteStudent_ClassAsyncTask;
import database.async.student_class.InsertStudent_ClassAsyncTask;
import database.async.student_class.UpdateStudent_ClassAsyncTask;
import database.dao.Student_ClassDao;
import database.entities.Student_Class;

public class Student_ClassRepository {

    private Student_ClassDao student_classDao;
    private LiveData<List<Student_Class>> allStudent_Classes;

    public Student_ClassRepository(Application application){
        SchoolManagementDatabase database = SchoolManagementDatabase.getInstance(application);
        student_classDao = database.student_classDao();
    }

    public void insert(Student_Class student_class)
    {
        new InsertStudent_ClassAsyncTask(student_classDao).execute(student_class);
    }

    public void update(Student_Class student_class)
    {
        new UpdateStudent_ClassAsyncTask(student_classDao).execute(student_class);
    }

    public void delete(Student_Class student_class)
    {
        new DeleteStudent_ClassAsyncTask(student_classDao).execute(student_class);
    }

    public LiveData<Integer> verifyExistance(int FKStudent, int FKClass)
    {
        return student_classDao.verifyExistance(FKStudent, FKClass);
    }

}
