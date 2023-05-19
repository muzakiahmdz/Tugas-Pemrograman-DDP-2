package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // TODO
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        nameLabel = new JLabel("Masukan nama Anda:"); //set label minta nama dan grid
        mainPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        nameTextField = new JTextField(20);
        mainPanel.add(nameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        phoneLabel = new JLabel("Masukan nomor handphone Anda:"); //set label minta noHP dan grid
        mainPanel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        phoneTextField = new JTextField(20);
        mainPanel.add(phoneTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        passwordLabel = new JLabel("Masukan password Anda:"); //set label minta pw dan grid
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.0;
        registerButton = new JButton("Register"); //set label buton regist dan grid
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        mainPanel.add(registerButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });
        mainPanel.add(backButton, gbc);
}

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        // TODO
    String name = nameTextField.getText();
    String phone = phoneTextField.getText();
    String password = new String(passwordField.getPassword());

    // Validasi field kosong
    if (name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields are required. Please fill in all the fields.", "Registration Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // validasi input angka saja
    if (!phone.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, "Invalid phone number. Please enter digits only.", "Registration Error", JOptionPane.ERROR_MESSAGE);
        phoneTextField.setText("");
        return;
    }

    Member registeredMember = loginManager.register(name, phone, password);
    if (registeredMember == null) { //validasi jika akun kembar
        JOptionPane.showMessageDialog(this, "Registration failed. Member already exists.", "Registration Error", JOptionPane.ERROR_MESSAGE);
    } else { //akun berhasil
        JOptionPane.showMessageDialog(this, "Berhasil membuat user dengan  ID " + registeredMember.getId(), "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    nameTextField.setText("");
    phoneTextField.setText("");
    passwordField.setText("");


    }
}
