package com.sananibrahimov.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Loginactivity extends AppCompatActivity {
Toolbar toolbar;
Button btn_login;
FirebaseAuth auth;
TextInputEditText gmail,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        toolbar=findViewById(R.id.toolbar);
        btn_login=findViewById(R.id.loginto);
        gmail=findViewById(R.id.email);
        password=findViewById(R.id.enterpassword);
        auth=FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                String mail=gmail.getText().toString();
                String pasword=password.getText().toString();

                if (mail.isEmpty() || !mail.contains("@")){

                    Toast.makeText(Loginactivity.this, "gmail i duzgun daxil edin", Toast.LENGTH_SHORT).show();
                }
                else if (pasword.isEmpty() || pasword.length()<6){

                    Toast.makeText(Loginactivity.this, "password 6 reqemden boyukdu", Toast.LENGTH_SHORT).show();

                }
                else{
                    auth.signInWithEmailAndPassword(mail,pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                Intent intent=new Intent(Loginactivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Loginactivity.this, "Authemantatition failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}