package com.example.repka_sizebook;

import java.util.Date;

/**
 * This is the main class that is used throughout the program to create
 * profiles, as well as access the profile's variables and set them.
 * For this class, the name of each of the profiles is just the name
 * given when the profile is created.
 *
 * @author Derek.R
 * @version 1.2
 * @since 1.0
 */

public class Profile {
    private String name;
    private Date date;
    private Double neck;
    private Double  bust;
    private Double  chest;
    private Double  waist;
    private Double  hip;
    private Double  inseam;
    private String comment;

    /**
     * Called when the profile entity is created, which sets the
     * new profile name to the inputted name.
     * @param name
     */
    public Profile(String name){
        this.name = name;
    }

    /**
     * Getter function for the name variable.
     * @return The name of the profile.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter function for the name variable.
     * @param name
     */

    public void setName(String name) {this.name = name;}
    /**
     * Getter function for the date variable.
     * @return The date of the profile.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter function for the date variable.
     * @param date
     */

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter function for the neck variable.
     * @return The neck circumference of the profile.
     */
    public Double getNeck() {return neck;}

    /**
     * Setter function for the neck variable.
     * @param neck
     */

    public void setNeck(Double neck) {
        this.neck = neck;
    }

    /**
     * Getter function for the bust variable.
     * @return The bust circumference of the profile.
     */
    public Double getBust() {
        return bust;
    }

    /**
     * Setter function for the bust variable.
     * @param bust
     */

    public void setBust(Double bust) {
        this.bust = bust;
    }

    /**
     * Getter function for the chest variable.
     * @return The chest circumference of the profile
     */
    public Double getChest() {
        return chest;
    }

    /**
     * Setter function for the chest variable.
     * @param chest
     */

    public void setChest(Double chest) {
        this.chest = chest;
    }

    /**
     * Getter function for the waist variable.
     * @return The waist circumference of the profile
     */
    public Double getWaist() {
        return waist;
    }

    /**
     * Setter function for the waist variable.
     * @param waist
     */

    public void setWaist(Double waist) {
        this.waist = waist;
    }

    /**
     * Getter function for the hip variable.
     * @return The hip circumference of the profile.
     */
    public Double getHip() {
        return hip;
    }

    /**
     * Setter function for the hip variable.
     * @param hip
     */

    public void setHip(Double hip) {
        this.hip = hip;
    }

    /**
     * Getter function for the inseam variable.
     * @return The length of the inseam of the profile.
     */
    public Double getInseam() {
        return inseam;
    }

    /**
     * Setter function for the inseam variable.
     * @param inseam
     */

    public void setInseam(Double inseam) {
        this.inseam = inseam;
    }

    /**
     * Getter function for the comment variable.
     * @return The comment of the profile.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter function for the comment variable.
     * @param comment
     */

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Converts the name of the profile to a string.
     * @return the name of the profile.
     */
    @Override
    public String toString(){return name;}
}
