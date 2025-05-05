package com.example.clubpilot;

import java.security.MessageDigest;
import java.util.Base64;

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
            // Convertir a Base64 en lugar de hexadecimal
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar hash", e);
        }
    }
}
