package com.example.bmrd.mahimess;

/**
 * Created by Dell on 27-Apr-18.
 */

public class Messes {


    public String address;
    public String name;
    public String status;
    public String thum_image;
    public String latt2;
    public String lngg2;
    public String index;

    public Messes(){

    }

    public String getLatt2() {
        return latt2;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setLatt2(String latt2) {
        this.latt2 = latt2;
    }

    public String getLngg2() {
        return lngg2;
    }

    public void setLngg2(String lngg2) {
        this.lngg2 = lngg2;
    }

    public Messes(String address, String name, String status, String thum_image,String latt2, String lngg2,String index) {
        this.address = address;
        this.name = name;
        this.status = status;
        this.thum_image = thum_image;
        this.latt2 = latt2;
        this.lngg2 = lngg2;
        this.index = index;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThum_image() {
        return thum_image;
    }

    public void setThum_image(String thum_image) {
        this.thum_image = thum_image;
    }
}
