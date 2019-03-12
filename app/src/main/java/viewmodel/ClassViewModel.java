package viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import database.entities.Class;
import database.repository.ClassRepository;

public class ClassViewModel extends AndroidViewModel {

    private ClassRepository repositoryC;
    private LiveData<List<Class>> allClass;

    public ClassViewModel(@NonNull Application application) {
        super(application);
        repositoryC = new ClassRepository(application);
        allClass = repositoryC.getAllClasses();
    }

    public void insert(Class classe){
        repositoryC.insert(classe);
    }

    public void update(Class classe){
        repositoryC.update(classe);
    }

    public void delete(Class classe){
        repositoryC.delete(classe);
    }

    public LiveData<List<Class>> getAllClass() {
        return allClass;
    }
}
