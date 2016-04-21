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

    //Oföränderliga strängar som används till skapande av databasen
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

    //Sträng för att skapa vår databas i SQL
    private static final String SQL_CREATE_ENTRIES = " CREATE TABLE " + TABLE_NAME +
            "(" + COLUMN_NAME_ENTRYID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_SYMPTOM + TEXT_TYPE +  COMMA_SEP +
            COLUMN_NAME_COMMENT + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_BUSID + TEXT_TYPE  + COMMA_SEP +
            COLUMN_NAME_DATE + TEXT_TYPE  + COMMA_SEP +
            COLUMN_NAME_GRADE + NUMBER_TYPE + ")"  ;

    //Sträng för att droppa vår databas i SQL
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS" +
            TABLE_NAME;

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "Database.db";

    //konstruktor
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
     }

    /**
     * Metod som skapar en databas
     * @param db
     */
    @Override
    public void onCreate (SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Metod för insättning av felrapporter i databasen
     * @param errorID
     * @param symptom
     * @param busID
     * @param date
     * @param comment
     * @param grade
     * @return
     */
    public boolean insertErrorReport(String errorID, String symptom, String comment, String busID, String date, int grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ErrorID", errorID);
        contentValues.put("Symptom", symptom);
        contentValues.put("Comment", comment);
        contentValues.put("BusID", busID);
        contentValues.put("Date", date);
        contentValues.put("Grade", grade);
        db.insert("ErrorReport", null, contentValues);
        return true;
    }

    /**
     * Metod för att hitta en felrapport
     * @param id
     * @return
     */
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from ErrorReport where id="+id+"", null );
        return res;
    }

    /**
     * Metod som returnerar en arraylis med alla rapporter i databasen
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
     * Metod för att hitta alla felraporten för en specifik buss
     * @param busID id för att identifiera en specifik buss
     */
    public ArrayList<ErrorReport> getBusReports(String busID) {
        ArrayList<ErrorReport> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ErrorReport WHERE BusID = ?", new String[]{busID});
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
     * Metod för att returnera alla unika bussar (med fel)
     * @return arraylist med bussar som har felrapporter
     */
        public ArrayList<String> getAllBuses() {
            ArrayList<String> array_list = new ArrayList<>();


            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery( "select DISTINCT BusID from ErrorReport", null );
            res.moveToFirst();

            while(res.isAfterLast() == false){
                array_list.add(res.getString(res.getColumnIndex(COLUMN_NAME_BUSID)));
                res.moveToNext();
            }
            return array_list;
        }




    /**
     * Metod för upgradering av databasen
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
