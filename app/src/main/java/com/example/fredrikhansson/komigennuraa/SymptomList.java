package com.example.fredrikhansson.komigennuraa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by fredrikhansson on 01/05/16.
 */
public class SymptomList extends AppCompatActivity {


    private ListView listView ;
    private String selectedSymptom;
    int clickPosition;
    //private SymptomListRowAdapter adapter;
    private ArrayAdapter adapter;
    private ArrayList<String> symptomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.symptomlist);

            //creates a list och view showing the buses
            listView = (ListView) findViewById(R.id.symptomList);
            symptomList=new ArrayList<>();
            symptomList.addAll(Arrays.asList(Symptoms.getsymptoms()));
            selectedSymptom = getIntent().getStringExtra("symptom");
            adapter=new SymptomListRowAdapter(this, symptomList, selectedSymptom);

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

