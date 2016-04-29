package com.example.fredrikhansson.komigennuraa;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.hardware.camera2.params.BlackLevelPattern;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    public void addReport (View v){
        mydb.insertErrorReport("b", "a", "a", "a", "b", 1);
    }

    public void selectColor (View v) {
        v.setSelected(true);

            //int c1 = v.getSolidColor();
        //v.setBackgroundResource(R.drawable.border_button);

        //v.setBackgroundColor(c1);

        //Button B = (Button) findViewById(R.id.)

    }

    public void testTable (){

    }

}
