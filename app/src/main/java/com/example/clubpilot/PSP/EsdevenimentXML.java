package com.example.clubpilot.PSP;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.clubpilot.Fan.CardNew;
import com.example.clubpilot.Fan.News;
import com.example.clubpilot.Player.Event;
import com.example.clubpilot.SQLite.DatabaseHelper;
import com.example.clubpilot.SQLite.EventContract;

import org.apache.commons.net.ftp.FTPClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class EsdevenimentXML implements Runnable{
    public static boolean connected = false;
    private static String hostname = "ftpupload.net";
    private static int port = 21;
    private static String username = "if0_38540833";
    private static String password = "yyFRKtFk8nhx9";
    private final Context context;

    public EsdevenimentXML(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        FTPClient ftpClient = new FTPClient();
        boolean download = false;
        FileOutputStream fos = null;

        try {
            ftpClient.connect(hostname, port);
            ftpClient.enterLocalPassiveMode();
            boolean login = ftpClient.login(username, password);

            if (login) {
                connected = true;
                System.out.println("Connection established...");

                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(downloadsDir, "esdeveniment.xml");

                fos = new FileOutputStream(file);
                download = ftpClient.retrieveFile("/htdocs/esdeveniment.xml", fos);

                if (download) {
                    System.out.println("File downloaded successfully!");

                        List<Event> events = parseEsdevenimentsXML();
                        DatabaseHelper dbHelper = new DatabaseHelper(context);
                        dbHelper.clearEventsTable();// Opcional: Limpiar datos antiguos
                        for (Event event : events) {
                            dbHelper.insertEvent(event);
                        }
                    logDatabaseContents();
                } else {
                    System.out.println("Error in downloading file!");
                }

                ftpClient.logout();
                System.out.println("Connection closed...");
            } else {
                connected = false;
                System.out.println("Connection failed...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void logDatabaseContents() {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
// En tu método logDatabaseContents():
        Cursor cursor = db.rawQuery("SELECT * FROM " + EventContract.EventEntry.TABLE_NAME, null);

        try {
            int categoriaIndex = cursor.getColumnIndexOrThrow(EventContract.EventEntry.COLUMN_CATEGORIA);
            int dataIndex = cursor.getColumnIndexOrThrow(EventContract.EventEntry.COLUMN_DATA);

            while (cursor.moveToNext()) {
                String categoria = cursor.getString(categoriaIndex);
                String data = cursor.getString(dataIndex);
                Log.d("DatabaseDebug", "Evento: " + categoria + " | Fecha: " + data);
            }
        } catch (IllegalArgumentException e) {
            Log.e("DatabaseDebug", "Error: " + e.getMessage()); // Te dirá qué columna falta
        } finally {
            cursor.close();
        }
    }
    private static File getLocalFile() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File downDir = new File(file, "esdeveniment.xml");
        return downDir;
    }


    public static List<Event> parseEsdevenimentsXML() {
        List<Event> eventList = new ArrayList<>();

        try {
            File file = getLocalFile();

            if (!file.exists() || file.length() == 0) {
                System.out.println("Archivo no encontrado o vacío");
                return eventList;
            }

            FileInputStream fis = new FileInputStream(file);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fis);

            NodeList nodeList = doc.getElementsByTagName("esdeveniment");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                String titol = element.getElementsByTagName("titol").item(0).getTextContent();
                String imatge = element.getElementsByTagName("imatge").item(0).getTextContent();
                String categoria = element.getElementsByTagName("categoria").item(0).getTextContent();
                String data = element.getElementsByTagName("ordre").item(0).getTextContent();
                String nom = element.getElementsByTagName("descripcio").item(0).getTextContent();

                eventList.add(new Event(id, categoria, data, nom, titol, imatge));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventList;
    }

}