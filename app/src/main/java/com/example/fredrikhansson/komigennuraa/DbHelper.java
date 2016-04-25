package com.example.fredrikhansson.komigennuraa;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.DatabaseUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;


/**
 * Created by fredrikhansson on 4/18/16.
 */
public class DbHelper extends SQLiteOpenHelper{

    //Static strings that are used in the creation of the database
    public static final String TABLE_NAME = "ErrorReport";
    public static final String COLUMN_NAME_ENTRYID = "ErrorID";
    public static final String COLUMN_NAME_SYMPTOM = "Symptom";
    public static final String COLUMN_NAME_BUSID = "BusID";
    public static final String COLUMN_NAME_DATE = "Date";
    public static final String COLUMN_NAME_COMMENT = "Comment";
    public static final String COLUMN_NAME_GRADE = "Grade";

    private static final String TEXT_TYPE = " TEXT ";
    private static final String COMMA_SEP = ",";
    private static final String NUMBER_TYPE = " INTEGER";
    private HashMap hp;

    //String to create the database in SQL
    private static final String SQL_CREATE_ENTRIES = " CREATE TABLE " + TABLE_NAME +
            "(" + COLUMN_NAME_ENTRYID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_SYMPTOM + TEXT_TYPE +  COMMA_SEP +
            COLUMN_NAME_COMMENT + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_BUSID + TEXT_TYPE  + COMMA_SEP +
            COLUMN_NAME_DATE + TEXT_TYPE  + COMMA_SEP +
            COLUMN_NAME_GRADE + NUMBER_TYPE + ")"  ;

    //String to drop the databse in SQL
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS" +
            TABLE_NAME;

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "Database.db";

    //Constructor
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
     }

    /**
     * Method that creates a database
     * @param db
     */
    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Method for inserting error report into the database
     * @param errorID   the unique ID of the error report
     * @param symptom   a description of the symtptom to the problem
     * @param busID     the unique ID of the bus
     * @param date      the date that the problem is registered
     * @param comment   additional comment of the error report
     * @param grade     a number indicating the gravity of the problem
     * @return          true, meaning the insertion has been made
     */
    public boolean insertErrorReport(String errorID, String symptom, String comment, String busID, String date, int grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ErrorID", errorID);
        cv.put("Symptom", symptom);
        cv.put("Comment", comment);
        cv.put("BusID", busID);
        cv.put("Date", date);
        cv.put("Grade", grade);
        db.insert("ErrorReport", null, cv);
        return true;
    }

    /**
     * Method for creating a error report
     * @param id
     * @return
     */
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from ErrorReport where id="+id+"", null );
        return res;
    }

    /**
     * Method that returns an ArrayList with all reports in the database
     */
    public ArrayList<String> getAllReports() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from ErrorReport", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_NAME_ENTRYID)));
            res.moveToNext();
        }
        return array_list;
    }

    /**
     * Method for finding all error reports for a specific bus
     * @param busID     id to identify a bus
     */
    public ArrayList<ErrorReport> getBusReports(String busID) {
        ArrayList<ErrorReport> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM ErrorReport WHERE BusID = ?", new String[]{busID});
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            ErrorReport er = new ErrorReport(res.getString(res.getColumnIndex(COLUMN_NAME_ENTRYID)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_SYMPTOM)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_COMMENT)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_BUSID)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_DATE)),
                    res.getInt(res.getColumnIndex(COLUMN_NAME_GRADE)));
            array_list.add(er);
            res.moveToNext();
        }
        return array_list;
    }


    /**
     * Method for returning all unique buses that have been registred with an error
     * @return      arraylist with all busses that have error reports
     */
        public ArrayList<String> getAllBuses() {
            ArrayList<String> array_list = new ArrayList<>();


            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery( "SELECT DISTINCT BusID FROM ErrorReport", null );
            res.moveToFirst();

            while(res.isAfterLast() == false){
                array_list.add(res.getString(res.getColumnIndex(COLUMN_NAME_BUSID)));
                res.moveToNext();
            }
            return array_list;
        }




    /**
     * Method for upgrading the databases
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
