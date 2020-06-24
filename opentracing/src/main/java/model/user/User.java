package model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class User {
    private String email;
    private String phone;
    private int balance;
    private boolean isSpecialSubscribed;
    private String pack;
    //private BasePack basePack;
    //private SpecialServices specialServices;

	  @Autowired
    public User() {
        balance = 200;
      isSpecialSubscribed = false;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * @return the isSubScribed
     */
    public boolean isSpecialSubscribed() {
        return isSpecialSubscribed;
    }

    /**
     * @param isSpecialSubscribed the isSubScribed to set
     */
    public void setisSpecialSubscribed(boolean isSpecialSubscribed) {
        this.isSpecialSubscribed = isSpecialSubscribed;
    }


    /**
     * @param packType the isSubScribed to set
     */
    public void setpack(String packType) {
        this.pack = packType;
    }

    /**
     * @return the email
     */
    public String getpack() {
        return pack;
    }

/*    *//**
     * @return the basePack
     *//*
    public BasePack getBasePack() {
        return basePack;
    }

    *//**
     * @param basePack the basePack to set
     *//*
    public void setBasePack(BasePack basePack) {
        this.basePack = basePack;
    }

    *//**
     * @return the specialServices
     *//*
    public SpecialServices getSpecialServices() {
        return specialServices;
    }

    *//**
     * @param specialServices the specialServices to set
     *//*
    public void setSpecialServices(SpecialServices specialServices) {
        this.specialServices = specialServices;
    }*/



}
