package database.entities;

import com.google.firebase.database.Exclude;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Class implements Serializable {

    @Exclude
    private int PK_ID_Class;

    private String Name;

    private int RoomNumber;

    private String Location;

    private String TeacherName;

    private String BeginningTime;

    private String EndingTime;

    @Exclude
    private String id;

    public Class(){

    }

    public Class(String Name, int RoomNumber, String Location, String TeacherName, String BeginningTime, String EndingTime) {
        this.Name = Name;
        this.RoomNumber = RoomNumber;
        this.Location = Location;
        this.TeacherName = TeacherName;
        this.BeginningTime = BeginningTime;
        this.EndingTime = EndingTime;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPK_ID_Class(int PK_ID_Class) {
        this.PK_ID_Class = PK_ID_Class;
    }

    @Exclude
    public int getPK_ID_Class() {
        return PK_ID_Class;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        RoomNumber = roomNumber;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getBeginningTime() {
        return BeginningTime;
    }

    public void setBeginningTime(String beginningTime) {
        BeginningTime = beginningTime;
    }

    public String getEndingTime() {
        return EndingTime;
    }

    public void setEndingTime(String endingTime) {
        EndingTime = endingTime;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", Name);
        result.put("roomNumber", RoomNumber);
        result.put("location", Location);
        result.put("teacher", TeacherName);
        result.put("beginningTime", BeginningTime);
        result.put("endingTime", EndingTime);
        return result;
    }
}