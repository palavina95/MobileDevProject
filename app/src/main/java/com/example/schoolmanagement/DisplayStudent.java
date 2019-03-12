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
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


import adapter.ClassByFKStudentAdapter;
import database.entities.Class;
import database.entities.Student;
import viewmodel.ClassViewModel;
import viewmodel.StudentViewModel;

public class DisplayStudent extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    //ViewModel
    private ClassViewModel classViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        int r=sharedPreferences.getInt("a_r",0);
        int g=sharedPreferences.getInt("a_g",0);
        int b=sharedPreferences.getInt("a_b",0);
        ActionBarClr(r,g,b);

        setContentView(R.layout.activity_display_student);

        //Get the student we want to display
        Student thisStudent = (Student) getIntent().getSerializableExtra("MyStudent");

        //Create the text views
        TextView textViewFirstname = findViewById(R.id.d_firstname);
        textViewFirstname.setText(thisStudent.getFirstname() + " id: "+thisStudent.getPK_ID_Student());
        TextView textViewLastname = findViewById(R.id.d_lastname);
        textViewLastname.setText(thisStudent.getLastname());
        TextView textViewBirthdate = findViewById(R.id.d_birthdate);
        textViewBirthdate.setText(thisStudent.getBirthdate());

        //Get the listView
        ListView listViewStudentByFKStudent = (ListView)findViewById(R.id.listViewStudentByFKStudent);

        // Construct the data source
        final ArrayList<Class> arrayOfClasses = new ArrayList<Class>();

        final ClassByFKStudentAdapter adapter = new ClassByFKStudentAdapter(DisplayStudent.this, arrayOfClasses);

        listViewStudentByFKStudent.setAdapter(adapter);

        classViewModel = ViewModelProviders.of(this).get(ClassViewModel.class);
        classViewModel.getAllClassByFKStudent(thisStudent.getPK_ID_Student()).observe(this, new Observer<List<Class>>() {
            @Override
            public void onChanged(@Nullable List<Class> classes) {
                adapter.addAll(classes);
            }
        });

        //Bloque on vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_settings_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //A ajouter partout ou l'on veut que le bouton settings ouvre la page settings.
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

    public void ActionBarClr(int r,int g,int b){
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.rgb(r
                        ,g,b)));
    }
}
