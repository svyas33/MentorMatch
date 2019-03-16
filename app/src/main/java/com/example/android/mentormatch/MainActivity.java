package com.example.android.mentormatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    
    private FirebaseAuth mAuth;
    
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabsAccesorAdapter myTabsAccesorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.main_tabs_pager);
        myTabsAccesorAdapter = new TabsAccesorAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myTabsAccesorAdapter);

        mTabLayout = findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
    }
    private void sendUserToLogInActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            sendUserToLogInActivity();
        } else{
            updateUI(currentUser);
        }

    }

    private void updateUI(FirebaseUser currentUser) {
    }
}
