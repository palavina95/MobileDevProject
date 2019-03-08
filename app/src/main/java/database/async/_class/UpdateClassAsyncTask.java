package database.async._class;

import android.os.AsyncTask;

import database.dao.ClassDao;
import database.entities.Class;

public class UpdateClassAsyncTask extends AsyncTask<Class,Void,Void> {

    private ClassDao classDao;

    public UpdateClassAsyncTask(ClassDao classDao){
        this.classDao = classDao;
    }

    @Override
    protected Void doInBackground(Class... classes) {
        classDao.update(classes[0]);
        return null;
    }
}
