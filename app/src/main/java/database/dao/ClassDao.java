package database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import database.entities.Class;

@Dao
public interface ClassDao {

    @Insert
    void insert(Class myClass);

    @Update
    void update(Class myClass);

    @Delete
    void delete(Class myClass);

    @Query("SELECT * FROM class_table ORDER BY RoomNumber")
    LiveData<List<Class>> getAllClass();

}
