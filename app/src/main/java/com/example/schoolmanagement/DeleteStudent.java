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
import database.entities.Student;
import database.entities.Student_Class;
import viewmodel.StudentViewModel;
import viewmodel.Student_ClassViewModel;

public class DeleteStudent extends AppCompatActivity {

    private Student_ClassViewModel student_classViewModel;
    private Student_Class student_class;
    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        int r=sharedPreferences.getInt("a_r",0);
        int g=sharedPreferences.getInt("a_g",0);
        int b=sharedPreferences.getInt("a_b",0);
        ActionBarClr(r,g,b);

        setContentView(R.layout.activity_delete_class);

        DeleteStudent monStudent = this;

        SwipeButton swipeButton = (SwipeButton)findViewById(R.id.swipe_btn);
        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                //Create student
                Student thisStudent = (Student) getIntent().getSerializableExtra("MyStudent");
                StudentViewModel studentViewModel = ViewModelProviders.of(DeleteStudent.this).get(StudentViewModel.class);

                //delete manyToMany
                student_classViewModel = ViewModelProviders.of(monStudent).get(Student_ClassViewModel.class);

                student_class = new Student_Class("", thisStudent.getId());

                student_classViewModel.verifyExistanceStudent(thisStudent.getId()).observe(monStudent, new Observer<Integer>() {

                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        if(integer == 1){
                            student_classViewModel.getIdStudent_ClassByOneID(thisStudent.getId()).observe(monStudent, new Observer<String>() {
                                boolean change = true;
                                @Override
                                public void onChanged(@Nullable String s) {
                                    if(!(s.equals(null)) && change){
                                        Log.e("DELETESTUDENT", "Valeur de s : "+s);
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
                Toast.makeText(DeleteStudent.this, "DELETION CONFIRMED !", Toast.LENGTH_SHORT).show();
                //Insert it in the database
                studentViewModel.delete(thisStudent);
                Intent intent = new Intent(DeleteStudent.this, ResultListOfSearchStudent.class);
                startActivity(intent);
            }
        });

        //Block on vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    //Actionbar color
    public void ActionBarClr(int r,int g,int b){
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.rgb(r
                        ,g,b)));
    }
}
