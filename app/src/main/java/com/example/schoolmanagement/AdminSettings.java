package com.example.schoolmanagement;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;

public class AdminSettings extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Button btnStudent;
    private Button btnClass;

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
        defaults.put("color_Student_Button","#D3D3D3");
        defaults.put("color_Class_Button","#D3D3D3");
        remoteConfig.setDefaults(defaults);

        final Task<Void> fetch = remoteConfig.fetch(0);
        fetch.addOnSuccessListener(this, new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                btnStudent = findViewById(R.id.button_manage_student);
                btnClass = findViewById(R.id.button_manage_class);
                remoteConfig.fetchAndActivate();
                updateColorButtons();
            }
        });

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        int r=sharedPreferences.getInt("a_r",0);
        int g=sharedPreferences.getInt("a_g",0);
        int b=sharedPreferences.getInt("a_b",0);
        ActionBarClr(r,g,b);

        setContentView(R.layout.activity_admin_settings);

        //Bloque on vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void updateColorButtons(){
        String colorStudent = remoteConfig.getString("color_Student_Button");
        String colorClass = remoteConfig.getString("color_Class_Button");

        btnStudent.setBackgroundColor(Color.parseColor(colorStudent));
        btnClass.setBackgroundColor(Color.parseColor(colorClass));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_settings_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void goStudentSettings(View view){
        Intent intent = new Intent(this, SearchStudent.class);
        startActivity(intent);
    }

    public void goClassSettings(View view){
        Intent intent = new Intent(this, SearchClass.class);
        startActivity(intent);
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
