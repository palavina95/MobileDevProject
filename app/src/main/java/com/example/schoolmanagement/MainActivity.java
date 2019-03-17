package com.example.schoolmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

        /*EditText loginEt = (EditText) findViewById(R.id.username);
        EditText mdpEt = (EditText) findViewById(R.id.mdp);

        String login = loginEt.getText().toString();
        String mdp = mdpEt.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", login);
        editor.putString("mdp", mdp);*/

        Intent intent = new Intent(this, AdminSettings.class);
        /*EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);*/
        startActivity(intent);
    }

    public void searchClassButton(View view){

        EditText editText = (EditText) findViewById(R.id.search_text_class);

        //Sauvegarde de la recherche dans sharedpreferences
        String valeurRecherche = editText.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("valeurRechercheClass",valeurRecherche);
        editor.commit();

        Intent intent = new Intent(this, ResultListOfSearchClass.class);
        startActivity(intent);
    }
}
