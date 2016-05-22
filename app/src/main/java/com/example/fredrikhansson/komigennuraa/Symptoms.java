package com.example.fredrikhansson.komigennuraa; /**
 * Created by Mikael on 2016-04-25.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

class Symptoms {

    public static String[] getsymptoms(){
        String[] symptoms = new String[46];

        symptoms[0]="Säkerhet/Brand - Skadegörelse/Vandalism - Klotter buss invändigt";
        symptoms[1]="Säkerhet/Brand - Skadegörelse/Vandalism - Övrig skadegörelse";
        symptoms[2]="Säkerhet/Brand - Ordningsstörning - Oljud/Gap/Skrik (ej hotfullt)";
        symptoms[3]="Bussar - Dörrfel - Dörröppning/stängning";
        symptoms[4]="Bussar - Dörrfel - Stänger inte";
        symptoms[5]="Bussar - Klimatfel - Temperatur förare";
        symptoms[6]="Bussar - Förarplatsfel - Förarstol";
        symptoms[7]="Bussar - Förarplatsfel - Instrumentering";
        symptoms[8]="Bussar - Förarplatsfel - Kontrollampor";
        symptoms[9]="Bussar - Förarplatsfel - Övrigt förarplats";
        symptoms[10]="Bussar - Teknisk utrustning - Fordonsdator/Radio";
        symptoms[11]="Bussar - Teknisk utrustning - Viseringsutrustning";
        symptoms[12]="Bussar - Teknisk utrustning - Skyltning/Utrop";
        symptoms[13]="Bussar - Teknisk utrustning - Bussprioritering";
        symptoms[14]="Bussar - Broms-/luftfel - P-broms";
        symptoms[15]="Bussar - Utvändigt fel - Vindrutetorkare/spolare";
        symptoms[16]="Bussar - Utvändigt fel - Däck";
        symptoms[17]="Bussar - Utvändigt fel - Plåtskada";
        symptoms[18]="Bussar - Utvändigt fel - Övrigt utvändigt";
        symptoms[19]="Bussar - Invändigt fel - Övrigt invändigt";
        symptoms[20]="Bussar - Drivningsfel - Motorfel";
        symptoms[21]="Bussar - Drivningsfel - Styrservo";
        symptoms[22]="Bussar - Drivningsfel - Elfel";
        symptoms[23]="Bussar - Drivningsfel - Övrigt drivningsfel";
        symptoms[24]="Bussar - Vätskerelaterat - Spolarvätska";
        symptoms[25]="Bussar - Vätskerelaterat - Bränsle";
        symptoms[26]="Felanmälan - Rastlokal - Övrigt rastlokal";
        symptoms[27]="Felanmälan - Trafik - Signalfel (ej prioritering)";
        symptoms[28]="Felanmälan - Trafik - Laddstation";
        symptoms[29]="Trafik - Platsbrist - Inga lediga sittplatser ( > 70-väg)";
        symptoms[30]="Trafik - Platsbrist - Få lediga ståplatser";
        symptoms[31]="Trafik - Platsbrist - Kvarlämnade passagerare";
        symptoms[32]="Trafik - Framkomlighet - Köer";
        symptoms[33]="Trafik - Framkomlighet - Hinder";
        symptoms[34]="Trafik - Framkomlighet - Övrigt framkomlighet";
        symptoms[35]="Olycka/Tillbud - Trafikolycka - Krock med vägfordon";
        symptoms[36]="Olycka/Tillbud - Trafikolycka - Påkörning fasta föremål";
        symptoms[37]="Olycka/Tillbud - Personolycka (resenär) - Sjukdomsfall i buss";
        symptoms[38]="Personal - Händelse - Medarbetare saknas";
        symptoms[39]="Personal - Händelse - Sen ankomst";
        symptoms[40]="Personal - Händelse - Avvikande från körväg";
        symptoms[41]="Personal - Händelse - Övriga händelser";
        symptoms[42]="Personal - Synpunkter - Övriga synpunkter";
        symptoms[43]="Personal - Bemanning - Sjuk under tjänst";
        symptoms[44]="Papperskorg";
        symptoms[45]="Övrigt";

        return symptoms;
    }

}