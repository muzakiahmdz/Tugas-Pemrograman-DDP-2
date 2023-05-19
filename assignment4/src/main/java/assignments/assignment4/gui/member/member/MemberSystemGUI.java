package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class MemberSystemGUI extends AbstractMemberGUI { //membersystem class
    public static final String KEY = "MEMBER";

    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    public Member getLoggedInMember(){
        return loggedInMember;
    }


    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {//metod buat button
        // TODO
        return new JButton[]{
                new JButton("Saya ingin laundry "),
                new JButton("Lihat Detail Nota Saya")
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
                e -> createNota(),
                e -> showDetailNota(),
        };
    }

    /**
     * Tampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() { //metod liatin nota
        JDialog dialog = new JDialog(MainFrame.getInstance(), "Nota Details", true);

        if (loggedInMember.getNotaList().length != 0) { //validasi nota kosong
            StringBuilder sb = new StringBuilder();
            for (Nota nota : loggedInMember.getNotaList()) { //iterasi nota
                sb.append(nota.toString()).append("\n");
            }
            String message = sb.toString();
            //setting allign dan scroll pane
            JTextArea storyArea = new JTextArea(message);
            storyArea.setEditable(false);
            storyArea.setLineWrap(true);
            storyArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(storyArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Nota Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Belum pernah laundry di CuciCuci. hiks :(", "Nota Details", JOptionPane.INFORMATION_MESSAGE);
            return; //validasi kalo nota kosong
        }
    }


    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    public void createNota() {
    MainFrame.getInstance().navigateTo(CreateNotaGUI.KEY);    }

}


