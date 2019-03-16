package com.example.android.mentormatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton, phoneLoginButton;
    private EditText userEmail, userPassword;
    private TextView forgetPasswordLink, needNewAccountLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.sign_in_button);
        userEmail = findViewById(R.id.sing_in_email);
        userPassword = findViewById(R.id.sign_in_password);
        needNewAccountLink = findViewById(R.id.need_new_account);
        forgetPasswordLink = findViewById(R.id.forget_password_link);
    }
    private void sendUserToMainActivity() {
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(loginIntent);
    }
    private void sendUserToRegisterActivity() {
        Intent loginIntent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(loginIntent);
    }
}
