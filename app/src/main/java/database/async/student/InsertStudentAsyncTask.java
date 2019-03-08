package database.async.student;

import android.os.AsyncTask;

import database.dao.StudentDao;
import database.entities.Student;

public class InsertStudentAsyncTask extends AsyncTask<Student,Void,Void> {
    private StudentDao studentDao;

    public InsertStudentAsyncTask(StudentDao studentDao){
        this.studentDao = studentDao;
    }

    @Override
    protected Void doInBackground(Student... students) {
        studentDao.insert(students[0]);
        return null;
    }
}
