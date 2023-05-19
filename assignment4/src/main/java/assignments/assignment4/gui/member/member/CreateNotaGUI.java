package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel { //class create nota gui
    public static final String KEY = "CREATE_NOTA";
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Set up main panel, Feel free to make any changes
        initGUI();
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        //set up grid
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create components label
        paketLabel = new JLabel("Paket:");
        paketComboBox = new JComboBox<>(new String[]{"Express", "Fast", "Regular"});
        showPaketButton = new JButton("Show Paket");
        beratLabel = new JLabel("Berat (Kg):");
        beratTextField = new JTextField();
        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000/kg)");
        antarCheckBox = new JCheckBox("Tambah Antar Service (2000/4kg pertama, kemudian 500/kg)");
        createNotaButton = new JButton("Buat Nota");
        backButton = new JButton("Kembali");

        // Add components ke panel
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(paketLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(paketComboBox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(showPaketButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(beratLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(beratTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        add(setrikaCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(antarCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(createNotaButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backButton, gbc);

        // action listeners
        showPaketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPaket();
            }
        });

        createNotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNota();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() { //method show packet
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    public void createNota() { //metod create nota
        String paket = (String) paketComboBox.getSelectedItem();
        String beratText = beratTextField.getText().trim();
        boolean isSetrika = setrikaCheckBox.isSelected();
        boolean isAntar = antarCheckBox.isSelected();
        String tanggal = fmt.format(cal.getTime());

        // validasi field berat
        if (beratText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Berat cucian harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // validasi input berat bil. positif
        int berat;
        try {
            berat = Integer.parseInt(beratText);
            if (berat <= 0) {
                JOptionPane.showMessageDialog(this, "Berat cucian harus lebih besar dari 0.", "Error", JOptionPane.ERROR_MESSAGE);
                beratTextField.setText("");
                return;
            }
        } catch (NumberFormatException e) { //catch kalo berat inputnya bukan angka
            JOptionPane.showMessageDialog(this, "Berat cucian harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            beratTextField.setText("");
            return;
        }

        // kalo berat <2
        if (berat < 2) {
            berat = 2;
            JOptionPane.showMessageDialog(this, "Berat cucian kurang dari 2kg akan dihitung menjadi 2kg.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        Member loggedInMember = memberSystemGUI.getLoggedInMember();
        Nota nota = new Nota(loggedInMember, berat, paket, tanggal);
        if (isAntar) { //jika mau diantar
            LaundryService antarService = new AntarService();
            nota.addService(antarService);
        }
        if (isSetrika) { //jika mau disetrika
            LaundryService setrikaService = new SetrikaService();
            nota.addService(setrikaService);
        }
        loggedInMember.addNota(nota);
        NotaManager.addNota(nota);
        JOptionPane.showMessageDialog(this, "Nota berhasil dibuat.", "Sukses", JOptionPane.INFORMATION_MESSAGE); //done message

        // Reset fields
        paketComboBox.setSelectedIndex(0);
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
    }



    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {//handle back method
        // TODO
        MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
        // Reset fields
        paketComboBox.setSelectedIndex(0);
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
    }
}
