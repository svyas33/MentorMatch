package com.example.android.mentormatch;

public class User {
    private ProfileData data;
    public User(ProfileData profileData){
        data = profileData;
    }

    public ProfileData getData() {
        return data;
    }

    public void setData(ProfileData data) {
        this.data = data;
    }
}
