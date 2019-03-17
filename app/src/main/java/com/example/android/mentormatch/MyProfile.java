package com.example.android.mentormatch;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends Fragment {
    EditText displayName, displayYear, displayMajor, displayBio;
    ImageView myProfileImg;
    Button signOutButton, confirmButton;
    String userID, name, major, year, bio, profileImageUrl;
    DatabaseReference mCustomerDatabase, userSpot;
    Uri resultUri;
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
        confirmButton = rootview.findViewById(R.id.my_profile_confirm);
        myProfileImg = rootview.findViewById(R.id.my_profile_image);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("User");

        if (mCustomerDatabase.child("Mentor").child(userID) == null) {
            userSpot = mCustomerDatabase.child("Mentee").child(userID);
        } else {
            userSpot = mCustomerDatabase.child("Mentor").child(userID);
        }
        getUserInfo();
        myProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

        signOutButton = (Button)rootview.findViewById(R.id.my_profile_signout);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(v);
            }
        });
        return rootview;
    }

    private void getUserInfo() {
        userSpot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map userInfo = ( Map<String, Object>) dataSnapshot.getValue();
                if(userInfo.get("name")!=null){
                    name = userInfo.get("name").toString();
                    displayName.setText(name);
                }
                if(userInfo.get("major")!=null){
                    major = userInfo.get("major").toString();
                    displayMajor.setText(major);
                }
                if(userInfo.get("bio")!=null){
                    bio = userInfo.get("bio").toString();
                    displayBio.setText(bio);
                }
                if(userInfo.get("year")!=null){
                    year = userInfo.get("year").toString();
                    displayYear.setText(year);
                }
                if(userInfo.get("profileImageUrl")!=null){
                    profileImageUrl = userInfo.get("profileImageUrl").toString();
                    switch(profileImageUrl){
                        case "default":
                            Glide.with(getActivity().getApplication()).load(R.mipmap.ic_launcher).into(myProfileImg);
                            break;
                        default:
                            Glide.with(getActivity().getApplication()).load(profileImageUrl).into(myProfileImg);
                            break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveUserData() {
        bio = displayBio.getText().toString();
        name = displayName.getText().toString();
        major = displayMajor.getText().toString();
        year = displayYear.getText().toString();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", name);
        userInfo.put("bio", bio);
        userInfo.put("year", year);
        userInfo.put("major", major);
        userSpot.updateChildren(userInfo);
        if(resultUri!=null){
            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile images").child(userID);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filePath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Photo failed to download", Toast.LENGTH_SHORT).show();
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                    Map userInfo = new HashMap();
                    userInfo.put("profileImageUrl", downloadUrl);
                    userSpot.updateChildren(userInfo);
                }
            });
        }
    }

    public void signOut(View view){
        mAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        return;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            myProfileImg.setImageURI(resultUri);
        }
    }
}