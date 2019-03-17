package com.example.android.mentormatch;

import android.content.Context;

import java.util.ArrayList;

public class Conversation {
    private Mentee mentee;
    private Mentor mentor;
    private ArrayList<ChatMessage> messages;

    public Conversation(Mentor mentor, Mentee mentee){
        this.mentee = mentee;
        this.mentor = mentor;
        messages = new ArrayList<>();

    }
    public void addMessage(ChatMessage e){
        messages.add(e);
    }
}
