package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.user.menu.EmployeeSystem;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }
    @Override
    public String getPageName(){
        return KEY;
    }
    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        // TODO
        //,method bikin button
        return new JButton[]{
                new JButton("It's nyuci time"),
                new JButton("Display List Nota")
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() { //method display nota
        StringBuilder sb = new StringBuilder();
        if (NotaManager.notaList.length == 0) { //validasi nota kosong
            JOptionPane.showMessageDialog(null, "Belum ada nota", "List Nota", JOptionPane.ERROR_MESSAGE);
            return;
        }
            for (Nota nota : NotaManager.notaList) {//iterasi isi nota
                sb.append(nota.getNotaStatus()).append("\n");

        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Daftar Nota", JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() { //method cuci
        boolean hasClothesToWash = false;
        StringBuilder sb = new StringBuilder();
        for (Nota nota : NotaManager.notaList) {//iterasi nota  untuk cuci
            String result = nota.kerjakan();
            if (!result.isEmpty()) { //validasi cuci
                hasClothesToWash = true;
                sb.append(result).append("\n");
            }
        }
        if (!hasClothesToWash) { //validasi kalo nota kosong
            JOptionPane.showMessageDialog(null, "Nothing to cuci here.", "Nyuci results ", JOptionPane.ERROR_MESSAGE);
        } else {//message
            String messagePopUp = "Stand back! " + loggedInMember.getNama() + " beginning to cuci!";
            JOptionPane.showMessageDialog(null, messagePopUp, "Nyuci Time!", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, sb.toString(), "Cuci", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
