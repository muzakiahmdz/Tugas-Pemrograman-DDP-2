package assignments.assignment3;
import assignments.assignment1.NotaGenerator;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;

public class LoginManager {
    //constructr
    private final EmployeeSystem employeeSystem;
    private final MemberSystem memberSystem;

    public LoginManager(EmployeeSystem employeeSystem, MemberSystem memberSystem) {
        this.employeeSystem = employeeSystem;
        this.memberSystem = memberSystem;
    }

    /**
     * Method mapping dari ke SystemCLI yang sesuai.
     *
     * @param id -> ID dari user yang akan menggunakan SystemCLI
     * @return SystemCLI object yang sesuai dengan ID, null if  ID tidak ditemukan.
     */
    public SystemCLI getSystem(String id){ //get system method defult templ
        if(memberSystem.isMemberExist(id)){
            return memberSystem;
        }
        if(employeeSystem.isMemberExist(id)){
            return employeeSystem;
        }
        return null;
    }

    /**
     * Mendaftarkan member baru dengan informasi yang diberikan.
     *
     * @param nama -> Nama member.
     * @param noHp -> Nomor handphone member.
     * @param password -> Password akun member.
     * @return Member object yang berhasil mendaftar, return null jika gagal mendaftar.
     */
    public Member register(String nama, String noHp, String password) { //method register
        if (memberSystem.isMemberExist(NotaGenerator.generateId(nama,noHp))){
            return null;
        }
        Member temp = new Member(nama, NotaGenerator.generateId(nama,noHp), password);
        memberSystem.addMember(temp);
        return temp;
    }

}