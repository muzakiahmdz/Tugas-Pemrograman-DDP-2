package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Nota {
    //attributes
    private static int idCounter = 0;
    private Member member;
    private String paket;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady;
    private int idNotaPribadi;
//constructor
    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.idNotaPribadi = idCounter;
        idCounter++;
    }
//getter dan setter
    public String getTanggalMasuk() {
        return this.tanggalMasuk;
    }

    public void setTanggalMasuk(String tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public int getSisaHariPengerjaan() {
        return this.sisaHariPengerjaan;
    }

    public void setSisaHariPengerjaan(int sisaHariPengerjaan) {
        if (sisaHariPengerjaan > 0) {
            this.sisaHariPengerjaan = sisaHariPengerjaan;
        }
        if (sisaHariPengerjaan == 0) {
            this.setIsReady();
        }
    }

    public boolean getIsReady() {
        return this.isReady;
    }

    public void setIsReady() {
        this.isReady = true;
    }

    public int getIdNotaPribadi() {
        return this.idNotaPribadi;
    }

    public void setIdCounter(int counter){
        idCounter = counter;
    }

    public String getStatus() {
        if (getIsReady()) {
            return "Sudah dapat diambil!";
        } else {
            return "Belum dapat diambil :(";
        }
    }
}
