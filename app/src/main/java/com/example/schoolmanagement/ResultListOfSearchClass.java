package com.example.schoolmanagement;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import database.entities.Class;
import adapter.ClassAdapter;
import viewmodel.ClassViewModel;

public class ResultListOfSearchClass extends AppCompatActivity {

    private ClassViewModel classViewModel;

    private static final String TAG = "ResultListOfSearchActivity";
    public final static String EXTRA_MESSAGE = "com.example.schoolmanagement.MESSAGE";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("key_clr", Context.MODE_PRIVATE);
        int r=sharedPreferences.getInt("a_r",0);
        int g=sharedPreferences.getInt("a_g",0);
        int b=sharedPreferences.getInt("a_b",0);
        ActionBarClr(r,g,b);

        setContentView(R.layout.activity_result_list_of_search_class);

        SwipeMenuListView listView = (SwipeMenuListView)findViewById(R.id.listViewClass);

        // Construct the data source
        final ArrayList<Class> arrayOfClass = new ArrayList<Class>();

        final ClassAdapter adapter = new ClassAdapter(ResultListOfSearchClass.this, arrayOfClass);

        listView.setAdapter(adapter);

        classViewModel = ViewModelProviders.of(this).get(ClassViewModel.class);
        classViewModel.getAllClass().observe(this, new Observer<List<Class>>() {
            @Override
            public void onChanged(@Nullable List<Class> classes) {
                adapter.addAll(classes);
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(250);
                // set item title
                openItem.setTitle("Modify");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(250);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Intent intent = new Intent(ResultListOfSearchClass.this, ModifyClass.class);
                        Class thisModifyClass = arrayOfClass.get(position);
                        intent.putExtra("MyClass", thisModifyClass);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(ResultListOfSearchClass.this, DeleteClass.class);
                        Class thisDeleteClass = arrayOfClass.get(position);
                        intent2.putExtra("MyClass", thisDeleteClass);
                        startActivity(intent2);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent3 = new Intent(ResultListOfSearchClass.this, DisplayClass.class);
                Class thisSelectedClass = arrayOfClass.get(position);
                intent3.putExtra("MyClass", thisSelectedClass);
                startActivity(intent3);
            }
        });

        //Bloque on vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public void ActionBarClr(int r,int g,int b){
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.rgb(r
                        ,g,b)));
    }


}
