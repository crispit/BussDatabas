package com.example.fredrikhansson.komigennuraa;

/**
 * Created by fredrikhansson on 4/21/16.
 */


import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

        import java.util.ArrayList;

public class TestList extends AppCompatActivity {

    ListView listView ;
    DbHelper mydb;
    int clickPosition;
    ArrayAdapter adapter;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlist);
        ListView busList = new ListView(this.getBaseContext());

        mydb = new DbHelper(this);

        //skapar lista och vy för visning av bussar
        listView = (ListView) findViewById(R.id.busList);
        list=new ArrayList<String>();
        list = mydb.getAllBuses(); //- Lägger till alla bussar i listan


        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
               // Intent intent = new Intent(view.getContext(),BusInfo.class);
                //TODO: Skapa en bundle och skicka med data för vilken buss som ska öppnas
                //startActivity(intent);
            }

        });

    }


}
