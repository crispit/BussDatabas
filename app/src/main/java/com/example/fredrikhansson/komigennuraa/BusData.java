package com.example.fredrikhansson.komigennuraa; /**
 * Created by Mikael on 2016-04-25.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

class BusData {

    private static String[] getSensors(){
        String[] sensors = new String[25];

        sensors[0]="Accelerator_Pedal_Position";
        sensors[1]="Ambient_Temperature";
        sensors[2]="At_Stop";
        sensors[3]="Cooling_Air_Conditioning";
        sensors[4]="Driver_Cabin_Temperature";
        sensors[5]="Fms_Sw_Version_Supported";
        sensors[6]="GPS";
        sensors[7]="GPS2";
        sensors[8]="GPS_NMEA";
        sensors[9]="Journey_Info";
        sensors[10]="Mobile_Network_Cell_Info";
        sensors[11]="Mobile_Network_Signal_Strength";
        sensors[12]="Next_Stop";
        sensors[13]="Offroute";
        sensors[14]="Online_Users";
        sensors[15]="Opendoor";
        sensors[16]="Position_Of_Doors";
        sensors[17]="Pram_Request";
        sensors[18]="Ramp_Wheel_Chair_Lift";
        sensors[19]="Status_2_Of_Doors";
        sensors[20]="Stop_Pressed";
        sensors[21]="Stop_Request";
        sensors[22]="Total_Vehicle_Distance";
        sensors[23]="Turn_Signals";
        sensors[24]="Wlan_Connectivity";

        return sensors;
    }




    private static String getBusInfo(String dgwNr, String infoTxt) throws IOException, EmptyReturnValueException {
        String dgw = "Ericsson$" + dgwNr;

        long t2 = System.currentTimeMillis();
        long t1 = t2 - (1000 * 120);

        StringBuffer response = new StringBuffer();
        String key = "Basic Z3JwMTA0OjRERnJlOXRrZ3U=";
        String url = "https://ece01.ericsson.net:4443/ecity?dgw=" + dgw + "&sensorSpec=Ericsson$" + infoTxt + "&t1=" + t1 + "&t2=" + t2;

        URL requestURL = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) requestURL
                .openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", key);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));


        String inputLine;


        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        if (response.toString().equals("")){
            throw new EmptyReturnValueException();
        }
        return response.toString();

    }

    private static class wThreads implements Runnable {

        private final String sensor;
        private final String dgwNr;
        private final HashMap<String,String> busDataMap;

        public wThreads(String dgwNr, String sensor,HashMap<String,String> busDataMap) {
            this.sensor=sensor;
            this.dgwNr=dgwNr;
            this.busDataMap=busDataMap;
        }
        @Override
        public void run() {
            try{
                busDataMap.put(sensor,getBusInfo(dgwNr,sensor));
            }
            catch (IOException e) {
                busDataMap.put(sensor,"Buss-ID:t existerar inte");

            }
            catch (EmptyReturnValueException e) {
                busDataMap.put(sensor,"Plattformen returnerade inget värde");
            }
        }
    }

    public static HashMap<String,String> getAllBusInfo (String dgwNr){

        String[] sensors = getSensors();
        HashMap<String, String> busDataMap = new HashMap<>(32);

        Thread[] myThreads;
        myThreads = new Thread[sensors.length];

        for (int i = 0; i < sensors.length; i++) {
            myThreads[i] = new Thread (new wThreads(dgwNr, sensors[i], busDataMap));
            myThreads[i].start();
        }


        for (int j = 0; j < sensors.length; j++) {

            try{
                myThreads[j].join();
            }
            catch(InterruptedException e) {
                busDataMap.put(sensors[j],"Kontakten med plattformen har blivit avbruten.");
            }
        }
        return busDataMap;

    }
}