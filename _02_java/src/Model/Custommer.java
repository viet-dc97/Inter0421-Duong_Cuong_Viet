package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Custommer {
    private String name;
    private String birthDay;
    private String gender;
    private String idCard;
    private String phone;
    private String email;
    private String customerType;
    private String address;
    private String services;

    @Override
    public String toString() {
        return "Custommer{" +
                "name='" + name + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", gender='" + gender + '\'' +
                ", idCard='" + idCard + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", customerType='" + customerType + '\'' +
                ", address='" + address + '\'' +
                ", services=" + services +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Custommer() {
    }

    public Custommer(String name, String birthDay, String gender, String idCard, String phone, String email, String customerType, String address, String services) {
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.idCard = idCard;
        this.phone = phone;
        this.email = email;
        this.customerType = customerType;
        this.address = address;
        this.services = services;
    }




    public void showInfor(){
        System.out.println(
                "name= " + name +
                ", birthDay= " + birthDay +
                ", gender= " + gender +
                ", idCard= " + idCard +
                ", phone= " + phone +
                ", email= " + email +
                ", customerType= " + customerType +
                ", address= " + address +
                ", services= " + services );
    }

    public void addNewCustomer(){

    }
    public void showAllInforCustommer(){

    }
}
