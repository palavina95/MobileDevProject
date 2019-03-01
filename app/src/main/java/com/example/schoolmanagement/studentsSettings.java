package com.example.schoolmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class studentsSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_settings_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void searchStudentButton(View view){
        Intent intent = new Intent(this, ResultListOfSearch.class);
        startActivity(intent);
    }

    public void addStudentButton(View view){
        Intent intent = new Intent(this, CreateClass.class);
        startActivity(intent);
    }
}
