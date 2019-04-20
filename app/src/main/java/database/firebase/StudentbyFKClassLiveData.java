package database.firebase;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import database.entities.Student;

public class StudentbyFKClassLiveData extends LiveData<List<Student>> {

    private static final String TAG = "StudentbtFKClassLiveData";
    private final MyValueEventListener listener = new MyValueEventListener();
    private final DatabaseReference reference, reference2;
    private final String FK_Class;
    private final LifecycleOwner owner;

    public StudentbyFKClassLiveData(DatabaseReference ref, DatabaseReference ref2, String FK_Class, LifecycleOwner owner) {
        this.reference = ref;
        this.reference2 = ref2;
        this.FK_Class = FK_Class;
        this.owner = owner;
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
            setValue(toStudentList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Student> toStudentList(DataSnapshot snapshot) {
        final List<Student> students = new ArrayList<>();

        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            final Student entity = childSnapshot.getValue(Student.class);
            entity.setID(childSnapshot.getKey());

            LiveData<Integer> response = new verifyExistanceLiveData(reference2,FK_Class,entity.getId());

            response.observe(owner, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer integer) {
                    if(integer == 1) {
                        entity.setPK_ID_Student(1);
                    }
                }
            });
        }
        return students;
    }
}
