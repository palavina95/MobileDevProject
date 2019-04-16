package adapter;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolmanagement.ModifyStudent;
import com.example.schoolmanagement.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import database.entities.Class;
import database.entities.Student_Class;
import viewmodel.Student_ClassViewModel;

public class ClassByFKStudentAdapter extends ArrayAdapter<Class> {

    private String IdStudent;
    private Student_ClassViewModel studentClassViewModel;
    public ModifyStudent modifyStudent;
    private static final String TAG = "ClassbyFKStudentAdapter";

    public ClassByFKStudentAdapter(Context context, ArrayList<Class> classes, String IdStudent, Student_ClassViewModel student_classViewModel, ModifyStudent modifyStudent) {
        super(context, 0, classes);
        this.IdStudent = IdStudent;
        this.studentClassViewModel = student_classViewModel;
        this.modifyStudent = modifyStudent;
    }

    public ClassByFKStudentAdapter(Context context, ArrayList<Class> classes, String IdStudent) {
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

                Student_Class student_class = new Student_Class(IdStudent, classe.getId());

                /*
                studentClassViewModel.getIdStudent_Class(IdStudent,classe.getId()).observe(modifyStudent, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        student_class.setId(s);
                    }
                });
                */


                Log.d(TAG, "student_classID : "+student_class.getId());

                if(checkBox.isChecked()) {
                    Toast.makeText(getContext(), "INSCRIPTION CONFIRMED !", Toast.LENGTH_LONG).show();
                    studentClassViewModel.insert(student_class);
                }else{
                    Toast.makeText(getContext(), "DELETION CONFIRMED !", Toast.LENGTH_LONG).show();
                    studentClassViewModel.delete(student_class);
                }

            }
        });

        //If Display don't show the checkbox

        if(getContext().toString().contains("DisplayStudent")){
            Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
            checkBox.setButtonDrawable(transparentDrawable);
        }else {

            studentClassViewModel.verifyExistance(IdStudent, classe.getId()).observe(modifyStudent, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer integer) {
                    //If the student has already applied for this class
                    if (integer >= 1) {
                        checkBox.setChecked(true);
                    }
                }
            });

        }

        // Return the completed view to render on screen + notify dataChanged

        notifyDataSetChanged();

        return convertView;
    }
}