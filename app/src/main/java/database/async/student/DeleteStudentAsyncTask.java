package database.async.student;

import android.os.AsyncTask;

import database.dao.StudentDao;
import database.entities.Student;

public class DeleteStudentAsyncTask extends AsyncTask<Student,Void,Void> {
    private StudentDao studentDao;

    public DeleteStudentAsyncTask(StudentDao studentDao)
    {
        this.studentDao = studentDao;
    }

    @Override
    protected Void doInBackground(Student... students) {
        studentDao.delete(students[0]);
        return null;
    }
}