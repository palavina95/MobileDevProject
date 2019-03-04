package com.example.schoolmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class ResultListOfSearchStudent extends AppCompatActivity {

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

        setContentView(R.layout.activity_result_list_of_search);

        SwipeMenuListView listView = (SwipeMenuListView)findViewById(R.id.listView);

        final ArrayList<String> list = new ArrayList<>();
        list.add("Vivian");
        list.add("Audrey");
        list.add("Vivian");
        list.add("Audrey");
        list.add("Vivian");
        list.add("Audrey");
        list.add("Vivian");
        list.add("Audrey");
        list.add("Vivian");
        list.add("Audrey");
        list.add("Vivian");
        list.add("Audrey");
        list.add("Vivian");
        list.add("Audrey");
        list.add("Vivian");
        list.add("Audrey");
        list.add("Vivian");
        list.add("Audrey");
        list.add("Vivian");
        list.add("Audrey");

        ArrayAdapter adapter = new ArrayAdapter(ResultListOfSearchStudent.this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

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
                        Intent intent = new Intent(ResultListOfSearchStudent.this, ModifyStudent.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(ResultListOfSearchStudent.this, DeleteStudent.class);
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
                Intent intent3 = new Intent(ResultListOfSearchStudent.this, DisplayStudent.class);
                String message = list.get(position);
                intent3.putExtra(EXTRA_MESSAGE,message);
                startActivity(intent3);
            }
        });

    }

    public void ActionBarClr(int r,int g,int b){
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.rgb(r
                        ,g,b)));
    }


}
