package models;

import java.io.Serializable;

public abstract class Person implements Serializable {
    private static final long serialVersionUID = 10L;

    protected String name;
    protected int age;
    protected String CNIC;
    protected String gender;
    protected long phoneNumber;

    public Person(String name, int age, String CNIC, String gender, long phoneNumber) {
        this.name = name;
        this.age = age;
        this.CNIC = CNIC;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    // Getters & setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getCNIC() {
        return CNIC;
    }
    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age +
               ", gender=" + gender + ", CNIC=" + CNIC +
               ", phoneNumber=" + phoneNumber + "]";
    }
}

