package database.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import database.entities.Student_Class;

public class getIdStudent_ClassbyOneIdLiveData extends LiveData<String> {

    private static final String TAG = "Student_ClassbyOneidListLiveData";
    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();
    private final String id;

    public getIdStudent_ClassbyOneIdLiveData(DatabaseReference ref, String id) {
        reference = ref;
        this.id = id;
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

    private String toClassList(DataSnapshot snapshot) {
        String valueToReturn = "";
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Student_Class entity = childSnapshot.getValue(Student_Class.class);
            entity.setId(childSnapshot.getKey());

            //Log.e(TAG, "La dedans IdStudentClass");

            if(entity.getFK_Student().equals(id) || entity.getFK_Class().equals(id)) {
                valueToReturn = entity.getId();
                //Log.e(TAG, "Ici getIdStudentCLass");
            }
        }
        return valueToReturn;
    }


}
