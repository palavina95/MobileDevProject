package com.example.schoolmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        classArray = new String[]{"XML","SQL","Entreprise","Anglais","Communication d'entreprise","JAVA","SAP","Marketing","Français","Math","Software development mobile de la mort qui tue de sa chienne de pute","Algo","TB","HES patron"};
        listView = (ListView) findViewById(R.id.main_listview);

        classAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classArray);
        listView.setAdapter(classAdapter);
    }
}
