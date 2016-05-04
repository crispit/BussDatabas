package com.example.fredrikhansson.komigennuraa;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


public class UpdateReport extends AppCompatActivity {

    Button gradeButton;
    Button updateButton;
    Button deleteButton;
    Button symptomButton;
    EditText commentField;
    PopupWindow popupMessage;

    Button b1;
    Button b2;
    Button b3;
    Button b4;

    DBHelper mydb;
    String busId;
    String errorId;
    String symptom;
    String status;
    String grade;
    boolean symptomSelected = false;
    boolean gradeSelected = false;

    Cursor cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatereport);

        updateButton = (Button)findViewById(R.id.updatebutton);
        deleteButton = (Button)findViewById(R.id.deletebutton);
        symptomButton = (Button)findViewById(R.id.symptombutton);
        gradeButton = (Button) findViewById(R.id.gradebutton);
        commentField = (EditText) findViewById(R.id.editText);

        errorId = getIntent().getStringExtra("errorId");

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

        cur = mydb.getData(errorId);
        cur.moveToFirst();

        //updateButton.setEnabled(false);

        grade = cur.getString(cur.getColumnIndex("Grade"));
        symptom = cur.getString(cur.getColumnIndex("Symptom"));

        setGradeButtonColor();

        symptomButton.setText(symptom);

    }

    public void setGradeButtonColor(){

        switch (grade) {
            case "1":
                gradeButton.setBackgroundResource(R.drawable.custom_button1);
                gradeButton.setText("1");
                break;
            case "2":
                gradeButton.setBackgroundResource(R.drawable.custom_button2);
                gradeButton.setText("2");
                break;
            case "3":
                gradeButton.setBackgroundResource(R.drawable.custom_button3);
                gradeButton.setText("3");
                break;
            case "4":
                gradeButton.setBackgroundResource(R.drawable.custom_button4);
                gradeButton.setText("4");
                break;
        }

    }


    //Method for showing all error reports in the database via a button
    public void displayReport (View V){
        Intent i = new Intent(this, TestList.class);
        Bundle bundle = new Bundle();
        bundle.putString("busId", busId);
        i.putExtras(bundle);
        startActivity(i);
    }

    //Method for updating reports in the database via a button
    public void updateReport (View v){
        String comment = commentField.getText().toString();
        System.out.println(comment);
        mydb.updateErrorReport(errorId, grade, symptom, comment, "completed");
        finish();
    }

    //Method for deleting reports in the database via a button
    public void deleteReport (View v){
        mydb.deletErrorReport(errorId);
        finish();
    }

    //Metod for opening a list of symptoms
    public void symptomList(View V){
        Intent i = new Intent(this, SymptomList.class);
        startActivityForResult(i, 1);
    }

    public void setGrade(View v){
        popupInit(v);
    }

    public void popupInit(View v) {
        //popupButton.setOnClickListener(this);
        //insidePopupButton.setOnClickListener(this);

        try{

        LayoutInflater inflater = (LayoutInflater) UpdateReport.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_choose_grade, (ViewGroup) findViewById(R.id.gradeLayout));
        popupMessage = new PopupWindow(layout, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            popupMessage.setBackgroundDrawable(new BitmapDrawable());
            popupMessage.setOutsideTouchable(true);
        popupMessage.showAtLocation(v, Gravity.CENTER, 0, -100);

            b1 = (Button) findViewById(R.id.cButton1);
            b2 = (Button) findViewById(R.id.cButton2);
            b3 = (Button) findViewById(R.id.cButton3);
            b4 = (Button) findViewById(R.id.cButton4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gradeSelected(View v) {

        switch (v.getId()) {
            case R.id.cButton1:
                grade = "1";
                popupMessage.dismiss();
                resetScreen();
                break;
            case R.id.cButton2:
                grade = "2";
                popupMessage.dismiss();
                resetScreen();
                break;
            case R.id.cButton3:
                grade = "3";
                popupMessage.dismiss();
                resetScreen();
                break;
            case R.id.cButton4:
                grade = "4";
                popupMessage.dismiss();
                resetScreen();
                break;
        }
    }

    //Method for catching the name of the symptom and adding it to an error report
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                symptom = data.getStringExtra("symptom");
                //symptomButton = (Button)findViewById(R.id.button2);

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
            updateButton.setEnabled(true);
            updateButton.setBackgroundResource(R.drawable.buttonn);
        }

    }

    //Method for resetting the screen after sending an error report
    public void resetScreen(){

        symptomSelected = false;
        gradeSelected = false;
        //Button symptomButton = (Button)findViewById(R.id.button2);
        symptomButton.setText("Symptom");
        gradeButton.setText(grade);
        setGradeButtonColor();

        Button sendButton = (Button)findViewById(R.id.button);
        updateButton.setEnabled(false);
        updateButton.setBackgroundResource(R.drawable.buttonn_grey);

    }

}

