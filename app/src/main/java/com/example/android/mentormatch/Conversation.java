package com.example.android.mentormatch;

import android.content.Context;

import java.util.ArrayList;

public class Conversation {
    private User mentee;
    private User mentor;
    private ArrayList<ChatMessage> messages;

    public Conversation(User mentor, User mentee){
        this.mentee = mentee;
        this.mentor = mentor;
        messages = new ArrayList<>();

    }
    public void addMessage(ChatMessage e){
        messages.add(e);
    }
}
