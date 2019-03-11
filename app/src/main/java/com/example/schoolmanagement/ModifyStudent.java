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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import database.entities.Student;
import viewmodel.StudentViewModel;

public class ModifyStudent extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private int IdStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        int r=sharedPreferences.getInt("a_r",0);
        int g=sharedPreferences.getInt("a_g",0);
        int b=sharedPreferences.getInt("a_b",0);
        ActionBarClr(r,g,b);

        setContentView(R.layout.activity_modify_student);

        //Get the student we want to display
        Student thisStudent = (Student) getIntent().getSerializableExtra("MyStudent");

        //Create the text views
        EditText editFirstName = findViewById(R.id.text_firtname_student);
        editFirstName.setText(thisStudent.getFirstname());
        EditText editLastname = findViewById(R.id.text_lastname_student);
        editLastname.setText(thisStudent.getLastname());
        EditText editBirthdate = findViewById(R.id.text_birthdate_student);
        editBirthdate.setText(thisStudent.getBirthdate());
        //On retrouve aussi l'ID de cet étudiant
        IdStudent = thisStudent.getPK_ID_Student();
        //Bloque on vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void modifyStudent(View view) {
        EditText textFirstname = (EditText)findViewById(R.id.text_firtname_student);
        String firstname = textFirstname.getText().toString();
        EditText textLastname = (EditText)findViewById(R.id.text_lastname_student);
        String lastname = textLastname.getText().toString();
        EditText textBirthdate = (EditText)findViewById(R.id.text_birthdate_student);
        String birthdate = textBirthdate.getText().toString();

        if(firstname.trim().isEmpty() || lastname.trim().isEmpty() || birthdate.trim().isEmpty()){
            Toast.makeText(ModifyStudent.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }else{
            //On crée un étudiant
            Student thisStudent = new Student(firstname,lastname,"visage1.jpg",birthdate);
            StudentViewModel studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
            //On l'insert dans la base de donnée
            thisStudent.setPK_ID_Student(IdStudent);    //Pour pouvoir retrouver le bon étudiant
            studentViewModel.update(thisStudent);
            //On affiche un toast
            Toast.makeText(ModifyStudent.this, "Student updated !", Toast.LENGTH_LONG).show();
            //On revient à la page précédente
            Intent intent = new Intent(this, ResultListOfSearchStudent.class);
            startActivity(intent);
        }
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
