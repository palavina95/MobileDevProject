package database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import database.entities.Class;
import database.entities.Student_Class;

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

    @Query("SELECT * FROM class_table WHERE PK_ID_Class IN (SELECT FK_Class FROM `student/class_table` WHERE FK_Student = :FKStudent) ORDER BY RoomNumber")
    LiveData<List<Class>> getAllClassByFKStudent(int FKStudent);

}
