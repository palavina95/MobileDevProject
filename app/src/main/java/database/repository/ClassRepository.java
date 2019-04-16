package database.repository;
import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;
import database.SchoolManagementDatabase;
import database.entities.Class;
import database.firebase.ClassListLiveData;

public class ClassRepository {

    private LiveData<List<Class>> allClasses;

    public ClassRepository(Application application){
        SchoolManagementDatabase database = SchoolManagementDatabase.getInstance(application);
    }

    public void insert(Class myClass)
    {
        String id = FirebaseDatabase.getInstance()
                .getReference("classes").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("classes")
                .child(id).setValue(myClass);
    }

    public void update(Class myClass)
    {
        FirebaseDatabase.getInstance()
                .getReference("classes")
                .child(myClass.getId())
                .updateChildren(myClass.toMap());
    }

    public void delete(Class myClass)
    {
        FirebaseDatabase.getInstance()
                .getReference("classes")
                .child(myClass.getId())
                .removeValue();
    }

    public LiveData<List<Class>> getAllClasses() {
        return allClasses;
    }

    public LiveData<List<Class>> getClassSearch(String valeurRecherche){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("classes");

        return new ClassListLiveData(reference,valeurRecherche);
    }

    public LiveData<List<Class>> getAllClassesByFKStudent(int FKStudent){
        return null;
    }

}