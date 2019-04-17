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
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.StudentByFKClassAdapter;
import database.entities.Class;
import database.entities.Student;
import viewmodel.ClassViewModel;
import viewmodel.StudentViewModel;
import viewmodel.Student_ClassViewModel;

public class ModifyClass extends AppCompatActivity {

    //Variable
    private SharedPreferences sharedPreferences;
    private String idClass;

    //Viewmodel
    private StudentViewModel studentViewModel;
    private Student_ClassViewModel studentClassViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        int r=sharedPreferences.getInt("a_r",0);
        int g=sharedPreferences.getInt("a_g",0);
        int b=sharedPreferences.getInt("a_b",0);
        ActionBarClr(r,g,b);

        setContentView(R.layout.activity_modify_class);

        //Get the class we want to display
        Class thisClass = (Class) getIntent().getSerializableExtra("MyClass");

        //Create the text views
        TextView textViewName = findViewById(R.id.text_name_class);
        textViewName.setText(thisClass.getName());
        TextView textViewRoom = findViewById(R.id.text_room_class);
        textViewRoom.setText(String.valueOf(thisClass.getRoomNumber()));
        TextView textViewLocation = findViewById(R.id.location_class);
        textViewLocation.setText(thisClass.getLocation());
        TextView textViewTeacher = findViewById(R.id.text_teacher_class);
        textViewTeacher.setText(thisClass.getTeacherName());
        TextView textViewBeginTime = findViewById(R.id.text_begintime_class);
        textViewBeginTime.setText(thisClass.getBeginningTime());
        TextView textViewEndTime = findViewById(R.id.text_endtime_class);
        textViewEndTime.setText(thisClass.getEndingTime());

        idClass = thisClass.getId();

        ListView listViewClassByFKClass = (ListView)findViewById(R.id.listforModifyClassByFkStudent);

        final ArrayList<Student> arrayOfStudent = new ArrayList<Student>();

        studentClassViewModel = ViewModelProviders.of(this).get(Student_ClassViewModel.class);

        final StudentByFKClassAdapter adapterC = new StudentByFKClassAdapter(ModifyClass.this, arrayOfStudent, idClass, studentClassViewModel, this);

        listViewClassByFKClass.setAdapter(adapterC);

        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);


        //Hold the smartphone in vertical mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void modifyClass(View view) {
        EditText textViewName = findViewById(R.id.text_name_class);
        String sName = textViewName.getText().toString();
        EditText textViewRoom = findViewById(R.id.text_room_class);
        String sRoom = textViewRoom.getText().toString();
        EditText textViewLocation = findViewById(R.id.location_class);
        String sLocation = textViewLocation.getText().toString();
        EditText textViewTeacher = findViewById(R.id.text_teacher_class);
        String sTeacher = textViewTeacher.getText().toString();
        EditText textViewBeginTime = findViewById(R.id.text_begintime_class);
        String sBeginTime = textViewBeginTime.getText().toString();
        EditText textViewEndTime = findViewById(R.id.text_endtime_class);
        String sEndTime = textViewEndTime.getText().toString();

        if(sName.trim().isEmpty() || sRoom.trim().isEmpty() || sLocation.trim().isEmpty() || sTeacher.trim().isEmpty()
                || sBeginTime.trim().isEmpty() || sEndTime.trim().isEmpty()){
            Toast.makeText(ModifyClass.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }else{
            //On crée une classe
            Class thisClass = new Class(sName,Integer.parseInt(sRoom),sLocation,sTeacher,sBeginTime,sEndTime);
            ClassViewModel classViewModel = ViewModelProviders.of(this).get(ClassViewModel.class);
            //On l'insert dans la base de donnée
            thisClass.setId(idClass);    //Pour pouvoir retrouver le bon étudiant
            classViewModel.update(thisClass);
            //On affiche un toast
            Toast.makeText(ModifyClass.this, "Class updated !", Toast.LENGTH_LONG).show();
            //On revient à la page précédente
            Intent intent = new Intent(this, ResultListOfSearchClass.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_settings_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
