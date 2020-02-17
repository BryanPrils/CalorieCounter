package com.example.caloriecounter.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.caloriecounter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AccountLoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(AccountLoginActivity.this, MainActivity.class));
            finish();
        }

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressbar);
        btnLogin = findViewById(R.id.btn_login);
        btnReset = findViewById(R.id.btn_reset_password);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountLoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email = inputEmail.getText().toString();
               final String password = inputPassword.getText().toString();

               if(TextUtils.isEmpty(email)){
                   Toast.makeText(getApplication(), "Enter Email Address!!!!", Toast.LENGTH_SHORT).show();
                   return;
               }
               if(TextUtils.isEmpty(password)){
                   Toast.makeText(getApplication(), "Enter password!!!!", Toast.LENGTH_SHORT).show();
                   return;
               }

               progressBar.setVisibility(View.VISIBLE);

               auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(AccountLoginActivity.this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       progressBar.setVisibility(View.GONE);
                       if(!task.isSuccessful()){
                           if(password.length()<6){
                               inputPassword.setError(getString(R.string.minimum_password));
                           }else {
                               Toast.makeText(AccountLoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                           }
                       }else{
                           Intent intent = new Intent(AccountLoginActivity.this, MainActivity.class);
                           startActivity(intent);
                           finish();
                       }
                   }
               });

            }
        });


    }
}
