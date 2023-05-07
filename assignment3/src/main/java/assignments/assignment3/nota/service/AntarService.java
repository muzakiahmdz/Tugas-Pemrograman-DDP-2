package assignments.assignment3.nota.service;

import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService{
    private boolean done = false;

    @Override
    public String doWork() {
        done = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return done;

    }

    @Override
    public long getHarga(int berat) {
        int biayaCucianDiantar = 0;
        if (berat <= 4) {
           biayaCucianDiantar = 2000;

        }else {
            biayaCucianDiantar = berat * 500;
        }
        return biayaCucianDiantar;
    }

    @Override
    public String getServiceName() {

        return "Antar";
    }
}
