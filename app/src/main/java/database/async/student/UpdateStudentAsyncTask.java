package database.async.student;

import android.os.AsyncTask;

import database.dao.StudentDao;
import database.entities.Student;

public class UpdateStudentAsyncTask extends AsyncTask<Student,Void,Void> {
    private StudentDao studentDao;

    public UpdateStudentAsyncTask(StudentDao studentDao)
    {
        this.studentDao = studentDao;
    }

    @Override
    protected Void doInBackground(Student... students) {
        studentDao.update(students[0]);
        return null;
    }
}