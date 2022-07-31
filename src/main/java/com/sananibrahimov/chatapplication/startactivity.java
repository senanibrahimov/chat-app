package com.sananibrahimov.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class startactivity extends AppCompatActivity {
Button register,Login;

FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);

        register = findViewById(R.id.register1);
        Login = findViewById(R.id.login1);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            Intent intent = new Intent(startactivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(startactivity.this, Registeractivity.class);
                    startActivity(intent);
                }
            });

            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(startactivity.this, Loginactivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}