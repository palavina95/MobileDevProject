package database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Time;

@Entity(tableName = "class_table")
public class Class {

    @PrimaryKey(autoGenerate = true)
    private int PK_ID_Class;

    private String Name;

    private int RoomNumber;

    private String Location;

    private String TeacherName;

    private String BeginningTime;

    private String EndingTime;

    public Class(String Name, int RoomNumber, String Location, String TeacherName, String BeginningTime, String EndingTime) {
        this.Name = Name;
        this.RoomNumber = RoomNumber;
        this.Location = Location;
        this.TeacherName = TeacherName;
        this.BeginningTime = BeginningTime;
        this.EndingTime = EndingTime;
    }

    public void setPK_ID_Class(int PK_ID_Class) {
        this.PK_ID_Class = PK_ID_Class;
    }

    public int getPK_ID_Class() {
        return PK_ID_Class;
    }

    public String getName() {
        return Name;
    }

    public int getRoomNumber() {
        return RoomNumber;
    }

    public String getLocation() {
        return Location;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public String getBeginningTime() {
        return BeginningTime;
    }

    public String getEndingTime() {
        return EndingTime;
    }
}
