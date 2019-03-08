package database.async._class;

import android.os.AsyncTask;

import database.dao.ClassDao;
import database.entities.Class;

public class DeleteClassAsyncTask extends AsyncTask<Class,Void,Void> {

    private ClassDao classDao;

    public DeleteClassAsyncTask(ClassDao classDao){
        this.classDao = classDao;
    }

    @Override
    protected Void doInBackground(Class... classes) {
        classDao.delete(classes[0]);
        return null;
    }
}
