package database.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import database.SchoolManagementDatabase;
import database.async._class.DeleteClassAsyncTask;
import database.async._class.InsertClassAsyncTask;
import database.async._class.UpdateClassAsyncTask;
import database.dao.ClassDao;
import database.entities.Class;

public class ClassRepository {

    private ClassDao classDao;
    private LiveData<List<Class>> allClasses;

    public ClassRepository(Application application){
        SchoolManagementDatabase database = SchoolManagementDatabase.getInstance(application);
        classDao = database.classDao();
        allClasses = classDao.getAllClass();
    }

    public void insert(Class myClass)
    {
        new InsertClassAsyncTask(classDao).execute(myClass);
    }

    public void update(Class myClass)
    {
        new UpdateClassAsyncTask(classDao).execute(myClass);
    }

    public void delete(Class myClass)
    {
        new DeleteClassAsyncTask(classDao).execute(myClass);
    }

    public LiveData<List<Class>> getAllClasses() {
        return allClasses;
    }

    public LiveData<List<Class>> getAllClassesByFKStudent(int FKStudent){
        return classDao.getAllClassByFKStudent(FKStudent);
    }

}
