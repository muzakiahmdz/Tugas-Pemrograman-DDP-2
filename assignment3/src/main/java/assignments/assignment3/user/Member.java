package assignments.assignment3.user;

import assignments.assignment3.nota.Nota;
public class Member {
    //constructor
    protected String id;
    protected String password;
    protected String nama;
    protected Nota[] notaList = new Nota[0];

    public Member(String nama, String id, String password) {
        this.nama = nama;
        this.id = id;
        this.password = password;
    }

    /**
     * Method otentikasi member dengan ID dan password yang diberikan.
     *
     * @param id -> ID anggota yang akan diautentikasi.
     * @param password -> password anggota untuk mengautentikasi.
     * @return true jika ID dan password sesuai dengan instance member, false jika tidak.
     */
    public boolean login(String id, String password) { //method login

        return id.equals(this.id) && authenticate(password);
    }

    /**
     * Menambahkan nota baru ke NotaList instance member.
     *
     * @param nota Nota object untuk ditambahkan.
     */
    public void addNota(Nota nota) { //add nota, method utk masukin ke list nota
        Nota[] newNotaList = new Nota[notaList.length + 1];
        System.arraycopy(notaList, 0, newNotaList, 0, notaList.length);
        newNotaList[newNotaList.length - 1] = nota;
        notaList = newNotaList;
    }

    /**
     * Method otentikasi member dengan password yang diberikan.
     *
     * @param password -> sandi password anggota untuk mengautentikasi.
     * @return true jika ID dan password sesuai dengan instance member, false jika tidak.
     */
    protected boolean authenticate(String password) {
        return this.password.equals(password);
    }

    // Dibawah ini adalah getter

    public String getNama() {
        return nama;
    }

    public String getId() {
        return id;
    }

    public Nota[] getNotaList() {
        return notaList;
    }
}