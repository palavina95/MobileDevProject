package adapter;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.schoolmanagement.DeleteClass;
import com.example.schoolmanagement.R;

import java.util.ArrayList;

import database.entities.Class;
import viewmodel.Student_ClassViewModel;

public class ClassAdapter extends ArrayAdapter<Class> {

    private Student_ClassViewModel student_classViewModel;
    private DeleteClass deleteClass;
    private String idClass;

    public ClassAdapter(Context context, ArrayList<Class> classes) {
        super(context, 0, classes);
    }

    public ClassAdapter(Context context, Student_ClassViewModel scvm, String idClass) {
        super(context, 0);
        this.student_classViewModel = scvm;
        this.idClass = idClass;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position

        Class classe = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.class_list_item, parent, false);

        }

        // Lookup view for data population

        TextView nameClass = (TextView) convertView.findViewById(R.id.text_view_name_class);

        TextView roomClass = (TextView) convertView.findViewById(R.id.text_view_room_class);

        // Populate the data into the template view using the data object

        nameClass.setText(classe.getName());

        roomClass.setText(String.valueOf(classe.getRoomNumber()));



        // Return the completed view to render on screen + notify dataChanged
        notifyDataSetChanged();

        return convertView;
    }
}
