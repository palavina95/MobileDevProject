package database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import database.entities.Class;
import database.entities.Student;

@Dao
public interface StudentDao {

    @Insert
    void insert(Student myStudent);

    @Update
    void update(Student myStudent);

    @Delete
    void delete(Student myStudent);

    @Query("SELECT * FROM student_table WHERE Firstname LIKE :valeurRecherche OR Lastname LIKE :valeurRecherche ORDER BY Firstname,Lastname")
    LiveData<List<Student>> getAllStudent(String valeurRecherche);

    @Query("SELECT * FROM student_table ORDER BY Lastname")
    LiveData<List<Student>> getAllStudentSimple();

    @Query("SELECT * FROM student_table WHERE PK_ID_Student IN (SELECT FK_Student FROM `student/class_table` WHERE FK_Class = :FKClass) ORDER BY Lastname")
    LiveData<List<Student>> getAllStudentByFKClass(int FKClass);

}
