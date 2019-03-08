package database.async._class;

import android.os.AsyncTask;

import database.dao.ClassDao;
import database.entities.Class;

public class InsertClassAsyncTask extends AsyncTask<Class,Void,Void> {

    private ClassDao classDao;

    public InsertClassAsyncTask(ClassDao classDao){
        this.classDao = classDao;
    }

    @Override
    protected Void doInBackground(Class... classes) {
        classDao.insert(classes[0]);
        return null;
    }
}
