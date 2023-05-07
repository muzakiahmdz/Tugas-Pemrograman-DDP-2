 package assignments.assignment3.user;

public class Employee extends Member {
    //constructor
    public static int employeeCount;
    public Employee(String nama, String password) {
        super(nama, generateId(nama), password);
    }

    /**
     * Membuat ID employee dari nama employee dengan format
     * NAMA_DEPAN-[jumlah employee, mulai dari 0]
     * Contoh: Dek Depe adalah employee pertama yang dibuat, sehingga IDnya adalah DEK-0
     *
     * @param nama -> Nama lengkap dari employee
     */
    private static String generateId(String nama) { //method tuk membuat id unik user
        String[] parts = nama.split(" ");
        String firstName = parts[0].toUpperCase();
        String id = firstName + "-" + employeeCount;
        employeeCount++;
        return id;
    }
}
