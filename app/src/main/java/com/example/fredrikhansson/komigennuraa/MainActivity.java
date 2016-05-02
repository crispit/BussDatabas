package com.example.fredrikhansson.komigennuraa;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    int urgency;

    Button b1;
    Button b2;
    Button b3;
    Button b4;

    DBHelper mydb;
    String busId;
    String symptom;
    boolean symptomSelected = false;
    boolean gradeSelected = false;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = (Button)findViewById(R.id.button);
        sendButton.setEnabled(false);

        calendar = Calendar.getInstance();

        busId = "Vin_Num_001";

        Context sharedContext = null;
        try {
            sharedContext = this.createPackageContext("crispit.errorextractor", Context.CONTEXT_INCLUDE_CODE);
            if (sharedContext == null) {
                return;
            }
        } catch (Exception e) {
            String error = e.getMessage();
            return;
        }

        mydb = new DBHelper(sharedContext);

        b1 = (Button) findViewById(R.id.colorButton1);
        b2 = (Button) findViewById(R.id.colorButton2);
        b3 = (Button) findViewById(R.id.colorButton3);
        b4 = (Button) findViewById(R.id.colorButton4);

    }


    //Method for showing all error reports in the database via a button
    public void displayReport (View V){
        Intent i = new Intent(this, TestList.class);
        startActivity(i);
    }

    //Method for adding reports in the database via a button
    public void addReport (View v){
        new RetrieveBusData().execute("Test");
        resetScreen();
    }

    //Method that is called when selecting any of the color buttons
    public void selectColor (View v) {
        v.setSelected(true);
        gradeSelected = true;

        switch (v.getId()) {
            case R.id.colorButton1:
                b2.setSelected(false);
                b3.setSelected(false);
                b4.setSelected(false);
                urgency = 1;
                break;
            case R.id.colorButton2:
                b1.setSelected(false);
                b3.setSelected(false);
                b4.setSelected(false);
                urgency = 2;
                break;
            case R.id.colorButton3:
                b1.setSelected(false);
                b2.setSelected(false);
                b4.setSelected(false);
                urgency = 3;
                break;
            case R.id.colorButton4:
                b1.setSelected(false);
                b2.setSelected(false);
                b3.setSelected(false);
                urgency = 4;
                break;
        }
        unLockSend();
    }

    //Metod for opening a list of symptoms
    public void symptomList(View V){
        Intent i = new Intent(this, SymptomList.class);
        startActivityForResult(i, 1);
    }

    //Method for catching the name of the symptom and adding it to an error report
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                symptom = data.getStringExtra("symptom");
                Button symptomButton = (Button)findViewById(R.id.button2);

                symptomButton.setText(symptom);

                symptomSelected = true;
                unLockSend();
            }
        }
    }

    //Method for unlocking the send button once a symptom and grade has been selected
    public void unLockSend(){

        if (gradeSelected && symptomSelected){
            Button sendButton = (Button)findViewById(R.id.button);
            sendButton.setEnabled(true);
            sendButton.setBackgroundResource(R.drawable.buttonn);
        }

    }

    //Method for resetting the screen after sending an error report
    public void resetScreen(){

        symptomSelected = false;
        gradeSelected = false;
        Button symptomButton = (Button)findViewById(R.id.button2);
        symptomButton.setText("Symptom");

        Button sendButton = (Button)findViewById(R.id.button);
        sendButton.setEnabled(false);
        sendButton.setBackgroundResource(R.drawable.buttonn_grey);

        b1.setSelected(false);
        b2.setSelected(false);
        b3.setSelected(false);
        b4.setSelected(false);


    }

    private class RetrieveBusData extends AsyncTask<String, String, String> {

        private Exception exception;

        protected String doInBackground(String... str) {
            try {

                mydb.insertErrorReport(mydb.getNewErrorId(), symptom, "Funkar det? Borde va 9 nu!!", busId, calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+", "+calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND), urgency,
                        BusData.getBusInfo(busId,"Accelerator_Pedal_Position"),
                        BusData.getBusInfo(busId,"Ambient_Temperature"), BusData.getBusInfo(busId,"At_Stop"),
                        BusData.getBusInfo(busId,"Cooling_Air_Conditioning"), BusData.getBusInfo(busId,"Driver_Cabin_Temperature"),
                        BusData.getBusInfo(busId,"Fms_Sw_Version_Supported"), BusData.getBusInfo(busId,"GPS"),
                        BusData.getBusInfo(busId,"GPS2"), BusData.getBusInfo(busId,"GPS_NMEA"),
                        BusData.getBusInfo(busId,"Journey_Info"), BusData.getBusInfo(busId,"Mobile_Network_Cell_Info"),
                        BusData.getBusInfo(busId,"Mobile_Network_Signal_Strength"), BusData.getBusInfo(busId,"Next_Stop"),
                        BusData.getBusInfo(busId,"Offroute"), BusData.getBusInfo(busId,"Online_Users"),
                        BusData.getBusInfo(busId,"Opendoor"), BusData.getBusInfo(busId,"Position_Of_Doors"),
                        BusData.getBusInfo(busId,"Pram_Request"), BusData.getBusInfo(busId,"Ramp_Wheel_Chair_Lift"),
                        BusData.getBusInfo(busId,"Status_2_Of_Doors"), BusData.getBusInfo(busId,"Stop_Pressed"),
                        BusData.getBusInfo(busId,"Stop_Request"), BusData.getBusInfo(busId,"Total_Vehicle_Distance"),
                        BusData.getBusInfo(busId,"Turn_Signals"), BusData.getBusInfo(busId,"Wlan_Connectivity"));

            } catch (Exception e) {
                this.exception = e;
                return "Could not insert!";
            }
            return "Insertion successful!";
        }

        protected void onPostExecute() {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

}

