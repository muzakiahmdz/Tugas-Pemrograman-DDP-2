package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    private boolean done; //param boolean

    @Override
    public String doWork() { //status kerjaan
        done = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() { //bool indikator
        // TODO
        return done;
    }

    @Override
    public long getHarga(int berat) {
        return 1000 * berat;
    } //methode get harga

    @Override
    public String getServiceName() {
        return "Setrika";
    } //nama servis
}
