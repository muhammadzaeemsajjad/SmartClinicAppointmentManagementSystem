package models;

public class Receptionist extends User implements Comparable<Receptionist> {
    private static final long serialVersionUID = 1L;

    private int receptionistId;

    public Receptionist(String name, int age, String CNIC, String gender, long phoneNumber,
                        String userName, String password, String email, String address,
                        int receptionistId) {

        super(name, age, CNIC, gender, phoneNumber, userName, password, email, address);
        this.receptionistId = receptionistId;
    }

    public int getReceptionistId() {
        return receptionistId;
    }

    public void setReceptionistId(int receptionistId) {
        this.receptionistId = receptionistId;
    }
    public int compareTo(Receptionist other) {
        return this.userName.compareToIgnoreCase(other.userName);
    }
    @Override
    public String toString() {
        return super.toString() + " Receptionist [receptionistId=" + receptionistId + "]";
    }
}
