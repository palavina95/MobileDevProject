package com.example.schoolmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bloque on vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void logUser(View view){

        //Récupération des données utilisateurs
        EditText loginEt = (EditText) findViewById(R.id.username);
        EditText mdpEt = (EditText) findViewById(R.id.mdp);
        //Transformation en String
        String login = loginEt.getText().toString();
        String mdp = mdpEt.getText().toString();

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);

        if(login.equals(sharedPreferences.getString("login", "")) && mdp.equals(sharedPreferences.getString("mdp","")))
        {
                Intent intent = new Intent(this, AdminSettings.class);
                startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this, "Wrong login or password", Toast.LENGTH_LONG).show();
        }
    }

}
