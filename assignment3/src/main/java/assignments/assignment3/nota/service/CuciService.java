package assignments.assignment3.nota.service;

public class CuciService implements LaundryService{
    private boolean done = false;

    @Override
    public String doWork() {
        done = true;
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        // TODO
        return done;
    }

    @Override
    public long getHarga(int berat) {
        // TODO
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
