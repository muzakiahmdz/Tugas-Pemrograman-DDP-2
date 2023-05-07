package assignments.assignment3.user.menu;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import static assignments.assignment3.nota.NotaManager.*;

public class MemberSystem extends SystemCLI {

    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {//switch case proses pilihan
        boolean logout = false;
        switch (choice) {
            case 1:
                // minta in paket cuci dan data cucian
                String tanggal = fmt.format(cal.getTime());
                showPaket(); //show paket
                System.out.println("Masukkan paket laundry: "); //meminta jenis paket
                String paket = in.nextLine().toLowerCase();
                System.out.println("Masukkan berat cucian Anda [Kg]: "); //meminta berat cucian
                int beratCucian = in.nextInt();
                in.nextLine();
                Nota notaMember = new Nota(loginMember, beratCucian, paket, tanggal);
                System.out.print("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?\n" +
                        "Hanya tambah 1000 / kg :0\n" +
                        "[Ketik x untuk tidak mau]: ");
                String cucianDisetrika = in.nextLine().toLowerCase();
                if (!cucianDisetrika.equals("x")) {
                    LaundryService mauSetrika = new SetrikaService();
                    notaMember.addService(mauSetrika);
                }
                System.out.print("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!\n" +
                        "Cuma 2000 / 4kg, kemudian 500 / kg\n" +
                        "[Ketik x untuk tidak mau]: ");
                String cucianDiantar = in.nextLine().toLowerCase();
                if (!cucianDiantar.equals("x")) {
                    LaundryService mauDiantar = new AntarService();
                    notaMember.addService(mauDiantar);
                }
                loginMember.addNota(notaMember);
                NotaManager.addNota(notaMember);
                System.out.println("Nota berhasil dibuat!\n");
                break;

            case 2: //display nota list
                for(int i = 0 ; i < loginMember.getNotaList().length; i++){
                    System.out.print(loginMember.getNotaList()[i]);
                }

                break;

            case 3: //logout acc
                logout = true;
                break;
        }
        return logout;
    }


    /**
     * Displays specific menu untuk Member biasa.
     */

    protected void displaySpecificMenu() { //display specific menu
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) { //masukin member ke list
            Member[] temp = new Member[memberList.length+1];
            for(int i = 0; i < memberList.length; i++){
                temp[i] = memberList[i];
            }
            temp[temp.length-1] = member;
            memberList = temp;
    }
    private static void showPaket() {//methode showpaket
            System.out.println("+-------------Paket-------------+");
            System.out.println("| Express | 1 Hari | 12000 / Kg |");
            System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
            System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
            System.out.println("+-------------------------------+");
        }
}