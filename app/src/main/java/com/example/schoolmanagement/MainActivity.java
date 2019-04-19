package com.example.schoolmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Firebase authentication instance
    private FirebaseAuth mAuth;

    //Final variable
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SITE_KEY = "6LeogJkUAAAAAG8ZY-oJlsRpFbsxMKL-pv_irl8s";
    private static final String SITE_SECRET_KEY = "6LeogJkUAAAAAB6l9QIlc_nEqZabqATuaGP5rk5n";

    //Variable
    private String userResponse;
    private boolean successCaptcha = false;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //Hold the smartphone in vertical mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null) {
            //Display personalised message
            Log.d(TAG, "Hello, " + currentUser.getEmail());
            Intent intent = new Intent(this, AdminSettings.class);
            startActivity(intent);
        }
    }

    public void logUser(View view){

        //Get the user information
        EditText loginEt = (EditText) findViewById(R.id.username);
        EditText mdpEt = (EditText) findViewById(R.id.mdp);
        //Transform to string
        String login = loginEt.getText().toString();
        String mdp = mdpEt.getText().toString();

        Log.e(TAG, "login = "+login+" | mdp = "+mdp);
        if(!login.equals("") && !mdp.equals("")) {
            mAuth.signInWithEmailAndPassword(login, mdp)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(MainActivity.this, "Please enter your email and password !",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
