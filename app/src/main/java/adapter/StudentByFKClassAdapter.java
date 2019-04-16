package adapter;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolmanagement.ModifyClass;
import com.example.schoolmanagement.R;

import java.util.ArrayList;

import database.entities.Student;
import database.entities.Student_Class;
import viewmodel.Student_ClassViewModel;

public class StudentByFKClassAdapter extends ArrayAdapter<Student> {

    private String IdClass;
    private Student_ClassViewModel studentClassViewModel;
    public ModifyClass modifyClass;

    public StudentByFKClassAdapter(Context context, ArrayList<Student> students, String IdClass, Student_ClassViewModel student_classViewModel, ModifyClass modifyClass) {
        super(context, 0, students);
        this.IdClass = IdClass;
        this.studentClassViewModel = student_classViewModel;
        this.modifyClass = modifyClass;
    }

    public StudentByFKClassAdapter(Context context, ArrayList<Student> students, String IdClass) {
        super(context, 0, students);
        this.IdClass = IdClass;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position

        final Student student = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_by_fkstudent, parent, false);

        }

        // Lookup view for data population

        TextView firstnameStudent = (TextView) convertView.findViewById(R.id.byFK_firstname_student);

        TextView lastnameStudent = (TextView) convertView.findViewById(R.id.byFK_lastname_student);

        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_view_student);

        // Populate the data into the template view using the data object

        firstnameStudent.setText(student.getFirstname());

        lastnameStudent.setText(String.valueOf(student.getLastname()));

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student_Class student_class = new Student_Class(student.getId(), IdClass);



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

        if(getContext().toString().contains("DisplayClass")){
            Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
            checkBox.setButtonDrawable(transparentDrawable);
        }else {
            studentClassViewModel.verifyExistance(student.getId(), IdClass).observe(modifyClass, new Observer<Integer>() {
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
