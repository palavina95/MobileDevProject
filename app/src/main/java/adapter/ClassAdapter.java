package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.schoolmanagement.R;

import java.util.ArrayList;

import database.entities.Class;

public class ClassAdapter extends ArrayAdapter<Class> {
    public ClassAdapter(Context context, ArrayList<Class> classes) {
        super(context, 0, classes);
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
