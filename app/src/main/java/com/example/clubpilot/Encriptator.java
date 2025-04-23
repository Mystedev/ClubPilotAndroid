package com.example.clubpilot;

import java.security.MessageDigest;

public class Encriptator {
    /*
    *   Cuando el usuario crea una contraseña, la hasheás con SHA-256 y guardás el resultado.

        Cuando quiere iniciar sesión, tomás la contraseña ingresada, la hasheás igual, y comparás con el hash guardado.

        Si coinciden, la contraseña es correcta ✅
    */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());

            // Convertimos el hash a una cadena hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar hash", e);
        }
    }
}
