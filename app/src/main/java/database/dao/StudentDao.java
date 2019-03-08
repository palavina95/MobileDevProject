package database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import database.entities.Student;

@Dao
public interface StudentDao {

    @Insert
    void insert(Student myStudent);

    @Update
    void update(Student myStudent);

    @Delete
    void delete(Student myStudent);

    @Query("SELECT * FROM student_table ORDER BY Firstname,Lastname")
    LiveData<List<Student>> getAllStudent();

}
