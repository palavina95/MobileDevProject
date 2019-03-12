package database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import database.entities.Student_Class;

@Dao
public interface Student_ClassDao {

    @Insert
    void insert(Student_Class myStudent_class);

    @Update
    void update(Student_Class myStudent_Class);

    @Delete
    void delete(Student_Class myStudent_Class);

    @Query("SELECT * FROM `student/class_table`")
    LiveData<List<Student_Class>> getAllStudent_Class();

    @Query("SELECT COUNT(*) FROM `student/class_table` WHERE FK_Student = :FKStudent AND FK_Class = :FKClass")
    LiveData<Integer> verifyExistance(int FKStudent, int FKClass);

}
