package com.example.schoolmanagement;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.entities.Class;
import viewmodel.ClassViewModel;

public class CreateClass extends AppCompatActivity {

    public static final String CHANNEL_ID_1 = "New class...";
    private NotificationManagerCompat notificationManagerCompat;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button createButton = (Button) findViewById(R.id.createClass);

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        int r=sharedPreferences.getInt("a_r",0);
        int g=sharedPreferences.getInt("a_g",0);
        int b=sharedPreferences.getInt("a_b",0);
        ActionBarClr(r,g,b);

        setContentView(R.layout.activity_create_class);

        //Bloque on vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_settings_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void createMyClass(View view) {


        EditText name = (EditText) findViewById(R.id.name_class);
        String sName = name.getText().toString();
        EditText room = (EditText) findViewById(R.id.room_class);
        String sRoom = room.getText().toString();
        EditText location = (EditText) findViewById(R.id.location_class);
        String sLocation = location.getText().toString();
        EditText teacher = (EditText) findViewById(R.id.teacher_class);
        String sTeacher = teacher.getText().toString();
        EditText beginTime = (EditText) findViewById(R.id.beginTime_class);
        String sBeginTime = beginTime.getText().toString();
        EditText endTime = (EditText) findViewById(R.id.endTime_class);
        String sEndTime = endTime.getText().toString();
        //On crée une classe
        Class thisClass = new Class(sName,Integer.parseInt(sRoom),sLocation,sTeacher,sBeginTime,sEndTime);

        //Notification intent onclick creation
        Intent clickIntent = new Intent(this, DisplayStudent.class);
        clickIntent.putExtra("MyClass", thisClass);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, clickIntent, 0);


        //Notification creation
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_new_releases_black_24dp)
                .setContentTitle(getString(R.string.channelName))
                .setContentText(getString(R.string.channelDescription))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.channelDescription)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .addAction(R.drawable.ic_touch_app_black_24dp, "Click to see it", pendingIntent );

        createNotificationChannel();

        //DATA
        if(sName.trim().isEmpty() || sRoom.trim().isEmpty() || sLocation.trim().isEmpty() || sTeacher.trim().isEmpty()
                || sBeginTime.trim().isEmpty() || sEndTime.trim().isEmpty()){
            Toast.makeText(CreateClass.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }else{

            ClassViewModel classViewModel = ViewModelProviders.of(this).get(ClassViewModel.class);
            //On l'insert dans la base de donnée
            classViewModel.insert(thisClass);
            //On affiche un toast
            Toast.makeText(CreateClass.this, "Class created !", Toast.LENGTH_LONG).show();
            //On revient à la page précédente
            Intent intent = new Intent(this, SearchClass.class);
            startActivity(intent);
        }

        //Create the channel
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channelName);
            String description = getString(R.string.channelDescription);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_1, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
