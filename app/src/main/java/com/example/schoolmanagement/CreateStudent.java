package com.example.schoolmanagement;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;

import database.entities.Student;
import viewmodel.StudentViewModel;

public class CreateStudent extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private EditText textFirstname;
    private EditText textLastname;

    //Remote control
    private FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //Set developper mode for Remote control
        remoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(true)
            .build());

        //Default value for remote control
        HashMap<String, Object> defaults = new HashMap<>();
        defaults.put("max_lastname_student",10);
        defaults.put("max_firstname_student",10);
        remoteConfig.setDefaults(defaults);


        final Task<Void> fetch = remoteConfig.fetch(0);
        fetch.addOnSuccessListener(this, new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                textFirstname = (EditText)findViewById(R.id.enter_firstname);
                textLastname = (EditText)findViewById(R.id.enter_lastname);
                remoteConfig.fetchAndActivate();
                updateLengthFirstandLastName();
            }
        });

        Button createButton = (Button) findViewById(R.id.createStudent);

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        int r=sharedPreferences.getInt("a_r",0);
        int g=sharedPreferences.getInt("a_g",0);
        int b=sharedPreferences.getInt("a_b",0);
        ActionBarClr(r,g,b);

        setContentView(R.layout.activity_create_student);

        //Block on vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void updateLengthFirstandLastName(){
        int maxFirstname = (int) remoteConfig.getLong("max_firstname_student");
        int maxLastname = (int) remoteConfig.getLong("max_lastname_student");

        textFirstname.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxFirstname)});
        textLastname.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLastname)});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_settings_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void createMyStudent(View view) {
        String firstname = textFirstname.getText().toString();
        String lastname = textLastname.getText().toString();
        EditText textBirthdate = (EditText)findViewById(R.id.enter_birthdate);
        String birthdate = textBirthdate.getText().toString();

        if(firstname.trim().isEmpty() || lastname.trim().isEmpty() || birthdate.trim().isEmpty()){
            Toast.makeText(CreateStudent.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }else{
            //Create student
            Student thisStudent = new Student(firstname,lastname,birthdate);
            StudentViewModel studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
            //Insert database
            studentViewModel.insert(thisStudent);
            //Display a toast
            Toast.makeText(CreateStudent.this, "Student created !", Toast.LENGTH_LONG).show();
            //Go back to the previous page
            Intent intent = new Intent(this, SearchStudent.class);
            startActivity(intent);
        }
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
