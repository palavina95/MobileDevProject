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

public class ClassbyFKStudentListLiveData extends LiveData<List<Class>> {

    private static final String TAG = "ClassbyFKStudentListLiveData";

    private final DatabaseReference reference,reference2;
    private final MyValueEventListener listener = new MyValueEventListener();
    private final String FK_Student;

    public ClassbyFKStudentListLiveData(DatabaseReference ref, DatabaseReference ref2,String FK_Student) {
        reference = ref;
        reference2 = ref2;
        this.FK_Student = FK_Student;
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

    private List<Class> toClassList(DataSnapshot snapshot) {
        List<Class> classes = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Class entity = childSnapshot.getValue(Class.class);
            entity.setId(childSnapshot.getKey());

            Log.e(TAG, "La dedans before Existance");

            LiveData<Integer> response = new verifyExistanceLiveData(reference2,FK_Student,entity.getId());

            Log.e(TAG, "reponse = "+ response.getValue());
            Log.e(TAG, "FK_Student = "+ FK_Student);
            Log.e(TAG, "FK_Class = "+ entity.getId());

            if(response.getValue() == 1) {
                classes.add(entity);
                Log.e(TAG, "Ici");
            }
        }
        return classes;
    }

}