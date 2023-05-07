package assignments.assignment3.nota;

import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static assignments.assignment3.nota.NotaManager.notaList;

public class Nota {
    //constructor
    private Member member;
    private static int idCounter = 0;

    private String paket;
    private LaundryService[] services = new LaundryService[] { // List LaundrService yang sudah berisikan CuciService
            // sejak awal
            new CuciService()
    };
    private long baseHarga;
    private int sisaHariPengerjaan;
    private int berat;
    private int id;
    private String tanggalMasuk;
    private boolean isDone = false;
    private boolean isDelayed = false;

    static public int totalNota;
    private int idNotaPribadi;

    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.sisaHariPengerjaan = lamaHariPaket();
        this.tanggalMasuk = tanggal;
        this.idNotaPribadi = idCounter;
        idCounter++;
    }

    public void addService(LaundryService service) { //method add service ke list launydr service
        if (services == null) {
            services = new LaundryService[1];
            services[0] = service;
        } else {
            LaundryService[] newServices = new LaundryService[services.length + 1];
            System.arraycopy(services, 0, newServices, 0, services.length);
            newServices[services.length] = service;
            services = newServices;
        }
    }

    public String kerjakan() { //method untuk melakukan service
        if (services != null) {
            for (LaundryService service : services) {
                service.doWork();
            }
        }
        return "Nota sudah dikerjakan.";
    }

    public void toNextDay() { //skip hari method
        if (this.sisaHariPengerjaan > 0) {
            this.sisaHariPengerjaan--; //decrement sHP
        } else {
            this.sisaHariPengerjaan--;
            boolean allServicesDone = true; //setter bool status servis
            this.isDelayed = true;
            for (LaundryService service : services) {
                if (!service.isDone()) {
                    allServicesDone = false;
                    break;
                }
            }

            if (allServicesDone && this.sisaHariPengerjaan <= 0) {
                setIsDone(); //kesiapan londri
            }
        }
    }

    public long calculateHarga() { //method kalkulasi harga
        long totalHarga = hargaBiasa();
        if (services != null) {
            for (LaundryService service : services) {
                totalHarga += service.getHarga(berat);
            }
        }
        return totalHarga;
    }

    public long hargaBiasa() { //harga normal
        return berat * baseHarga;
    }

    @Override
    public String toString() { //to string method message by format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tanggalTerima = LocalDate.now();
        LocalDate tanggalSelesai = tanggalTerima.plusDays(lamaHariPaket());
        String pesanUser = String.format("[ID Nota = %s]\n", getIdNotaPribadi());
        long totalHarga = calculateHarga();
        pesanUser += String.format("ID : %s\n", member.getId());
        pesanUser += String.format("Paket : %s\n", paket);
        pesanUser += String.format("Harga :\n%d kg x %d = %d\n", berat, baseHarga, hargaBiasa());
        pesanUser += String.format("tanggal terima  : %s\n", tanggalMasuk);
        pesanUser += String.format("tanggal selesai : %s\n", tanggalSelesai.format(formatter));

        // Perhitungan kompensasi
        if (sisaHariPengerjaan < 0 && !isDone) {
            long kompensasi = sisaHariPengerjaan * -1 * 2000;
            totalHarga -= kompensasi;
            pesanUser += String.format("--- SERVICE LIST ---\n");
            for (LaundryService service : services) {
                pesanUser += String.format("-%s @ Rp.%d\n", service.getServiceName(), service.getHarga(berat));
            }
            pesanUser += String.format("Harga Akhir: %d Ada kompensasi keterlambatan %d * 2000 hari\n", totalHarga,
                    sisaHariPengerjaan * -1);
        }
        else if(sisaHariPengerjaan < 0 && isDone) {
            long kompensasi = sisaHariPengerjaan * -1 * 2000;
            totalHarga -= kompensasi;
            pesanUser += String.format("--- SERVICE LIST ---\n");
            for (LaundryService service : services) {
                pesanUser += String.format("-%s @ Rp.%d\n", service.getServiceName(), service.getHarga(berat));
            }
            pesanUser += String.format("Harga Akhir: %d Ada kompensasi keterlambatan %d * 2000 hari\n", totalHarga,
                    sisaHariPengerjaan * -1);

        }else { //service list
            if (services != null) {
                pesanUser += String.format("--- SERVICE LIST ---\n");
                for (LaundryService service : services) {
                    pesanUser += String.format("-%s @ Rp.%d\n", service.getServiceName(), service.getHarga(berat));
                }
            }
            pesanUser += String.format("Harga Akhir: %d\n\n", totalHarga);
        }
        return pesanUser;
    }

    public int lamaHariPaket() { //method hitung lama hari paket
        int lamaHariPaket = 0;
        if (paket.equals("express")) {
            baseHarga = 12000;
            return lamaHariPaket = 1;

        } else if (paket.equals("fast")) {
            baseHarga = 10000;
            return lamaHariPaket = 2;

        } else if (paket.equals("reguler")) {
            baseHarga = 7000;
            return lamaHariPaket = 3;
        }
        return -1;
    }
    // Dibawah ini adalah getter dan setter

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan() {
        return this.sisaHariPengerjaan;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setIsDone() {
        this.isDone = true;
    }

    public void setSisaHariPengerjaan() {
        toNextDay();

    }

    public LaundryService[] getServices() {
        return services;
    }

    public int getIdNotaPribadi() {
        return this.idNotaPribadi;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public boolean getIsDelayed() {
        return isDelayed;
    }

    public Object getNotaStatus() {
        String message = String.format("Nota %d : ", id);
        if (isDone) {
            return message + "Sudah selesai.";
        }
        return message + "Belum selesai.";
    }

}