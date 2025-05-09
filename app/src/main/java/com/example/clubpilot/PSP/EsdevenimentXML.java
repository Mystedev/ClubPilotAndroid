package com.example.clubpilot.PSP;

import android.content.Context;
import android.os.Environment;

import com.example.clubpilot.Fan.CardNew;
import com.example.clubpilot.Fan.News;
import com.example.clubpilot.Player.Event;

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
                boolean download = ftpClient.retrieveFile("/htdocs/esdeveniment.xml", fos);

                if (download) {
                    System.out.println("File downloaded successfully!");
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

                String categoria = element.getElementsByTagName("categoria").item(0).getTextContent();
                String data = element.getElementsByTagName("ordre").item(0).getTextContent();
                String nom = element.getElementsByTagName("descripcio").item(0).getTextContent();

                eventList.add(new Event(categoria, data, nom));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventList;
    }

}