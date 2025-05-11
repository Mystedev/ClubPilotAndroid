package com.example.clubpilot;

import com.example.clubpilot.Fan.NewsData;
import com.example.clubpilot.Player.PlayerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends Encriptator{
    // Funcio per registrar un usuari
    public static int insertUserAndReturnId(String username,String password,String nom,String cognoms,String email) {
        int userId = -1;
        try (Connection conn = Conection.CONN()) {
            if (conn != null) {
                String query = "INSERT INTO usuari (username,password,nom,cognoms,email) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, nom);
                stmt.setString(4, cognoms);
                stmt.setString(5, email);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        userId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public static boolean insertAficionat(int userId){
        boolean success = false;
        try (Connection conn = Conection.CONN()) {
            if (conn != null) {
                String query = "INSERT INTO aficionat (id_usuari, id_club) VALUES (?, 6)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, userId);
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
                String query = "SELECT password FROM usuari WHERE username = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    String inputHash = hashPassword(password);

                    // Comparar hash guardado con el hash de la contraseña ingresada
                    isValid = storedHash.equals(inputHash);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public static String getUserType(String username) {
        final String[] result = {null};

        // Crear un hilo para obtener el id del usuario
        Thread getIdThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (Connection conn = Conection.CONN()) {
                    if (conn != null) {
                        String getIdQuery = "SELECT id FROM usuari WHERE username = ?";
                        PreparedStatement getIdStmt = conn.prepareStatement(getIdQuery);
                        getIdStmt.setString(1, username);
                        ResultSet rs = getIdStmt.executeQuery();

                        if (rs.next()) {
                            int userId = rs.getInt("id");

                            // Ahora que tenemos el id, creamos hilos para las comprobaciones
                            Thread checkAficionatThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try (Connection conn2 = Conection.CONN()) {
                                        if (conn2 != null) {
                                            String checkAficionatQuery = "SELECT id_usuari FROM aficionat WHERE id_usuari = ?";
                                            PreparedStatement aficionatStmt = conn2.prepareStatement(checkAficionatQuery);
                                            aficionatStmt.setInt(1, userId);
                                            ResultSet aficionatRs = aficionatStmt.executeQuery();
                                            if (aficionatRs.next()) {
                                                result[0] = "Aficionado";
                                            }
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            Thread checkJugadorThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try (Connection conn3 = Conection.CONN()) {
                                        if (conn3 != null) {
                                            String checkJugadorQuery = "SELECT id_usuari FROM jugador WHERE id_usuari = ?";
                                            PreparedStatement jugadorStmt = conn3.prepareStatement(checkJugadorQuery);
                                            jugadorStmt.setInt(1, userId);
                                            ResultSet jugadorRs = jugadorStmt.executeQuery();
                                            if (jugadorRs.next()) {
                                                result[0] = "Jugador";
                                            }
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            // Iniciar ambos hilos
                            checkAficionatThread.start();
                            checkJugadorThread.start();

                            // Esperar a que ambos hilos terminen
                            try {
                                checkAficionatThread.join();
                                checkJugadorThread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // Si no se asignó ningún resultado, asignamos "Aficionado"
                            if (result[0] == null) {
                                result[0] = "Unknown";
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // Iniciar el hilo principal
        getIdThread.start();

        // Esperar a que el hilo principal termine
        try {
            getIdThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result[0];
    }

    public static PlayerData getDataPlayer(String idUsuari) {
        final PlayerData[] playerData = {null};
        Thread getDataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (Connection conn = Conection.CONN()) {
                    if (conn != null) {
                        // Consulta SQL para obtener los campos id_usuari, disponibilitat, dorsal, posicio
                        String query = "SELECT id_usuari, disponibilitat, dorsal, posicio FROM jugador WHERE id_usuari = ?";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setString(1, idUsuari);
                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            // Crear un objeto PlayerData con los valores obtenidos
                            String id = rs.getString("id_usuari");
                            String disponibilitat = rs.getString("disponibilitat");
                            String dorsal = rs.getString("dorsal");
                            String posicio = rs.getString("posicio");

                            // Crear y llenar el objeto PlayerData
                            playerData[0] = new PlayerData(id, disponibilitat, dorsal, posicio);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // Iniciar el hilo principal
        getDataThread.start();

        // Esperar a que el hilo principal termine
        try {
            getDataThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return playerData[0];
    }

    public static NewsData getNewsData(String idUsuari) {
        final NewsData[] newsData = {null};
        Thread getDataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (Connection conn = Conection.CONN()) {
                    if (conn != null) {
                        // Consulta SQL para obtener los campos id_usuari, disponibilitat, dorsal, posicio
                        String query = "SELECT id_usuari FROM aficionat WHERE id_usuari = ?";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setString(1, idUsuari);
                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            // Crear un objeto PlayerData con los valores obtenidos
                            String id = rs.getString("id_usuari");

                            // Crear y llenar el objeto PlayerData
                            newsData[0] = new NewsData(id);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // Iniciar el hilo principal
        getDataThread.start();

        // Esperar a que el hilo principal termine
        try {
            getDataThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return newsData[0];
    }

    public static boolean updatePassword(String email, String newPassword) {
        boolean success = false;
        try (Connection conn = Conection.CONN()) {
            if (conn != null) {
                String hashedPassword = hashPassword(newPassword); // Asegúrate de encriptar la nueva contraseña
                String query = "UPDATE usuari SET password = ? WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, hashedPassword);
                stmt.setString(2, email);
                int rows = stmt.executeUpdate();
                success = rows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static int getUserId(String username) {
        int userId = 0;
        try (Connection conn = Conection.CONN()) {
            if (conn != null) {
                String query = "SELECT id FROM usuari WHERE username = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    userId = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }
    public static String getUserNameById(int userId) {
        String name = null;
        try (Connection conn = Conection.CONN()) {
            if (conn != null) {
                String query = "SELECT nom FROM usuari WHERE id = ?";
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

    // Devuelve la lista de IDs de clubes que sigue el usuario
    public static List<Integer> getFollowedClubIds(int userId) {
        List<Integer> clubs = new ArrayList<>();

        try (Connection conn = Conection.CONN()){
            if (conn != null) {
                String sql = "SELECT id_club FROM aficionat WHERE id_usuari = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, userId);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        clubs.add(rs.getInt("id_club"));
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubs;
    }

    // Inserta una fila en aficionat (ignora si ya existe)
    public static void followClub(int userId, int clubId) {
        String sql = "INSERT INTO aficionat (id_usuari, id_club) VALUES (?, ?)";
        try (Connection conn = Conection.CONN()){
             PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, clubId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Elimina la fila de aficionat
    public static void unfollowClub(int userId, int clubId) {
        String sql = "DELETE FROM aficionat WHERE id_usuari = ? AND id_club = ?";
        try (Connection conn = Conection.CONN()){
             PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, clubId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
