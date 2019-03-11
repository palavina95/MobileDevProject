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
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import database.entities.Student;
import viewmodel.StudentViewModel;

public class DeleteStudent extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        int r=sharedPreferences.getInt("a_r",0);
        int g=sharedPreferences.getInt("a_g",0);
        int b=sharedPreferences.getInt("a_b",0);
        ActionBarClr(r,g,b);

        setContentView(R.layout.activity_delete_class);

        SwipeButton swipeButton = (SwipeButton)findViewById(R.id.swipe_btn);
        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                //On crée un étudiant
                Student thisStudent = (Student) getIntent().getSerializableExtra("MyStudent");
                StudentViewModel studentViewModel = ViewModelProviders.of(DeleteStudent.this).get(StudentViewModel.class);
                //Afficher un toast
                Toast.makeText(DeleteStudent.this, "DELETION CONFIRMED !", Toast.LENGTH_SHORT).show();
                //On l'insert dans la base de donnée
                studentViewModel.delete(thisStudent);
                Intent intent = new Intent(DeleteStudent.this, ResultListOfSearchStudent.class);
                startActivity(intent);
            }
        });

        //Bloque on vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public void ActionBarClr(int r,int g,int b){
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.rgb(r
                        ,g,b)));
    }
}
