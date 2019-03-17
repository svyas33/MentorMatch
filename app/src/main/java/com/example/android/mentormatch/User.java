package com.example.android.mentormatch;

public class User {
    private ProfileData data;
    String userID;
    String status;

    public User(String id,ProfileData profileData, String m){
        userID = id;
        data = profileData;
        status = m;
    }

    public String getStatus() {
        return status;
    }

    public ProfileData getData() {
        return data;
    }

    public void setData(ProfileData data) {
        this.data = data;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
