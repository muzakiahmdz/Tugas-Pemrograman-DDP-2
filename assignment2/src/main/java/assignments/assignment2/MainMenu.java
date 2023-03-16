package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.*;
import java.time.LocalDate; //import time
import java.time.format.DateTimeFormatter; //import formatting time


import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList = new ArrayList<Nota>();
    private static ArrayList<Member> memberList = new ArrayList<Member>();


    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    private static void handleGenerateUser() {//method generate user
        // TODO: handle generate user
        System.out.println("Masukkan nama Anda: "); //meminta nama
        String nama = "";
        while(true) {//while loop validasi nama
            String namaInput = input.nextLine();
            if (namaInput.matches("[ a-zA-Z]+")) {// asumsi jika nama mengandung selain alfabet akan diminta ulang
                nama = namaInput;
                break;
            } else {
                System.out.print("Nama mengandung karakter selain alphabet, silahkan input nama kembali");
                continue;
            }
        }
        System.out.println("Masukkan nomor handphone Anda: "); //meminta no hp
        String nomorHPInput = input.nextLine();
        while (true) { //loop formatting hp untuk menerima digit saja
            if (!nomorHPInput.matches("[0-9]+")) {
                System.out.println("Field nomor hp hanya menerima digit.");
                nomorHPInput = input.nextLine();
            } else {
                break;
            }
        }
        boolean flag = true;
        for(Member member : memberList) {//looping validasi id yang sudah ada
            if (generateId(nama,nomorHPInput).equals(member.getId())){
                System.out.println("Member dengan nama " + nama + " dan nomor hp " + nomorHPInput + " sudah ada!\n");
                flag = false;
                break;
            }
        }
        if (flag) {
            String userID = generateId(nama, nomorHPInput); //memanggil method generateid untuk mendapatkan userID baru
            addMember(memberList, nama, nomorHPInput);// Menambahkan member baru ke dalam list memberList
            System.out.print("Berhasil membuat member dengan ID " + userID +"!\n");
        }
    }

    private static void handleGenerateNota() {//methode generate nota
        // TODO: handle ambil cucian
        System.out.println("Masukkan ID Member: "); //meminta ID Member
        String idMember ="";
        Member objMember = null;
        boolean param = true;
        String id = input.nextLine();
        for (Member member : memberList) {
            if (member.getId().equals(id)) {
                idMember = id;
                objMember = member;
                param = false;
                break; // ID sudah terdaftar dalam list
            }
        }
        if (param) System.out.println("Member dengan ID " + id + " tidak ditemukan!" ); // ID belum terdaftar dalam list
        else {

            // minta input paket cuci dan berat cucian
            System.out.println("Masukkan paket laundry: "); //meminta jenis paket
            String paket = input.nextLine().toLowerCase();
            int harga = 0;
            int lamaHariPaket = 0;
            while (true) { //loop validasi paket dan setting harga
                if (paket.equals("express")) {
                    harga += 12000;
                    lamaHariPaket += 1;
                    break;
                } else if (paket.equals("fast")) {
                    harga += 10000;
                    lamaHariPaket += 2;
                    break;
                } else if (paket.equals("reguler")) {
                    harga += 7000;
                    lamaHariPaket += 3;
                    break;
                } else if (paket.equals("?")) {
                    showPaket();
                    System.out.println("Masukkan paket laundry: ");
                    paket = input.nextLine().toLowerCase();
                } else {
                    System.out.printf("Paket %s tidak diketahui\n", paket);
                    System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                    System.out.println("Masukkan paket laundry: ");
                    paket = input.nextLine().toLowerCase();
                }
            }
            System.out.println("Masukkan berat cucian Anda [Kg]: "); //meminta berat cucian
            int beratCucian = 0;
            try { //loop validasi berat cucian
                while (true) {
                    beratCucian = input.nextInt();
                    if (beratCucian <= 0) {
                        System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                        beratCucian = input.nextInt();
                    } else if (beratCucian < 2) {
                        beratCucian = 2;
                        System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg\n ");
                        break;
                    } else {
                        break;
                    }
                }
            } catch (NumberFormatException e) { //exception
                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.\n");

            }
            System.out.println("Berhasil menambahkan nota!");
            //formatting jam
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate tanggalTerima = LocalDate.parse(fmt.format(cal.getTime()), formatter);
            LocalDate tanggalSelesai = tanggalTerima.plusDays(lamaHariPaket);
            Nota notaBaru = new Nota(objMember, paket, beratCucian, tanggalTerima.format(formatter).toString()); //membuat nota baru dengan param
            notaList.add(notaBaru);
            notaBaru.setSisaHariPengerjaan(lamaHariPaket);
            System.out.printf("[ID Nota = %d]\n", notaBaru.getIdNotaPribadi());
            int totalHarga = beratCucian * harga;
            String pesanUser = String.format("ID    : %s\nPaket : %s\nHarga :\n", idMember, paket);
            objMember.setBonusCounter(objMember.getBonusCounter() + 1);

            if (objMember.getBonusCounter() == 3) {//counter diskon
                pesanUser += String.format("%d kg x %d = %d = %d (Discount member 50%%!!!)\n", beratCucian, harga, totalHarga, totalHarga / 2);
                objMember.setBonusCounter(0); //setter bonus counter ke 0
            } else {
                pesanUser += String.format("%d kg x %d = %d\n", beratCucian, harga, totalHarga);
            }
            pesanUser += String.format(
                    "Tanggal Terima  : %s\n" +
                            "Tanggal Selesai : %s\n" +
                            "Status          : Belum bisa diambil :(",
                    (tanggalTerima.format(formatter)).toString(), (tanggalSelesai.format(formatter)).toString());
            System.out.println(pesanUser);
        }
    }

    private static void handleListNota() {//methode listnota
        // TODO: handle list semua nota pada sistem
        if (notaList.size() == 0){
            System.out.println("Terdaftar 0 nota dalam sistem.");}
        else {
            System.out.println("Terdaftar " + notaList.size() + " nota dalam sistem.");
            for (Nota nota : notaList) {
                System.out.println("- ["+ nota.getIdNotaPribadi() +"] Status  : " + nota.getStatus());
            }
        }
    }

    private static void handleListUser() {//handle list user
        // TODO: handle list semua user pada sistem
        if (memberList.size() == 0){
            System.out.println("Terdaftar 0 user dalam sistem.");}
        else{
        System.out.println("Terdaftar " + memberList.size() + " member dalam sistem.");
        for (Member member : memberList) {
            System.out.println("- " + member.getId() + " : " + member.getNama());}
        }
    }

    private static void handleAmbilCucian() {//methode ambil cucian
        // TODO: handle ambil cucian
        System.out.println("Masukkan ID nota yang akan diambil: "); // meminta ID nota
        while (true) {
            String idNota = input.nextLine();
            if (!idNota.matches("\\d+")) { // check if idNota is not a number
                System.out.print("ID nota harus berbentuk angka!\n");
                continue;
            }
            boolean isNotaFound = false;
            for (Nota nota : notaList) {//looping cek id nota
                if (idNota.equals(Integer.toString(nota.getIdNotaPribadi()))) {
                    isNotaFound = true;
                    if (nota.getIsReady()) {
                        System.out.printf("Nota dengan ID %s berhasil diambil!\n", idNota);
                        notaList.remove(nota); // remove the nota from the list
                    } else {
                        System.out.printf("Nota dengan ID %s gagal diambil!\n", idNota);
                    }
                    break;
                }
            }
            if (!isNotaFound) {
                System.out.println("Nota dengan ID " + idNota + " tidak ditemukan!\n");
                break;
            } else {
                break; // break the loop when the nota is found and handled
            }
        }
    }


    private static void handleNextDay() {//methode nextday
        // TODO: handle ganti hari
        cal.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println("Dek Depe tidur hari ini... zzz...");
        if (notaList != null) {
            for (Nota nota : notaList) {//looping kesiapan ambil cucian
                nota.setSisaHariPengerjaan(nota.getSisaHariPengerjaan()-1);
                if(nota.getSisaHariPengerjaan() <= 0){
                    nota.setIsReady();
                }
                if (nota.getIsReady()) {
                    System.out.println("Laundry dengan nota ID " + nota.getIdNotaPribadi() + " sudah dapat diambil!"); // add missing semicolon
                }
            }
        }
        System.out.println("Selamat pagi dunia!\n" +
                "Dek Depe: It's CuciCuci Time.");
    }


    private static void printMenu() {//methode print menu
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }
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
        return id;
    }
    public static void addMember(ArrayList<Member> memberList, String nama, String noHp) {//method add member
        Member member = new Member(nama, noHp);
        memberList.add(member);
    }
    private static void showPaket() {//methode showpaket
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

}
