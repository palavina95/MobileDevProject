package database.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import database.SchoolManagementDatabase;
import database.async.student.DeleteStudentAsyncTask;
import database.async.student.InsertStudentAsyncTask;
import database.async.student.UpdateStudentAsyncTask;
import database.dao.StudentDao;
import database.entities.Class;
import database.entities.Student;

public class StudentRepository {

    private StudentDao studentDao;
    private LiveData<List<Student>> allStudents;

    public StudentRepository(Application application){
        SchoolManagementDatabase database = SchoolManagementDatabase.getInstance(application);
        studentDao = database.studentDao();
        allStudents = studentDao.getAllStudentSimple();
    }

    public void insert(Student student)
    {
        new InsertStudentAsyncTask(studentDao).execute(student);
    }

    public void update(Student student)
    {
        new UpdateStudentAsyncTask(studentDao).execute(student);
    }

    public void delete(Student student)
    {
        new DeleteStudentAsyncTask(studentDao).execute(student);
    }

    public LiveData<List<Student>> getAllStudents(String valeurRecherche) {
        return studentDao.getAllStudent(valeurRecherche);
    }

    public LiveData<List<Student>> getAllStudentsSimple(){
        return allStudents;
    }

    public LiveData<List<Student>> getAllStudentsByFKClass (int FKClass) {
        return studentDao.getAllStudentByFKClass(FKClass);
    }

}
