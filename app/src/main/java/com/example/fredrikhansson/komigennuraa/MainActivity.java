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
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int urgency;

    Button b1;
    Button b2;
    Button b3;
    Button b4;

    DbHelper mydb;
    boolean symptomSelected = false;
    boolean gradeSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = (Button)findViewById(R.id.button);
        sendButton.setEnabled(false);

        mydb = new DbHelper(this);

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
        mydb.insertErrorReport("b", "a", "a", "a", "b", urgency);
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
               String symptom = data.getStringExtra("symptom");
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

}
