package org.example;

/**
 * @author Lunculescu Vlad
 * Clasa User
 */
public class User {
    String email;
    String parola;
    int rotiri;

    /**
     * Constructor
     * @param email
     * @param parola
     * @param rotiri
     */
    public User(String email, String parola, int rotiri) {
        this.email = email;
        this.parola = parola;
        this.rotiri = rotiri;
    }

    public String getEmail() {
        return email;
    }
    public String getParola() {
        return parola;
    }

    /**
     *
     * @return rotiri
     */
    public int getRotiri() {
        return rotiri;
    }


}
