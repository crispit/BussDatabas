package com.example.fredrikhansson.komigennuraa;

/**
 * Created by fredrikhansson on 4/21/16.
 */


import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
import android.widget.ListView;

        import java.util.ArrayList;

public class ErrorReportsList extends AppCompatActivity {

    ListView listView ;
    DbHelper mydb;
    private ArrayList<ErrorReport> list;
    String busId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlist);

        mydb = new DbHelper(this);

        busId = getIntent().getStringExtra("busId");

        //creates a list och view showing the buses
        listView = (ListView) findViewById(R.id.busList);

        list = mydb.getBusReports(busId); // Adds all reports in the list

        setAdapterToListview();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Intent i = new Intent(view.getContext(), UpdateReport.class);
                Bundle bundle = new Bundle();
                bundle.putString("errorId",list.get(position).getId());
                i.putExtras(bundle);
                startActivityForResult(i, 2);
            }

        });

    }//onCreate

    //Method for updating the reports list after a change
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 2) {

            list = mydb.getBusReports(busId); // Adds all reports in the list
            setAdapterToListview();
        }
    }//onActivityResult

    //Method for adding the adapter to the listview.
    public void setAdapterToListview() {
        ListRowAdapter objAdapter = new ListRowAdapter(ErrorReportsList.this,
                R.layout.row, list);
        listView.setAdapter(objAdapter);
    }//setAdapterToListview


}
