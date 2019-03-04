package com.example.schoolmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_settings_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void createMyStudent(View view) {
        StringBuffer result = new StringBuffer();
        EditText firstname = (EditText) findViewById(R.id.room);
        EditText lastname = (EditText) findViewById(R.id.room);
        EditText birtdate = (EditText) findViewById(R.id.birthdate);
        //EditText picture = (EditText) findViewById(R.id.birthdate);

        result.append("Firstname: " + firstname.getText().toString() + "\n");
        result.append("Lastname: " + lastname.getText().toString() + "\n");
        result.append("Birthdate: " + birtdate.getText().toString() + "\n");

        // TODO Auto-generated method stub
        Toast.makeText(CreateStudent.this, result, Toast.LENGTH_LONG).show();
    }
}
