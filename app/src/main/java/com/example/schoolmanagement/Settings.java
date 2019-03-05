package com.example.schoolmanagement;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Settings extends AppCompatActivity /*implements OnMapReadyCallback*/ {

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //vars
    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    /*Code pour changer couleur actionBar dynamic */
    SeekBar seekBarRed,seekBarGreen,seekBarBlue;
    RadioGroup idForRadioGroup;
    TextView textR,textG,textB;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean bar=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getLocationPermission();


        //Code pour cliquer sur le numéro de téléphone et le composer directement
        final TextView phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String temp = "tel:"+phoneNumber.getText();
                intent.setData(Uri.parse(temp));

                startActivity(intent);
            }
        });

        //Code pour cliquer sur le mail et envoyer un mail
        final TextView mail = (TextView) findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",(String) mail.getText(),null));

                startActivity(intent);
            }
        });

        /*Code pour changer couleur actionBar dynamic */
        idForRadioGroup=(RadioGroup)findViewById(R.id.idForRadioGroup);
        RadioButton rButton = (RadioButton) idForRadioGroup.getChildAt(0);
        rButton.setChecked(true);


        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        textR=(TextView)findViewById(R.id.textR);
        textG=(TextView)findViewById(R.id.textG);
        textB=(TextView)findViewById(R.id.textB);

        seekBarRed=(SeekBar)findViewById(R.id.seekBarRed);
        seekBarGreen=(SeekBar)findViewById(R.id.seekBarGreen);
        seekBarBlue=(SeekBar)findViewById(R.id.seekBarBlue);
        getClr();


        idForRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio0:
                        // do operations specific to this selection
                        bar=true;
                        getClr();
                        break;
                }
            }
        });


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

        //Bloque on vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.info_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //A ajouter partout ou l'on veut que le bouton settings ouvre la page settings.
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



    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            //On pose la camera sur l'HES de Sierre
                            moveCamera(new LatLng(46.292854, 7.536262), DEFAULT_ZOOM,"FUCKING HES-SO BABY");

                        } else {
                            Log.d(TAG, "onComplete: current location is NULL");
                            Toast.makeText(Settings.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions().position(latLng).title(title);

        mMap.addMarker(options);
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Toast.makeText(Settings.this, "Map is ready", Toast.LENGTH_SHORT).show();
                mMap = googleMap;

                if (mLocationPermissionGranted) {
                    getDeviceLocation();

                    if (ActivityCompat.checkSelfPermission(Settings.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Settings.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }
            }
        });
    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                initMap();
            }else {
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0 ){
                    for(int i=0; i< grantResults.length;i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    //initialize our map
                    initMap();

                }
            }
        }
    }

    /*Méthode pour changement dynamic couleur ActionBar*/
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


}

