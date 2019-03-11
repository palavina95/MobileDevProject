package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.schoolmanagement.R;

import java.util.ArrayList;
import java.util.List;

import database.entities.Student;

public class StudentsAdapter extends ArrayAdapter<Student> {
    public StudentsAdapter(Context context, ArrayList<Student> students){
        super(context,0,students);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        // Get the data item for this position

        Student student = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_list_item, parent, false);

        }

        // Lookup view for data population

        TextView firstNameStudent = (TextView) convertView.findViewById(R.id.text_view_firstname_student);

        TextView lastNameStudent = (TextView) convertView.findViewById(R.id.text_view_lastname_student);

        // Populate the data into the template view using the data object

        firstNameStudent.setText(student.getFirstname());

        lastNameStudent.setText(student.getLastname());

        // Return the completed view to render on screen + notify dataChanged
        notifyDataSetChanged();

        return convertView;
    }


}
