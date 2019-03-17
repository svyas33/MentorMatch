package com.example.android.mentormatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.measurement.module.Analytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private Button createAccountButton;
    private EditText userEmail, userPassword,userName, userMajor, userYear, userBio;
    private TextView alreadyHaveAccountLink;
    private RadioGroup mRadioGroup;
    private User cUser;
    private DatabaseReference postsRef;

    private FirebaseAuth mAuth;
    DatabaseReference ref;
    String email, password, name, major,bio,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userEmail = findViewById(R.id.sign_up_email);
        userPassword = findViewById(R.id.sign_up_password);
        userName = findViewById(R.id.sign_up_name);
        userMajor = findViewById(R.id.sign_up_major);
        userBio = findViewById(R.id.sign_up_bio);
        userYear = findViewById(R.id.sign_up_year);
        mRadioGroup = findViewById(R.id.radioGroup);
        alreadyHaveAccountLink = findViewById(R.id.already_have_account);
        alreadyHaveAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToLoginActivity();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        createAccountButton = findViewById(R.id.sign_up_button);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = userEmail.getText().toString();
                password = userPassword.getText().toString();
                name = userName.getText().toString();
                major = userMajor.getText().toString();
                year = userYear.getText().toString();
                bio = userBio.getText().toString();
                ProfileData profileData = new ProfileData(email, name,major,year,bio);
                int id = mRadioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(id);
                ref = FirebaseDatabase.getInstance().getReference();
                cUser = new User(null,profileData,radioButton.getText().toString());
                postsRef = ref.child(cUser.getStatus());
                createUser(email, password, cUser);
            }
        });

    }

    private void createUser(String email, String password, final User cUser){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser user = mAuth.getCurrentUser();
                            cUser.setUserID(user.getUid());
                            postsRef.child(user.getUid()).put();
                            //updateUI(user);
                            sendUserToMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private void sendUserToMainActivity() {
        Intent loginIntent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(loginIntent);
    }


}
