package com.example.clubpilot.PSP;

import org.apache.commons.net.ftp.FTPClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

public class DownloadFiles {
    private static void download(){
        // get an ftpClient object
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;

        try {
            // pass directory path on server to connect
            ftpClient.connect("ftpupload.net",21);

            // pass username and password, returned true if authentication is
            // successful
            boolean login = ftpClient.login("if0_38540833", "yyFRKtFk8nhx9");

            if (login) {
                System.out.println("Connection established...");

                fos = new FileOutputStream("C:\\Intel\\index.php");
                boolean download = ftpClient.retrieveFile("/htdocs/index.php", fos);
                if (download) {
                    System.out.println("File downloaded successfully !");
                } else {
                    System.out.println("Error in downloading file !");
                }

                // logout the user, returned true if logout successfully
                boolean logout = ftpClient.logout();
                if (logout) {
                    System.out.println("Connection close...");
                }
            } else {
                System.out.println("Connection fail...");
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
