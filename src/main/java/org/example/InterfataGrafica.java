package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import org.example.Database;

/**
 * @author Lunculescu Vlad
 * Clasa InterfataGrafica care este main JFrame ul aplicatiei
 */
public class InterfataGrafica {
    private App app;

    /**
     * metoda InterfataGrafica porneste aplicatia si afiseaza frame ul de login
     */
    public InterfataGrafica() {
        app = new App();
        showLoginScreen();
    }

    /**
     * showLoginScreen creaza JFRame ul de Login
     */
    private void showLoginScreen() {
        JFrame loginFrame = new JFrame("Autentificare");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel emailLabel = new JLabel("Email:");
        JLabel parolaLabel = new JLabel("Parola:");

        JTextField emailField = new JTextField();
        JPasswordField parolaField = new JPasswordField();
        JButton loginButton = new JButton("Autentificare");
        JButton registerButton = new JButton("Inregistrare");

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(parolaLabel);
        panel.add(parolaField);
        panel.add(loginButton);
        panel.add(registerButton);

        loginFrame.add(panel);
        loginFrame.setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String parola = new String(parolaField.getPassword());
                Database db = new Database();
                User user = db.login(email, parola);
                if (user != null) {
                    JOptionPane.showMessageDialog(loginFrame, "Autentificare reusita, numar de rotiri disponibile: " + user.getRotiri());
                    loginFrame.dispose();
                    showGameScreen(email);
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Utilizator negasit sau inexistent");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                showRegisterScreen();
            }
        });
    }
    /**
     * showRegisterScreen creaza JFRame ul de Register
     */
    private void showRegisterScreen() {
        JFrame registerFrame = new JFrame("Inregistrare");
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel emailLabel = new JLabel("Email:");
        JLabel parolaLabel = new JLabel("Parola:");

        JTextField emailField = new JTextField();
        JPasswordField parolaField = new JPasswordField();
        JButton registerButton = new JButton("Inregistrare");
        JButton backButton = new JButton("Inapoi");

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(parolaLabel);
        panel.add(parolaField);
        panel.add(registerButton);
        panel.add(backButton);

        registerFrame.add(panel);
        registerFrame.setVisible(true);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String parola = new String(parolaField.getPassword());
                Database db = new Database();
                boolean success = db.register(email,parola);
                if (success) {
                    JOptionPane.showMessageDialog(registerFrame, "Inregistrare reusita, te rugam sa te autentifici");
                    registerFrame.dispose();
                    showLoginScreen();
                } else {
                    JOptionPane.showMessageDialog(registerFrame, "Inregistrare esuata, incearca din nou");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerFrame.dispose();
                showLoginScreen();
            }
        });
    }
    /**
     * showGameScreen creaza JFRame ul jocului principal si afisarea rotirilor
     */
    private void showGameScreen(String email) {
        JFrame gameFrame = new JFrame("Joc Slot Machine");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(800, 600);

        Joc joc = new Joc(email);
        int rotiriInitiale = joc.getRotiri1();


        JLabel rotiriLabel = new JLabel("Rotiri ramase: " + rotiriInitiale);
        JButton playButton = new JButton("Joaca");

        JLabel joacaLabel = new JLabel("Apasati butonul joaca pentru a genera rotiri");
        joacaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        joacaLabel.setForeground(Color.BLUE);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(joacaLabel);

        JPanel matricePanel = new JPanel(new GridLayout(3, 5));
        matricePanel.setVisible(false);
        updateMatrice(matricePanel, joc.matrice);

        JPanel controlPanel = new JPanel();
        controlPanel.add(rotiriLabel);
        controlPanel.add(playButton);

        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(centerPanel, BorderLayout.NORTH);
        gameFrame.add(matricePanel, BorderLayout.CENTER);
        gameFrame.add(controlPanel, BorderLayout.SOUTH);

        gameFrame.setVisible(true);

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int castig = joc.joaca();
                if (castig >= 0) {
                    joacaLabel.setVisible(false);
                    centerPanel.setVisible(false);
                    matricePanel.setVisible(true);
                    int rotiriRamase = joc.getRotiri1();
                    rotiriLabel.setText("Rotiri ramase: " + rotiriRamase);

                    updateMatrice(matricePanel, joc.matrice);
                    gameFrame.revalidate();
                    gameFrame.repaint();

                    if(castig > 0){
                        int optiune = JOptionPane.showConfirmDialog(gameFrame, "Felicitari, ai castigat: " + castig + " rotiri. Vrei sa iti dublezi castigul", "Dublaj", JOptionPane.YES_NO_OPTION);
                        if(optiune == JOptionPane.YES_OPTION){
                            showDublaj(gameFrame, joc, castig, rotiriLabel);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(gameFrame, "Nu mai ai rotiri disponibile!");
                }
            }

        });


    }
    /**
     * showDublaj creaza JFrame ul unui potential dublaj in cazul unui castig
     */
    private void showDublaj(JFrame gameFrame, Joc joc, int castig, JLabel rotiriLabel){
        String[] optiuni = {"Rosu", "Negru"};
        int alegere = JOptionPane.showOptionDialog(gameFrame,"Alege o culoare pentru dublaj: ", "Dublaj", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, optiuni, optiuni[0]);

        Random random = new Random();
        boolean rezultat = random.nextBoolean();

        if((rezultat && alegere == 0) || (!rezultat && alegere == 1)){
            int nouCastig = castig * 2;
            JOptionPane.showMessageDialog(gameFrame, "Felicitari ai castigat: " + nouCastig + " rotiri");
            joc.updateRotiri(joc.getRotiri1() + nouCastig - castig);

        }else{
            JOptionPane.showMessageDialog(gameFrame, "Ai pierdut, rotirile castigate au fost pierdute");
            joc.updateRotiri(joc.getRotiri1() - castig);
        }
    }
    /**
     * updateMatrice schimba simbolurile vechi cu cele noi aferente matricei generate
     */
    private void updateMatrice(JPanel matricePanel, Fruct[][] matrice) {
        matricePanel.removeAll();
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                if (matrice[i][j] != null) {
                    ImageIcon icon = new ImageIcon(matrice[i][j].imagine);
                    JLabel label = new JLabel(icon);
                    matricePanel.add(label);
                } else {
                    matricePanel.add(new JLabel());
                }
            }
        }
    }

    public static void main(String[] args) {
        new InterfataGrafica();
    }
}
