package assignments.assignment3.nota.service;

import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService{ //ini buat antar service
    private boolean done = false;

    @Override
    public String doWork() { //status
        done = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() { //boolean indikator
        return done;

    }

    @Override
    public long getHarga(int berat) { //setting harga
        int biayaCucianDiantar = 0;
        if (berat <= 4) {
           biayaCucianDiantar = 2000;

        }else {
            biayaCucianDiantar = berat * 500;
        }
        return biayaCucianDiantar;
    }

    @Override
    public String getServiceName() { //nama service

        return "Antar";
    }
}
