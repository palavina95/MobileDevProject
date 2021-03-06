package com.example.schoolmanagement;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import adapter.StudentByFKClassAdapter;
import database.entities.Class;
import database.entities.Student;
import viewmodel.StudentViewModel;

public class DisplayClass extends AppCompatActivity {

    //Variables
    private SharedPreferences sharedPreferences;
    private StudentViewModel studentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        int r=sharedPreferences.getInt("a_r",0);
        int g=sharedPreferences.getInt("a_g",0);
        int b=sharedPreferences.getInt("a_b",0);
        ActionBarClr(r,g,b);

        setContentView(R.layout.activity_display_class);

        //Get the class we want to display
        Class thisClass = (Class) getIntent().getSerializableExtra("MyClass");

        //Create the text views
        TextView textViewName = findViewById(R.id.d_name_class);
        textViewName.setText(thisClass.getName());
        TextView textViewRoom = findViewById(R.id.d_room_class);
        textViewRoom.setText(String.valueOf(thisClass.getRoomNumber()));
        TextView textViewLocation = findViewById(R.id.d_location_class);
        textViewLocation.setText(thisClass.getLocation());
        TextView textViewTeacher = findViewById(R.id.d_teacher_class);
        textViewTeacher.setText(thisClass.getTeacherName());
        TextView textViewBeginTime = findViewById(R.id.d_beginTime_class);
        textViewBeginTime.setText(thisClass.getBeginningTime());
        TextView textViewEndTime = findViewById(R.id.d_endTime_class);
        textViewEndTime.setText(thisClass.getEndingTime());

        //get the listView
        ListView listViewClassByFKClass = (ListView)findViewById(R.id.listViewClassByFKClass);



        //Data source
        final ArrayList<Student> arrayOfStudents = new ArrayList<Student>();

        final StudentByFKClassAdapter adapterC = new StudentByFKClassAdapter(DisplayClass.this, arrayOfStudents, thisClass.getPK_ID_Class());

        listViewClassByFKClass.setAdapter(adapterC);

        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        studentViewModel.getAllStudentByFKClass(thisClass.getPK_ID_Class()).observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable List<Student> students) {
                adapterC.addAll(students);
            }
        });

        //Block on vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_settings_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Settings button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Actionbar color
    public void ActionBarClr(int r,int g,int b){
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.rgb(r
                        ,g,b)));
    }
}
