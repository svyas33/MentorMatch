package com.example.android.mentormatch;

public class ProfileData {
    private String name, major, year, bio,email;
    public ProfileData(String email, String name,String major, String year, String bio){
        this. major = major;
        this.name = name;
        this.year = year;
        this.bio = bio;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public String getMajor() {
        return major;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
