package viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import database.dao.Student_ClassDao;
import database.entities.Class;
import database.entities.Student_Class;
import database.repository.ClassRepository;
import database.repository.Student_ClassRepository;

public class ClassViewModel extends AndroidViewModel {

    private ClassRepository repositoryC;
    private Student_ClassRepository repositorySC;
    private LiveData<List<Class>> allClass;

    public ClassViewModel(@NonNull Application application) {
        super(application);
        repositoryC = new ClassRepository(application);
        allClass = repositoryC.getAllClasses();


        repositorySC = new Student_ClassRepository(application);
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

    public LiveData<List<Class>> getClassSearch(String valeurRecherche){
        return repositoryC.getClassSearch(valeurRecherche);
    }

    public LiveData<List<Class>> getAllClassByFKStudent(int FKStudent){

        /*Juste pour maintenant (une seule fois pour commencer
        Student_Class first = new Student_Class(1,1);
        repositorySC.insert(first); */
        /***************/
        return repositoryC.getAllClassesByFKStudent(FKStudent);
    }
}
