package com.example.fredrikhansson.komigennuraa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    DbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DbHelper(this);
        //ArrayList array_list = mydb.getAllReports();
        //ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

    }


    //Method for showing all error reports in the database via a button
    public void displayReport (View V){
            Intent i = new Intent(this, TestList.class);
            startActivity(i);
    }

    //Method for adding reports in the database via a button
    public void addReport (View V){
            mydb.insertErrorReport("b", "a", "a", "a", "b", 1);
    }

    public void testTable (){

    }

}
