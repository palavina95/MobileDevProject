package database.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import database.entities.Student_Class;

public class verifyExistanceClassLiveData extends LiveData<Integer> {

    private final String TAG = "verifyExistanceClassLiveData";
    private DatabaseReference reference;
    private String FKClass;
    MyValueEventListener listener = new MyValueEventListener();

    public verifyExistanceClassLiveData(DatabaseReference reference, String FKClass){
        this.reference = reference;
        this.FKClass = FKClass;
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

            if(entity.getFK_Class().equals(FKClass)) {
                valueToReturn = 1;
            }
        }
        return valueToReturn;
    }

}
