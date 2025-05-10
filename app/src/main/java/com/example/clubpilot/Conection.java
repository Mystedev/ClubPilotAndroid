package com.example.clubpilot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conection {
    private static String database = "clubPilot";
    private static String ip = "webapps.insjoanbrudieu.cat";// Ip per provar des de fora
    private static String ipIns = "192.168.1.150"; // Ip per provar des de dins
    private static String port = "25230";
    private static String user = "clubPilot";
    private static String password = "ABCD!!25230";

    public static Connection CONN(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + database;
            conn = DriverManager.getConnection(connectionString, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
