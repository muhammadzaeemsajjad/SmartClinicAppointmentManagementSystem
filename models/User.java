package models;



public abstract class User extends Person  {
    private static final long serialVersionUID = 1L;

    protected String userName;
    protected String password;
    protected String email;
    protected String address;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User(String name, int age, String CNIC, String gender, long phoneNumber,
                String userName, String password, String email, String address) {

        super(name, age, CNIC, gender, phoneNumber);

        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
    }


    @Override
    public String toString() {
        return super.toString() + " User [userName=" + userName + ", email=" + email + ", address=" + address + "]";
    }
}
