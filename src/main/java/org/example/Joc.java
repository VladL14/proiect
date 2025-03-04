package org.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clasa Joc este practic logica jocului
 */
public class Joc {
    ArrayList<Fruct> fructe;
    Fruct[][] matrice;
    Random rand;
    String email;

    /**
     * Metoda Joc creeaza obiecte de tip fruct
     * @param email
     */
    public Joc(String email) {
        this.email = email;
        fructe = new ArrayList<>();
        fructe.add(new Fruct(1, "Cirese", 10, "src/main/java/Imagini/shining-crown-cherry.png"));
        fructe.add(new Fruct(2, "Struguri", 16, "src/main/java/Imagini/shining-crown-grapes.png"));
        fructe.add(new Fruct(3, "Lamaie", 6, "src/main/java/Imagini/shining-crown-lemons.png"));
        fructe.add(new Fruct(4, "Portocala", 8, "src/main/java/Imagini/shining-crown-orange.png"));
        fructe.add(new Fruct(5, "Pepene", 20, "src/main/java/Imagini/shining-crown-simbol-pepene.png"));
        fructe.add(new Fruct(6,"Prune", 6 , "src/main/java/Imagini/shining-crown-prune.png"));

        rand = new Random();
        matrice = new Fruct[3][5];
    }

    /**
     * Genereaza matricea aleatorie cu obiectele respective
     */
    public void genereazaMatrice() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                matrice[i][j] = fructe.get(rand.nextInt(fructe.size()));
            }
        }
    }

    /**
     * getRotiri1 extrage numarul de rotiri din baza de date
     * @return rotiri
     */
    public int getRotiri1() {
        String sql = "select rotiri from utilizatori where email = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("rotiri");
            }


        } catch (Exception e) {
            System.out.println("Numarul de rotiri nu a putut fi extras" + e.getMessage());

        }
        return 0;
    }

    /**
     * verificaCastig verifica combinatiile castigatoare
     * @return rotiriCastigate
     */
    public int verificaCastig() {
    int rotiriCastigate = 0;
    genereazaMatrice();
            if (matrice[0][0].getId() == matrice[0][1].getId() && matrice[0][1].getId() == matrice[0][2].getId() && matrice[0][2].getId() != matrice[0][3].getId()) {
                rotiriCastigate += matrice[0][0].getValoare() / 2;
            }
            if (matrice[1][0].getId() == matrice[1][1].getId() && matrice[1][1].getId() == matrice[1][2].getId() && matrice[1][2].getId() != matrice[1][3].getId()) {
                rotiriCastigate += matrice[1][0].getValoare() / 2;
            }
            if (matrice[2][0].getId() == matrice[2][1].getId() && matrice[2][1].getId() == matrice[2][2].getId() && matrice[2][2].getId() != matrice[2][3].getId()) {
                rotiriCastigate += matrice[2][0].getValoare() / 2;
            }

            if (matrice[0][0].getId() == matrice[0][1].getId() && matrice[0][1].getId() == matrice[0][2].getId() && matrice[0][2].getId() == matrice[0][3].getId() && matrice[0][3].getId() != matrice[0][4].getId()) {
                rotiriCastigate += matrice[0][0].getValoare() ;
            }
            if (matrice[1][0].getId() == matrice[1][1].getId() && matrice[1][1].getId() == matrice[1][2].getId() && matrice[1][2].getId() == matrice[1][3].getId() && matrice[1][3].getId() != matrice[1][4].getId()) {
                rotiriCastigate += matrice[1][0].getValoare() ;
            }
            if (matrice[2][0].getId() == matrice[2][1].getId() && matrice[2][1].getId() == matrice[2][2].getId() && matrice[2][2].getId() == matrice[2][3].getId() && matrice[2][3].getId() != matrice[2][4].getId()) {
                rotiriCastigate += matrice[2][0].getValoare() ;
            }

            if (matrice[0][0].getId() == matrice[0][1].getId() && matrice[0][1].getId() == matrice[0][2].getId() && matrice[0][2].getId() == matrice[0][3].getId() && matrice[0][3].getId() == matrice[0][4].getId()) {
                rotiriCastigate += matrice[0][0].getValoare() * 2 ;
            }
            if (matrice[1][0].getId() == matrice[1][1].getId() && matrice[1][1].getId() == matrice[1][2].getId() && matrice[1][2].getId() == matrice[1][3].getId() && matrice[1][3].getId() == matrice[1][4].getId()) {
                rotiriCastigate += matrice[1][0].getValoare() * 2 ;
            }
            if (matrice[2][0].getId() == matrice[2][1].getId() && matrice[2][1].getId() == matrice[2][2].getId() && matrice[2][2].getId() == matrice[2][3].getId() && matrice[2][3].getId() == matrice[2][4].getId()) {
                rotiriCastigate += matrice[2][0].getValoare() * 2 ;
            }
                if (matrice[0][0].getId() == matrice[1][1].getId() && matrice[1][1].getId() == matrice[2][2].getId() && matrice[2][2].getId() != matrice[1][3].getId()|| matrice[2][0].getId() == matrice[1][1].getId() && matrice[1][1].getId() == matrice[0][2].getId() && matrice[0][2].getId() != matrice[1][3].getId()) {
                    rotiriCastigate += matrice[1][1].getValoare() / 2;
                }
                if ((matrice[0][0].getId() == matrice[1][1].getId() && matrice[1][1].getId() == matrice[2][2].getId() && matrice[2][2].getId() == matrice[1][3].getId() && matrice[1][3].getId() != matrice[0][4].getId()) || (matrice[2][0].getId() == matrice[1][1].getId() && matrice[1][1].getId() == matrice[0][2].getId() && matrice[0][2].getId() == matrice[1][3].getId() && matrice[1][3].getId() != matrice[2][4].getId())) {
                    rotiriCastigate += matrice[1][1].getValoare() ;
                }
                if ((matrice[0][0].getId() == matrice[1][1].getId() && matrice[1][1].getId() == matrice[2][2].getId() && matrice[2][2].getId() == matrice[1][3].getId() && matrice[1][3].getId() == matrice[0][4].getId()) || (matrice[2][0].getId() == matrice[1][1].getId() && matrice[1][1].getId() == matrice[0][2].getId() && matrice[0][2].getId() == matrice[1][3].getId() && matrice[1][3].getId() == matrice[2][4].getId())) {
                    rotiriCastigate += matrice[1][1].getValoare() * 2 ;

                }


        return rotiriCastigate;
    }

    /**
     * updateRotiri updateaza numarul de rotiri in baza de date
     * @param rotiriNoi
     */
    public void updateRotiri( int rotiriNoi ){
        String sql = "update utilizatori set rotiri = ? where email = ?";
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,rotiriNoi);
            ps.setString(2,email);
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println("Numarul de rotiri nu a putut fi actualizat" + e.getMessage());
        }
    }

    /**
     * metoda Joc verifica daca ai castigat si updateaza rotirile
     * @return castig
     */
    public int joaca() {
        int rotiri = getRotiri1();
        if (rotiri > 0) {
            genereazaMatrice();
            int castig = verificaCastig();
            updateRotiri(rotiri - 1 + castig);

//            if (castig > 0) {
//                System.out.println("Felicitari, ai castigat " + castig + " puncte");
//            } else {
//                System.out.println("Rotire necastigatoare!");
//            }
            return castig;
        } else {
            //System.out.println("Nu mai ai rotiri disponibile");
            return -1;
        }
    }

    public ArrayList<Fruct> getFructe() {
        return fructe;
    }
}
