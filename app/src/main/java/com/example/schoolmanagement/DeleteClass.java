package com.example.schoolmanagement;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import adapter.ClassAdapter;
import database.entities.Class;
import database.entities.Student_Class;
import viewmodel.ClassViewModel;
import viewmodel.Student_ClassViewModel;

public class DeleteClass extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Student_ClassViewModel student_classViewModel;
    private Student_Class student_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        int r=sharedPreferences.getInt("a_r",0);
        int g=sharedPreferences.getInt("a_g",0);
        int b=sharedPreferences.getInt("a_b",0);
        ActionBarClr(r,g,b);

        setContentView(R.layout.activity_delete_class);

        DeleteClass maClasse = this;

        SwipeButton swipeButton = (SwipeButton)findViewById(R.id.swipe_btn);
        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {

                //Create class
                Class thisClass = (Class) getIntent().getSerializableExtra("MyClass");
                ClassViewModel classViewModel = ViewModelProviders.of(DeleteClass.this).get(ClassViewModel.class);

                //delete manyToMany
                student_classViewModel = ViewModelProviders.of(maClasse).get(Student_ClassViewModel.class);

                student_class = new Student_Class("", thisClass.getId());

                student_classViewModel.verifyExistanceClass(thisClass.getId()).observe(maClasse, new Observer<Integer>() {

                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        if(integer == 1){
                            student_classViewModel.getIdStudent_ClassByOneID(thisClass.getId()).observe(maClasse, new Observer<String>() {
                                boolean change = true;
                                @Override
                                public void onChanged(@Nullable String s) {
                                    if(!(s.equals(null)) && change){
                                        Log.e("DELETECLASS", "Valeur de s : "+s);
                                        student_class.setId(s);
                                        student_classViewModel.delete(student_class);
                                    }
                                    change = false;
                                }
                            });
                        }
                    }
                });


                //Display a toast
                Toast.makeText(DeleteClass.this, "DELETION CONFIRMED !", Toast.LENGTH_SHORT).show();
                //Insert in database
                classViewModel.delete(thisClass);
                Intent intent = new Intent(DeleteClass.this, ResultListOfSearchClass.class);
                startActivity(intent);

            }
        });

        //Block on vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    //ACtionbar color
    public void ActionBarClr(int r,int g,int b){
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.rgb(r
                        ,g,b)));
    }
}
