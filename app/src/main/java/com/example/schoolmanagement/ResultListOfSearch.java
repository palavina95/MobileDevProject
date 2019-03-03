package com.example.schoolmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultListOfSearch extends AppCompatActivity {

    String[] classArray = {};
    ListView listView;
    ArrayAdapter<String> classAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list_of_search);
        classArray = new String[]{"XML","SQL","Entreprise","Anglais","Communication d'entreprise","JAVA","SAP","Marketing","Français"};
        listView = (ListView) findViewById(R.id.main_listview);

        classAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classArray);
        listView.setAdapter(classAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_settings_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void deleteButton(View view){
        Intent intent = new Intent(this, DeleteClass.class);
        startActivity(intent);
    }

    public void modifyButton(View view){
        Intent intent = new Intent(this, ModifyClass.class);
        startActivity(intent);
    }
}
