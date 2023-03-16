package assignments.assignment1;

import com.sun.source.doctree.SinceTree;

import java.util.Scanner;
import java.time.LocalDate; //import time
import java.time.format.DateTimeFormatter; //import formatting time
public class NotaGenerator {
    private static final Scanner scanner = new Scanner(System.in);

        /**
         * Method main, program utama kalian berjalan disini.
         */
        public static void main(String[] args){
            // Menampilkan menu pilihan
            while (true) {
            printMenu();
            System.out.print("Pilihan: ");
                String pilihan = scanner.nextLine();
                // Memilih aksi sesuai pilihan
                if (pilihan.equals("1")) {
                    System.out.println("================================\n" +
                            "Masukkan nama Anda: "); //meminta nama
                    String namaInput = scanner.nextLine();
                    System.out.println("Masukkan nomor handphone Anda: "); //meminta no hp
                    String nomorHPInput = scanner.nextLine();
                    while (true) { //loop formatting hp untuk menerima digit saja
                        if (!nomorHPInput.matches("[0-9]+")) {
                            System.out.println("Nomor hp hanya menerima digit.");
                            nomorHPInput = scanner.nextLine();
                        }
                        else {
                            break;
                        }
                    }
                    String userID = generateId(namaInput, nomorHPInput); //memanggil method generateid
                    System.out.printf("ID Anda : %s\n", userID); //print sesuai format


                } else if (pilihan.equals("2")) { //aksi dari pilihan 2
                    System.out.println("================================\n" +
                            "Masukkan nama Anda: "); //meminta nama
                    String nama = scanner.nextLine();
                    System.out.println("Masukkan nomor handphone Anda: "); //meminta no hp
                    String nomorHp = scanner.nextLine();
                    while (true) { //loop validasi no hp
                        if (!nomorHp.matches("[0-9]+")) {
                            System.out.println("Nomor hp hanya menerima digit.");
                            nomorHp = scanner.nextLine();
                        }
                        else {
                            break;
                        }
                    }
                    System.out.println("Masukkan tanggal terima: "); //meminta tgl terima
                    String tanggalTerima = scanner.nextLine();
                    System.out.println("Masukkan paket laundry: "); //meminta jenis paket
                    String paket = scanner.nextLine().toLowerCase();
                    // minta input paket cuci dan berat cucian
                    int harga = 0;
                    while (true) { //loop validasi paket dan setting harga
                        if (paket.equals("express")) {
                            harga += 12000;
                            break;
                        } else if (paket.equals("fast")) {
                            harga += 10000;
                            break;
                        } else if (paket.equals("regular")) {
                            harga += 7000;
                            break;
                        } else if (paket.equals("?")){
                            showPaket();
                            System.out.println("Masukkan paket laundry: ");
                            paket = scanner.nextLine().toLowerCase();
                        }
                        else {
                            System.out.printf("Paket %s tidak diketahui\n", paket);
                            System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                            System.out.println("Masukkan paket laundry: ");
                            paket = scanner.nextLine().toLowerCase();
                        }
                    }
                    System.out.println("Masukkan berat cucian Anda [Kg]: "); //meminta berat cucian
                    int beratCucian = 0;
                    try { //loop validasi berat cucian
                        while (true) {
                            beratCucian = scanner.nextInt();
                            if (beratCucian <= 0) {
                                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                                beratCucian = scanner.nextInt();
                            } else if (beratCucian < 2) {
                                beratCucian = 2;
                                System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg\n ");
                                break;
                            } else {
                                break;
                            }
                        }
                    } catch (NumberFormatException e){ //exception
                        System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.\n");
                        continue;
                    }
                    //print nota laundry dengan format
                    System.out.println("Nota Laundry");
                    System.out.println(generateNota(nama, paket, beratCucian, tanggalTerima));


                } else if (pilihan.equals("0")) {
                    System.out.println("================================\n" +
                            "Terima kasih telah menggunakan NotaGenerator!");
                    System.exit(0); // keluar program
                } else {
                    System.out.println("Pilihan tidak diketahui, silakan coba lagi");
                }
            }
        }
    /**
     * Method untuk menampilkan menu di NotaGenerator.
     */
    private static void printMenu() { //method print menu
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    /**
     * Method untuk menampilkan paket.
     */
    private static void showPaket() {//methode showpaket
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /**
     * Method untuk membuat ID dari nama dan nomor handphone.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing
     *
     * @return String ID anggota dengan format [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String namaInput, String nomorHpInput){ //method generate id
        String nama = namaInput.toUpperCase().split(" ")[0];
        String nomorHp = nomorHpInput.replaceAll("[^0-9]", "");
        String id = nama + "-" + nomorHp;
        // hitung checksum
        int checksum = 0;
        for (char c : id.toCharArray()) {
            if (Character.isLetter(c)) {
                checksum += c - 'A' + 1;
            } else if (Character.isDigit(c)) {
                checksum += c - '0';
            } else {
                checksum += 7;
            }

        }
        checksum %= 100;
        // tambahkan digit 0 jika checksum hanya satu digit
        if (checksum < 10) {
            id += "-0" + checksum;
        } else {
            id += "-" + checksum;
        }
        // tampilkan ID nota
        return id;
    }



    /**
         *
         * Method untuk membuat Nota.
         * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing.
         *
         * @return string nota dengan format di bawah:
         *         <p>ID    : [id]
         *         <p>Paket : [paket]
         *         <p>Harga :
         *         <p>[berat] kg x [hargaPaketPerKg] = [totalHarga]
         *         <p>Tanggal Terima  : [tanggalTerima]
         *         <p>Tanggal Selesai : [tanggalTerima + LamaHariPaket]
         */

        public static String generateNota(String id, String paket, int berat, String tanggalTerima) { //method generate nota
            int harga = 0;
            int lamaHariPaket = 0 ;
            while (true) {//loop validasi jenis paket
                if (paket.equals("express")) {
                    harga += 12000;
                    lamaHariPaket += 1;
                    break;
                } else if (paket.equals("fast")) {
                    harga += 10000;
                    lamaHariPaket += 2;
                    break;
                } else if (paket.equals("regular")) {
                    harga += 7000;
                    lamaHariPaket += 3;
                    break;
                } else { //action jika input ?
                    System.out.println("Paket hemat tidak diketahui");
                    System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                    System.out.print("Masukkan paket laundry: ");
                    paket = scanner.nextLine().toLowerCase();
                    if (paket.equals("?")) {
                        showPaket();
                    }
                }
            }
// formatting jam
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dateTanggalTerima = LocalDate.parse(tanggalTerima, formatter);
            LocalDate tanggalSelesai = dateTanggalTerima.plusDays(lamaHariPaket);
            int totalHarga = berat * harga;

            return String.format(
                            "ID    : %s\n" +
                            "Paket : %s\n" +
                            "Harga :\n" +
                            "%d kg x %d = %d\n" +
                            "Tanggal Terima  : %s\n" +
                            "Tanggal Selesai : %s",
                    id, paket, berat, harga, totalHarga, (dateTanggalTerima.format(formatter)).toString(), (tanggalSelesai.format(formatter)).toString());
        }

}

