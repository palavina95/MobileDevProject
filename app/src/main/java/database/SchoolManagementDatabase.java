package database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import database.dao.Student_ClassDao;
import database.entities.Class;
import database.entities.Student_Class;

@Database(entities = {Student_Class.class}, version = 3,exportSchema = false)
public abstract class SchoolManagementDatabase extends RoomDatabase {

    private static SchoolManagementDatabase instance;

    public abstract Student_ClassDao student_classDao();

    //Synchronised = only one thread can access this instance at a time
    public static synchronized SchoolManagementDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SchoolManagementDatabase.class,"schoolmanagement_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private Student_ClassDao student_classDao;

        private PopulateDbAsyncTask (SchoolManagementDatabase db){
            student_classDao = db.student_classDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
