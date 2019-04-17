package database.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import database.entities.Class;
import database.entities.Student_Class;

public class verifyExistanceLiveData extends LiveData<Integer> {

    private static final String TAG = "Student_ClassListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();
    private final String FK_Student, FK_Class;

    public verifyExistanceLiveData(DatabaseReference ref, String FK_Student, String FK_Class) {
        reference = ref;
        this.FK_Student = FK_Student;
        this.FK_Class = FK_Class;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toClassList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private int toClassList(DataSnapshot snapshot) {
        int valueToReturn = 0;
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Student_Class entity = childSnapshot.getValue(Student_Class.class);
            entity.setId(childSnapshot.getKey());

            Log.e(TAG, "La dedans verify");

            //if(entity.getFK_Student().equals(FK_Student) && entity.getFK_Class().equals(FK_Class)) {
                valueToReturn = 1;
                Log.e(TAG, "Ici verify");
            //}
        }
        return valueToReturn;
    }

}