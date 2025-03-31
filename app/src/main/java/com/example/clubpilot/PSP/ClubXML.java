package com.example.clubpilot.PSP;

import android.os.Environment;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

public class ClubXML implements Runnable{
    public static boolean connected = false;
    private static String hostname = "ftpupload.net";
    private static int port = 21;
    private static String username = "if0_38540833";
    private static String password = "yyFRKtFk8nhx9";

    @Override
    public void run() {
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;

        try {
            ftpClient.connect(hostname, port);
            boolean login = ftpClient.login(username, password);

            if (login) {
                connected = true;
                System.out.println("Connection established...");

                // Obtener la ruta de la carpeta de descargas
                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(downloadsDir, "club.xml");

                fos = new FileOutputStream(file);
                boolean download = ftpClient.retrieveFile("/htdocs/club.xml", fos);

                if (download) {
                    System.out.println("File downloaded successfully!");
                } else {
                    System.out.println("Error in downloading file!");
                }

                boolean logout = ftpClient.logout();
                if (logout) {
                    System.out.println("Connection closed...");
                }
            } else {
                connected = false;
                System.out.println("Connection failed...");
            }
        } catch (SocketException e) {
            e.printStackTrace();
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
}
