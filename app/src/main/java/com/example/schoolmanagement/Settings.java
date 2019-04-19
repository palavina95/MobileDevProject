package com.example.schoolmanagement;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity /*implements OnMapReadyCallback*/ {

    //Firebase authentication instance
    private FirebaseAuth mAuth;

    //Final variable
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //Variable
    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    //Variable responsible for the color change of the actionBar
    private SeekBar seekBarRed,seekBarGreen,seekBarBlue;
    private RadioGroup idForRadioGroup;
    private TextView textR,textG,textB;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean bar=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();

        //Get sharedPreferences so we can modify it
        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        //Get the values of red,green and blue for the color change of the action bar
        textR=(TextView)findViewById(R.id.textR);
        textG=(TextView)findViewById(R.id.textG);
        textB=(TextView)findViewById(R.id.textB);

        //Get each bar and its value
        seekBarRed=(SeekBar)findViewById(R.id.seekBarRed);
        seekBarGreen=(SeekBar)findViewById(R.id.seekBarGreen);
        seekBarBlue=(SeekBar)findViewById(R.id.seekBarBlue);
        getClr();

        //Listener on the red bar
        seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textR.setText("= "+String.valueOf(progress));
                putClr();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //Listener on the green bar
        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textG.setText("= "+String.valueOf(progress));
                putClr();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //Listener on the blue bar
        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textB.setText("= "+String.valueOf(progress));
                putClr();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //Hold the smartphone in vertical mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.info_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //To add whenever you want to access the settings in the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_infos:
                Intent intent = new Intent(this, InfosApp.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Method responsible of the change of color of the acton bar
    public void ActionBarClr(){
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.rgb(seekBarRed.getProgress()
                        ,seekBarGreen.getProgress(),seekBarBlue.getProgress())));
    }

    public void StatusBarClr(){
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //    window.setStatusBarColor(Color.BLUE);

            window.setStatusBarColor(Color.rgb(seekBarRed.getProgress()
                    ,seekBarGreen.getProgress(),seekBarBlue.getProgress()));
        }
    }


    public void initStatusBarClr(){

        int r=sharedPreferences.getInt("s_r",0);
        int g=sharedPreferences.getInt("s_g",0);
        int b=sharedPreferences.getInt("s_b",0);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //    window.setStatusBarColor(Color.BLUE);
            window.setStatusBarColor(Color.rgb(r,g,b));
        }


    }

    public void getClr(){
        if(bar) {
            int r=sharedPreferences.getInt("a_r",0);
            int g=sharedPreferences.getInt("a_g",0);
            int b=sharedPreferences.getInt("a_b",0);

            seekBarRed.setProgress(r);
            seekBarGreen.setProgress(g);
            seekBarBlue.setProgress(b);

            textR.setText("= "+String.valueOf(r));
            textG.setText("= "+String.valueOf(g));
            textB.setText("= "+String.valueOf(b));
            ActionBarClr();
        }else{
            int r=sharedPreferences.getInt("s_r",0);
            int g=sharedPreferences.getInt("s_g",0);
            int b=sharedPreferences.getInt("s_b",0);

            seekBarRed.setProgress(r);
            seekBarGreen.setProgress(g);
            seekBarBlue.setProgress(b);

            textR.setText("= "+String.valueOf(r));
            textG.setText("= "+String.valueOf(g));
            textB.setText("= "+String.valueOf(b));
            StatusBarClr();
        }
    }

    public void putClr(){
        if (bar){
            editor.putInt("a_r",seekBarRed.getProgress());
            editor.putInt("a_g",seekBarGreen.getProgress());
            editor.putInt("a_b",seekBarBlue.getProgress());
            editor.commit();
            ActionBarClr();
        }else{
            editor.putInt("s_r",seekBarRed.getProgress());
            editor.putInt("s_g",seekBarGreen.getProgress());
            editor.putInt("s_b",seekBarBlue.getProgress());
            editor.commit();
            StatusBarClr();
        }
    }

    public void changePassword(View view){
        //EditText oldMdpEt = (EditText) findViewById(R.id.oldmdp);
        EditText newMdpEt = (EditText) findViewById(R.id.newmdp);

        //String oldMdp = oldMdpEt.getText().toString();
        String newMdp = newMdpEt.getText().toString();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUser.updatePassword(newMdp);
        Toast.makeText(Settings.this, "Password changed successfully", Toast.LENGTH_LONG).show();

    }

    public void logout(View view){
        mAuth.signOut();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}