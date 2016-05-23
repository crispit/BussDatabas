package com.example.fredrikhansson.komigennuraa;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import java.util.ArrayList;

/**
 * Created by fredrikhansson on 4/18/16.
 */
class DbHelper extends SQLiteOpenHelper{

    //Unchangeable strings which is used when the database is created
    private static final String TABLE_NAME = "ErrorReport";
    private static final String COLUMN_NAME_ENTRYID = "FelrapportsID";
    public static final String COLUMN_NAME_SYMPTOM = "Felkategori";
    private static final String COLUMN_NAME_BUSID = "BussID";
    private static final String COLUMN_NAME_DATE = "Datum";
    public static final String COLUMN_NAME_COMMENT = "Kommentar";
    public static final String COLUMN_NAME_GRADE = "Gradering";
    private static final String COLUMN_NAME_STATUS = "Status";

    //Busspecific data
    private static final String COLUMN_NAME_Accelerator_Pedal_Position = "Accelerator_Pedal_Position";
    private static final String COLUMN_NAME_Ambient_Temperature = "Ambient_Temperature";
    private static final String COLUMN_NAME_At_Stop = "At_Stop";
    private static final String COLUMN_NAME_Cooling_Air_Conditioning = "Cooling_Air_Conditioning";
    private static final String COLUMN_NAME_Driver_Cabin_Temperature = "Driver_Cabin_Temperature";
    private static final String COLUMN_NAME_Fms_Sw_Version_Supported = "Fms_Sw_Version_Supported";
    private static final String COLUMN_NAME_GPS = "GPS";
    private static final String COLUMN_NAME_GPS2 = "GPS2";
    private static final String COLUMN_NAME_GPS_NMEA = "GPS_NMEA";
    private static final String COLUMN_NAME_Journey_Info = "Journey_Info";
    private static final String COLUMN_NAME_Mobile_Network_Cell_Info = "Mobile_Network_Cell_Info";
    private static final String COLUMN_NAME_Mobile_Network_Signal_Strength = "Mobile_Network_Signal_Strength";
    private static final String COLUMN_NAME_Next_Stop = "Next_Stop";
    private static final String COLUMN_NAME_Offroute = "Offroute";
    private static final String COLUMN_NAME_Online_Users = "Online_Users";
    private static final String COLUMN_NAME_Opendoor = "Opendoor";
    private static final String COLUMN_NAME_Position_Of_Doors = "Position_Of_Doors";
    private static final String COLUMN_NAME_Pram_Request = "Pram_Request";
    private static final String COLUMN_NAME_Ramp_Wheel_Chair_Lift = "Ramp_Wheel_Chair_Lift";
    private static final String COLUMN_NAME_Status_2_Of_Doors = "Status_2_Of_Doors";
    private static final String COLUMN_NAME_Stop_Pressed = "Stop_Pressed";
    private static final String COLUMN_NAME_Stop_Request = "Stop_Request";
    private static final String COLUMN_NAME_Total_Vehicle_Distance = "Total_Vehicle_Distance";
    private static final String COLUMN_NAME_Turn_Signals = "Turn_Signals";
    private static final String COLUMN_NAME_Wlan_Connectivity = "Wlan_Connectivity";

    //Helpstrings for queries
    private static final String TEXT_TYPE = " TEXT ";
    private static final String COMMA_SEP = ",";
    private static final String NUMBER_TYPE = " INTEGER";

    //String to create table in DB
    private static final String SQL_CREATE_ENTRIES = " CREATE TABLE " + TABLE_NAME +
            "(" + COLUMN_NAME_ENTRYID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_SYMPTOM + TEXT_TYPE +  COMMA_SEP +
            COLUMN_NAME_COMMENT + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_BUSID + TEXT_TYPE  + COMMA_SEP +
            COLUMN_NAME_DATE + TEXT_TYPE  + COMMA_SEP +
            COLUMN_NAME_GRADE + NUMBER_TYPE + COMMA_SEP +
            COLUMN_NAME_STATUS + NUMBER_TYPE + COMMA_SEP +
            COLUMN_NAME_Accelerator_Pedal_Position + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Ambient_Temperature + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_At_Stop + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Cooling_Air_Conditioning + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Driver_Cabin_Temperature + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Fms_Sw_Version_Supported + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_GPS + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_GPS2 + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_GPS_NMEA + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Journey_Info + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Mobile_Network_Cell_Info + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Mobile_Network_Signal_Strength + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Next_Stop + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Offroute + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Online_Users + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Opendoor + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Position_Of_Doors + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Pram_Request + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Ramp_Wheel_Chair_Lift + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Status_2_Of_Doors + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Stop_Pressed + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Stop_Request + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Total_Vehicle_Distance + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Turn_Signals + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME_Wlan_Connectivity + TEXT_TYPE + ")"  ;

    //String to drop database
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    //Strings with database info
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "Database.db";

    //constructor
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Method for creating database
     * @param db the input parameter
     */
    @Override
    public void onCreate (SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Method for upgrading database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Method for finding an error report for a specific error ID
     * @param id the error ID
     * @return a cursor
     */
    public Cursor getData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where "+COLUMN_NAME_ENTRYID+" ="+id, null );
        return res;
    }

    /**
     * Method for deleting an error report with a specific error id
     * @param errorId unique ID for the error report which to update
     * @param grade the updated urgency of the error
     * @param symptom the updated symptom of the error
     * @param symptom the updated comment of the error
     */
    public void updateErrorReport(String errorId, String grade, String symptom, String comment, String status){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_GRADE, grade);
        contentValues.put(COLUMN_NAME_SYMPTOM, symptom);
        contentValues.put(COLUMN_NAME_COMMENT, comment);
        contentValues.put(COLUMN_NAME_STATUS, status);

        db.update(TABLE_NAME, contentValues, COLUMN_NAME_ENTRYID+" = ? ", new String[]{errorId});

    }

    /**
     * Method to delete a specific error report for an unique error id
     * @param errorId unique ID for the error report which to delete
     */
    public void deleteErrorReport(String errorId){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, COLUMN_NAME_ENTRYID+" = ? ", new String[]{errorId});

    }

    /**
     * Method for finding all error reports for a specific bus
     * @param busID id to identify a specific bus
     */
    public ArrayList<ErrorReport> getBusReports(String busID) {
        ArrayList<ErrorReport> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where "+COLUMN_NAME_BUSID+" = ? and not "+COLUMN_NAME_STATUS+" = ?", new String[]{busID,"Löst"});
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            ErrorReport er = new ErrorReport(res.getString(res.getColumnIndex(COLUMN_NAME_ENTRYID)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_SYMPTOM)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_COMMENT)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_BUSID)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_DATE)),
                    res.getInt(res.getColumnIndex(COLUMN_NAME_GRADE)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_STATUS)));
            array_list.add(er);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public String getNewErrorId(){

        int nextId=0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+COLUMN_NAME_ENTRYID+" from "+TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            int currentId = Integer.parseInt(res.getString(res.getColumnIndex(COLUMN_NAME_ENTRYID)));
            if(currentId>nextId)
                nextId=currentId;
            res.moveToNext();
        }
        res.close();
        return Integer.toString(nextId+1);
    }


    //Method for inserting a preliminary error report
    public boolean insertPreliminaryReport(String errorID, String symptom, String comment, String busID, String date, int grade, String status){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_ENTRYID, errorID);
        contentValues.put(COLUMN_NAME_SYMPTOM, symptom);
        contentValues.put(COLUMN_NAME_COMMENT, "Kommentar saknas...");
        contentValues.put(COLUMN_NAME_BUSID, busID);
        contentValues.put(COLUMN_NAME_DATE, date);
        contentValues.put(COLUMN_NAME_GRADE, grade);
        contentValues.put(COLUMN_NAME_STATUS, status);

        db.insert(TABLE_NAME, null, contentValues);
        return true;

    }

    //Method for updating a preliminary report
    public boolean updatePreliminaryReport(String errorID, String accelerator_Pedal_Position, String ambient_Temperature, String at_Stop, String cooling_Air_Conditioning,
                                           String driver_Cabin_Temperature, String fms_Sw_Version_Supported, String gps, String gps2,
                                           String gps_nmea, String journey_Info, String mobile_Network_Cell_Info, String mobile_Network_Signal_Strength,
                                           String next_Stop, String offroute, String online_Users, String opendoor, String position_Of_Doors,
                                           String pram_Request, String ramp_Wheel_Chair_Lift, String status_2_Of_Doors, String stop_Pressed, String stop_Request,
                                           String total_Vehicle_Distance, String turn_Signals, String wlan_Connectivity){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_ENTRYID, errorID);
        contentValues.put(COLUMN_NAME_Accelerator_Pedal_Position, accelerator_Pedal_Position);
        contentValues.put(COLUMN_NAME_Ambient_Temperature, ambient_Temperature);
        contentValues.put(COLUMN_NAME_At_Stop, at_Stop);
        contentValues.put(COLUMN_NAME_Cooling_Air_Conditioning, cooling_Air_Conditioning);
        contentValues.put(COLUMN_NAME_Driver_Cabin_Temperature, driver_Cabin_Temperature);
        contentValues.put(COLUMN_NAME_Fms_Sw_Version_Supported, fms_Sw_Version_Supported);
        contentValues.put(COLUMN_NAME_GPS, gps);
        contentValues.put(COLUMN_NAME_GPS2, gps2);
        contentValues.put(COLUMN_NAME_GPS_NMEA, gps_nmea);
        contentValues.put(COLUMN_NAME_Journey_Info, journey_Info);
        contentValues.put(COLUMN_NAME_Mobile_Network_Cell_Info, mobile_Network_Cell_Info);
        contentValues.put(COLUMN_NAME_Mobile_Network_Signal_Strength, mobile_Network_Signal_Strength);
        contentValues.put(COLUMN_NAME_Next_Stop, next_Stop);
        contentValues.put(COLUMN_NAME_Offroute, offroute);
        contentValues.put(COLUMN_NAME_Online_Users, online_Users);
        contentValues.put(COLUMN_NAME_Opendoor, opendoor);
        contentValues.put(COLUMN_NAME_Position_Of_Doors, position_Of_Doors);
        contentValues.put(COLUMN_NAME_Pram_Request, pram_Request);
        contentValues.put(COLUMN_NAME_Ramp_Wheel_Chair_Lift, ramp_Wheel_Chair_Lift);
        contentValues.put(COLUMN_NAME_Status_2_Of_Doors, status_2_Of_Doors);
        contentValues.put(COLUMN_NAME_Stop_Pressed, stop_Pressed);
        contentValues.put(COLUMN_NAME_Stop_Request, stop_Request);
        contentValues.put(COLUMN_NAME_Total_Vehicle_Distance, total_Vehicle_Distance);
        contentValues.put(COLUMN_NAME_Turn_Signals, turn_Signals);
        contentValues.put(COLUMN_NAME_Wlan_Connectivity, wlan_Connectivity);

        db.update(TABLE_NAME, contentValues, COLUMN_NAME_ENTRYID+" = ? ", new String[]{errorID});
        return true;
    }

}
