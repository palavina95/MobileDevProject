package database.async.student_class;

import android.os.AsyncTask;

import database.dao.Student_ClassDao;
import database.entities.Student_Class;


public class InsertStudent_ClassAsyncTask extends AsyncTask<Student_Class,Void,Void> {

    private Student_ClassDao studentClassDao;

    public InsertStudent_ClassAsyncTask(Student_ClassDao studentClassDao)
    {
        this.studentClassDao = studentClassDao;
    }

    @Override
    protected Void doInBackground(Student_Class... student_Classes) {
        studentClassDao.insert(student_Classes[0]);
        return null;
    }
}