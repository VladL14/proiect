package org.example;

/**
 * @author Lunculescu Vlad
 */

import java.sql.*;

/**
 * Clasa Database care face conexiunea proiectului cu baza de date, login si register
 */

public class Database {

    /**
     * Metodă pentru obținerea unei conexiuni la baza de date.
     */

    private Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Optional for modern drivers
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            return conn;
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }
        return null;
    }


    /**
     * Autentificarea utilizatorului.
     * @param email
     * @param parola
     * @return
     */
    public User login(String email, String parola) {
        String sql = "SELECT * FROM utilizatori WHERE email = ? AND parola = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                //System.out.println("Eroare: Conexiunea este null.");
                return null;
            }

            pstmt.setString(1, email);
            pstmt.setString(2, parola);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int rotiri = rs.getInt("rotiri");
                //System.out.println("Utilizator găsit.");
                return new User(email, parola, rotiri);
            } else {
                //System.out.println("Utilizator negăsit sau inexistent.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Eroare la autentificare: " + e.getMessage());
            return null;
        }
    }

    /**
     * Înregistrarea unui utilizator nou.
     * @param email
     * @param parola
     * @return
     */
    public boolean register(String email, String parola) {
        String sql = "INSERT INTO utilizatori (email, parola, rotiri) VALUES (?, ?, ?)";
        int rotiriInitiale = email.endsWith("@e-uvt.ro") ? 20 : 15;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                //System.out.println("Eroare: Conexiunea este null.");
                return false;
            }

            pstmt.setString(1, email);
            pstmt.setString(2, parola);
            pstmt.setInt(3, rotiriInitiale);
            pstmt.executeUpdate();
            //System.out.println("Utilizatorul a fost înregistrat cu succes.");
            return true;
        } catch (Exception e) {
            System.out.println("Eroare la înregistrare: " + e.getMessage());
            return false;
        }
    }
}







