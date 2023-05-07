package assignments.assignment3.nota.service;

public class CuciService implements LaundryService{
    private boolean done = false;

    @Override
    public String doWork() { //status service
        done = true;
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() { //bool idnikator
        // TODO
        return done;
    }

    @Override
    public long getHarga(int berat) { //get harga by default
        // TODO
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    } //nama servis
}
