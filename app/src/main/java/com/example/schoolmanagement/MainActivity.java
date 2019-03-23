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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

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

        //Hold the smartphone in vertical mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void captchaClick(View view){
        SafetyNet.getClient(this).verifyWithRecaptcha(SITE_KEY)
                .addOnSuccessListener(this,
                    new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                        userResponse = response.getTokenResult();
                        if(!userResponse.isEmpty()){
                            sendRequest();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(e instanceof ApiException){
                            ApiException apiException = (ApiException) e;
                            int statusCode = apiException.getStatusCode();
                            Log.d(TAG,"Error : "+ CommonStatusCodes.getStatusCodeString(statusCode));
                        }else{
                            Log.d(TAG,"Error : "+e.getMessage());
                        }
                    }
                });

    }

    public void sendRequest(){

        String url = "https://www.google.com/recaptcha/api/siteverify";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getString("success").equals("true")) {
                                successCaptcha = true;
                                Button captchaButton = (Button) findViewById(R.id.captchaButton);
                                captchaButton.setBackgroundColor(Color.GREEN);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Captcha failed", Toast.LENGTH_LONG).show();
                    }
                }) {

            protected Map<String, String> getParams() throws AuthFailureError{
                 Map<String, String> params = new HashMap<>();
                 params.put("secret", SITE_SECRET_KEY);
                 params.put("response", userResponse);
                 return params;
            }
        };
        AppController.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void logUser(View view){

        //Get the user information
        EditText loginEt = (EditText) findViewById(R.id.username);
        EditText mdpEt = (EditText) findViewById(R.id.mdp);
        //Transform to string
        String login = loginEt.getText().toString();
        String mdp = mdpEt.getText().toString();

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);

        if(login.equals(sharedPreferences.getString("login", "")) && mdp.equals(sharedPreferences.getString("mdp",""))
            && successCaptcha)
        {
                Intent intent = new Intent(this, AdminSettings.class);
                startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this, "Wrong login or password", Toast.LENGTH_LONG).show();
        }
    }

}
