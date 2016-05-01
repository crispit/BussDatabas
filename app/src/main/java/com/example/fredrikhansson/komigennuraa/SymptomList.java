package com.example.fredrikhansson.komigennuraa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by fredrikhansson on 01/05/16.
 */
public class SymptomList extends AppCompatActivity {


    ListView listView ;
    DbHelper mydb;
    int clickPosition;
    ArrayAdapter adapter;
    private ArrayList<String> symptomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.symptomlist);


        mydb = new DbHelper(this);

        //creates a list och view showing the buses
        listView = (ListView) findViewById(R.id.symptomList);
        symptomList=new ArrayList<String>();
        symptomList.add("Dörr fastnade");
        symptomList.add("Motor exploderade");
        symptomList.add("Buss dog");




        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,symptomList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                //TODO: Skapa en bundle och skicka med data för vilken buss som ska öppnas

                Intent i = new Intent();
                i.putExtra("symptom", symptomList.get(position));
                setResult(RESULT_OK, i);
                finish();
            }

        });

    }
}

