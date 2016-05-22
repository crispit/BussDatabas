package com.example.fredrikhansson.komigennuraa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

    DbHelper mydb;
    String errorId;
    String symptom;
    String grade;

    boolean commentSelected = false;

    Cursor cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatereport);

        //creating buttons and comment field
        updateButton = (Button)findViewById(R.id.updatebutton);
        deleteButton = (Button)findViewById(R.id.deletebutton);
        symptomButton = (Button)findViewById(R.id.symptombutton);
        gradeButton = (Button) findViewById(R.id.gradebutton);
        commentField = (EditText) findViewById(R.id.editText);

        //adding a listener to the comment field
        commentField.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                commentSelected=true;
                unLockSend();

            }
        });

        //fetching the id for the error report
        errorId = getIntent().getStringExtra("errorId");

        mydb = new DbHelper(this);

        cur = mydb.getData(errorId);
        cur.moveToFirst();

        updateButton.setEnabled(false);

        grade = cur.getString(cur.getColumnIndex(DbHelper.COLUMN_NAME_GRADE));
        symptom = cur.getString(cur.getColumnIndex(DbHelper.COLUMN_NAME_SYMPTOM));
        setGradeButtonColor();
        symptomButton.setText(symptom);

    }//onCreate

    //Method for setting number and color on the grade buttons.
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

    }//setGradeButtonColor

    //Method for updating reports and changing status to completed in the database via a button
    public void updateReport (View v){

        String comment = commentField.getText().toString();
        if(!comment.equals("")) {
            mydb.updateErrorReport(errorId, grade, symptom, comment, "Kommenterad");
        }
        else {
            mydb.updateErrorReport(errorId, grade, symptom, "Kommentar saknas...", "Okommenterad");
        }
        Intent i = new Intent();
        i.putExtra("action", "refresh");
        setResult(RESULT_OK, i);
        finish();
    }//updateReport

    //Method for deleting reports in the database via a button
    public void deleteReport (View v){




        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        mydb.deleteErrorReport(errorId);
                        Intent i = new Intent();
                        i.putExtra("action", "delete");
                        setResult(RESULT_OK, i);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        //finish();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vill du verkligen radera den här felrapporten?").setNegativeButton("Nej", dialogClickListener)
                .setPositiveButton("Ja", dialogClickListener).show();





    }//deleteReport

    //Method for opening a list of symptoms
    public void symptomList(View V){
        Intent i = new Intent(this, SymptomList.class);
        startActivityForResult(i, 1);
    }//symptomList

    //Method for selecting a new grade. Opens up a popup-window from which to choose.
    public void setGrade(View v) {

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
    }//setGrade

    //Method called when a new grade is selected.
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
    }//gradeSelected

    //Method for catching the name of the symptom and adding it to an error report
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                symptom = data.getStringExtra("symptom");
                symptomButton.setText(symptom);
            }
        }
    }//onActivityResult

    //Method for unlocking the send button once a comment has been selected
    public void unLockSend(){

        if (commentSelected){
            updateButton.setEnabled(true);
            updateButton.setBackgroundResource(R.drawable.buttonn);
        }

    }//unLockSend

    //Method for resetting the screen after sending an error report
    public void resetScreen(){

        gradeButton.setText(grade);
        setGradeButtonColor();

    }//resetScreen

}

