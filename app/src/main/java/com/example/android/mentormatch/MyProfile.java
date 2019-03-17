package com.example.android.mentormatch;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends Fragment {
    EditText displayName, displayYear, displayMajor, displayBio;
    Button signOutButton;

    private FirebaseAuth mAuth;

    public MyProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_my_profile, container, false);
        displayBio = rootview.findViewById(R.id.my_profile_bio);
        displayName = rootview.findViewById(R.id.my_profile_name);
        displayYear = rootview.findViewById(R.id.my_profile_year);
        displayMajor = rootview.findViewById(R.id.my_profile_major);
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        if(mAuth.getCurrentUser()!=null){
            String userId = mAuth.getCurrentUser().getUid();


            ref.child("User").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if(user !=null){
                        ProfileData profileData = user.getData();
                        displayBio.setText(profileData.getBio());
                        displayName.setText(profileData.getName());
                        displayMajor.setText(profileData.getMajor());
                        displayYear.setText(profileData.getYear());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        signOutButton = (Button)rootview.findViewById(R.id.my_profile_signout);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(v);
            }
        });
        return rootview;
    }

    public void signOut(View view){
        mAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        return;
    }

}