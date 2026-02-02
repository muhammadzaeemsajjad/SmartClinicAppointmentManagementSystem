package models;


public class Admin extends User {
    private static final long serialVersionUID = 2L;



    public Admin(String name, int age, String CNIC, String gender, long phoneNumber,
                 String userName, String password, String email, String address) {

        // Call the User constructor that accepts Person fields + user fields
        super(name, age, CNIC, gender, phoneNumber, userName, password, email, address);


    }

    @Override
    public String toString() {
        return super.toString() + " Admin []";
    }

}