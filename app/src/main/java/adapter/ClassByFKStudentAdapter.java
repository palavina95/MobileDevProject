package adapter;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolmanagement.ModifyStudent;
import com.example.schoolmanagement.R;

import java.util.ArrayList;
import java.util.List;

import database.entities.Class;
import database.entities.Student_Class;
import viewmodel.ClassViewModel;
import viewmodel.Student_ClassViewModel;

public class ClassByFKStudentAdapter extends ArrayAdapter<Class> {

    private int IdStudent,verification;
    private Student_ClassViewModel studentClassViewModel;

    public ClassByFKStudentAdapter(Context context, ArrayList<Class> classes, int IdStudent, Student_ClassViewModel student_classViewModel) {
        super(context, 0, classes);
        this.IdStudent = IdStudent;
        this.studentClassViewModel = student_classViewModel;
    }

    public ClassByFKStudentAdapter(Context context, ArrayList<Class> classes, int IdStudent) {
        super(context, 0, classes);
        this.IdStudent = IdStudent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position

        final Class classe = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.class_by_fk_student_item, parent, false);

        }

        // Lookup view for data population

        TextView nameClass = (TextView) convertView.findViewById(R.id.byFK_name_class);

        TextView roomClass = (TextView) convertView.findViewById(R.id.byFK_room_class);

        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_view_class);

        // Populate the data into the template view using the data object

        nameClass.setText(classe.getName());

        roomClass.setText(String.valueOf(classe.getRoomNumber()));

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student_Class student_class = new Student_Class(IdStudent, classe.getPK_ID_Class());
                if(checkBox.isChecked()) {
                    Toast.makeText(getContext(), "BONJOUR !", Toast.LENGTH_LONG).show();
                    studentClassViewModel.insert(student_class);
                }else{
                    Toast.makeText(getContext(), "BONNE NUIT !", Toast.LENGTH_LONG).show();
                    studentClassViewModel.delete(student_class);
                }
            }
        });

        /*
        Si j'arrive à comprendre un jour comment faire

        studentClassViewModel.verifyExistance(IdStudent,classe.getPK_ID_Class()).observe( this, new Observer<Integer>(){
            @Override
            public void onChanged(@Nullable Integer integer) {
                //If the student has already applied for this class
                if(integer >= 1) {
                    checkBox.setActivated(true);
                }
            }
        });
        */

        // Return the completed view to render on screen + notify dataChanged
        notifyDataSetChanged();

        return convertView;
    }
}
