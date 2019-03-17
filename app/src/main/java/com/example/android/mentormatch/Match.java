package com.example.android.mentormatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 */
public class Match extends Fragment {
    private ArrayList<String> al;
    private ArrayAdapter arrayAdapter;
    private int i;

    private FirebaseAuth mAuth;

    public Match() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_match, container, false);

        checkUserStatus();

        al = new ArrayList<>();
        al.add("Shivani\n2021\nBiomed. Eng.");
        al.add("Rebecca\n2021\nComp. Eng.");
        al.add("Nick\n2023\nUndecided");
        al.add("Raj\n2019\nPublic Health");
        al.add("Mona\n2020\nPreMed");
        al.add("Paul\n2021\nEnglish");
        al.add("Kareem\n2022\nComp. Sci.");
        al.add("Laura\n2019\nAerospace Eng.");

        arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.item, R.id.helloText, al);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) rootView.findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
            }
            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(getContext(),"Match!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });
        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Intent intent = new Intent(getActivity(), BioPopUpActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private String userStatus;
    private String oppositeUserStatus;

    public void checkUserStatus(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mentorDb = FirebaseDatabase.getInstance().getReference().child("User").child("Mentor");
        mentorDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals(user.getUid())){
                    userStatus = "Mentor";
                    oppositeUserStatus = "Mentee";
                    getOppositeStatusUsers();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference menteeDb = FirebaseDatabase.getInstance().getReference().child("User").child("Mentee");
        menteeDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals(user.getUid())){
                    userStatus = "Mentee";
                    oppositeUserStatus = "Mentor";
                    getOppositeStatusUsers();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void getOppositeStatusUsers(){
        DatabaseReference oppositeStatusDb = FirebaseDatabase.getInstance().getReference().child("User").child(oppositeUserStatus);
        oppositeStatusDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    al.add(dataSnapshot.child("name").getValue().toString());
                    arrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}