package database.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import database.entities.Class;

public class ClassListLiveData extends LiveData<List<Class>> {

    private static final String TAG = "ClassListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();
    private final String valeurRecherche;

    public ClassListLiveData(DatabaseReference ref, String valeurRecherche) {
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

    private List<Class> toClassList(DataSnapshot snapshot) {
        List<Class> classes = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Class entity = childSnapshot.getValue(Class.class);
            entity.setId(childSnapshot.getKey());

            if(entity.getName().toLowerCase().contains(valeurRecherche)) {
                classes.add(entity);
            }
        }
        return classes;
    }

}