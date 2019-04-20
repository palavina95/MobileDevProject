package database.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import database.entities.Student;

public class StudentListLiveData extends LiveData<List<Student>> {

    private static final String TAG = "StudentListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();
    private final String valeurRecherche;

    public StudentListLiveData(DatabaseReference ref, String valeurRecherche) {
        reference = ref;
        this.valeurRecherche = valeurRecherche;
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

    private List<Student> toClassList(DataSnapshot snapshot) {
        List<Student> students = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Student entity = childSnapshot.getValue(Student.class);
            entity.setID(childSnapshot.getKey());

            if(entity.getFirstname().toLowerCase().contains(valeurRecherche) || entity.getLastname().toLowerCase().contains(valeurRecherche)) {
                students.add(entity);
            }
        }

        return students;
    }

}