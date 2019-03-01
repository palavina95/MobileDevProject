package com.example.schoolmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Time;

public class CreateClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button createButton = (Button) findViewById(R.id.createClass);
        setContentView(R.layout.activity_create_class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_settings_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void createMyClass(View view) {
        StringBuffer result = new StringBuffer();
        EditText name = (EditText) findViewById(R.id.name);
        EditText room = (EditText) findViewById(R.id.room);
        EditText location = (EditText) findViewById(R.id.location);
        EditText teacher = (EditText) findViewById(R.id.teacher);
        EditText beginTime = (EditText) findViewById(R.id.beginTime);
        EditText endTime = (EditText) findViewById(R.id.endTime);

        result.append("Name: " + name.getText().toString() + "\n");
        result.append("Room number: " + room.getText().toString() + "\n");
        result.append("Location: " + location.getText().toString() + "\n");
        result.append("Teacher: " + teacher.getText().toString() + "\n");
        result.append("Beginning Time: " + beginTime.getText().toString() + "\n");
        result.append("Ending Time: " + endTime.getText().toString() + "\n");

        // TODO Auto-generated method stub
        Toast.makeText(CreateClass.this, result, Toast.LENGTH_LONG).show();
    }


}
