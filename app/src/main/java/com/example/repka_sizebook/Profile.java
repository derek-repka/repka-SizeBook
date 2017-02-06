package com.example.repka_sizebook;

/**
 * Created by Derek.R on 2017-01-29.
 */

public class Profile {
    private String name;
    private String date;
    private Integer neck;
    private Integer bust;
    private Integer chest;
    private Integer waist;
    private Integer hip;
    private Integer inseam;
    private String comment;

    public Profile(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNeck() {return neck;}

    public void setNeck(Integer neck) {
        this.neck = neck;
    }

    public Integer getBust() {
        return bust;
    }

    public void setBust(Integer bust) {
        this.bust = bust;
    }

    public Integer getChest() {
        return chest;
    }

    public void setChest(Integer chest) {
        this.chest = chest;
    }

    public Integer getWaist() {
        return waist;
    }

    public void setWaist(Integer waist) {
        this.waist = waist;
    }

    public Integer getHip() {
        return hip;
    }

    public void setHip(Integer hip) {
        this.hip = hip;
    }

    public Integer getInseam() {
        return inseam;
    }

    public void setInseam(Integer inseam) {
        this.inseam = inseam;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString(){return name;}
}
