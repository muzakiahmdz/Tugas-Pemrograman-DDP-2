package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    //attributes
    private String nama;
    private String noHp;
    private String userID;
    private int bonusCounter = 0;
    public Member(String nama, String noHp) {
        //Constructor
        this.nama = nama;
        this.noHp = noHp;
        this.bonusCounter = 0;
        this.userID = MainMenu.generateId(nama, noHp);
    }

    // Getter
    public String getId(){
        return this.userID;
    }

    public String getNama(){
        return this.nama;
    }

    public String getNoHp(){
        return this.noHp;
    }

    public int getBonusCounter(){
        return this.bonusCounter;
    }

    //setter
    public void setBonusCounter(int setter){
        this.bonusCounter = setter;
    }




}



