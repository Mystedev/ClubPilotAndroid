package com.example.clubpilot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static String getUserNameById(int userId) {
        String name = null;
        try (Connection conn = Conection.CONN()) {
            if (conn != null) {
                String query = "SELECT nom FROM Usuari WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    name = rs.getString("nom");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    // Funcio per registrar un usuari
    public static boolean insertUser(String username,String password,String nom,String cognoms,String email) {
        boolean success = false;
        try (Connection conn = Conection.CONN()) {
            if (conn != null) {
                String query = "INSERT INTO Usuari (username,password,nom,cognoms,email) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, nom);
                stmt.setString(4, cognoms);
                stmt.setString(5, email);
                int rows = stmt.executeUpdate();
                success = rows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Funcio per iniciar sessio
    public static boolean loginUser(String username, String password) {
        boolean isValid = false;
        try (Connection conn = Conection.CONN()) {
            if (conn != null) {
                String query = "SELECT password FROM Usuari WHERE username = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    String inputHash = Encriptator.hashPassword(password);

                    // Comparar hash guardado con el hash de la contraseña ingresada
                    isValid = storedHash.equals(inputHash);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

}
