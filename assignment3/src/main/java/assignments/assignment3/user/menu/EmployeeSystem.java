package assignments.assignment3.user.menu;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Employee;
import assignments.assignment3.user.Member;

import static assignments.assignment3.nota.NotaManager.*;

public class EmployeeSystem extends SystemCLI {

    /**
     * Membuat object baru EmployeeSystem dan mendaftarkan Employee pada CuciCuci
     */
    public EmployeeSystem() { //data login admin
        memberList = new Member[]{
                new Employee("Dek Depe", "akuDDP"),
                new Employee("Depram", "musiktualembut"),
                new Employee("Lita Duo", "gitCommitPush"),
                new Employee("Ivan Hoshimachi", "SuamiSahSuisei"),
        };
    }

    /**
     * Memproses pilihan dari employee yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */

    protected boolean processChoice(int choice) { //proses pilihan
        boolean logout = false;
        switch (choice) {
            case 1: //nyuci
                nyuciTime();
                break;

            case 2: //display nota
                displayAllNotas();
                break;

            case 3: //logout
                logout = true;
                break;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Employee.
     */

    protected void displaySpecificMenu() { //menu pilihan
        System.out.println("1. It's nyuci time");
        System.out.println("2. Display List Nota");
        System.out.println("3. Logout");
    }

    /**
     * Menampilkan pesan dari semua nota yang ada.
     */
    private void nyuciTime() { //menyuci laundry dan memnerikan status
        System.out.println("Stand back! " +loginMember.getNama()+ " beginning to nyuci!");
        for (Nota nota : notaList) {
            System.out.println("Nota " + nota.getIdNotaPribadi() + " : " + getNotaStatus(nota));
        }
    }

    /**
     * Menampilkan status dari semua nota.
     */
    private void displayAllNotas() { //display nota
        for (Nota nota : notaList) {
            System.out.println("Nota " + nota.getIdNotaPribadi() + " : " + (nota.getIsDone() ? "Sudah selesai." : "Belum selesai."));
        }
    }

    /**
     * Mengembalikan pesan status dari sebuah nota.
     *
     * @param nota -> nota yang ingin ditampilkan pesannya.
     * @return pesan status nota.
     */


    /**
     * Mengembalikan status kerja dari sebuah nota.
     *
     * @param nota -> nota yang ingin diketahui status kerjanya.
     * @return status kerja nota.
     */
    private String getNotaStatus(Nota nota) {//memberikan status nota
        LaundryService[] services = nota.getServices();
        if (services != null) {
            for (int i = 0; i < services.length; i++) {
                if (!services[i].isDone()) {
                    if (i == services.length-1){
                        nota.setIsDone();
                    }
                    return services[i].doWork();
                }
            }
        }
        return "Sudah selesai. ";
    }
}
