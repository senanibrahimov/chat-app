package com.sananibrahimov.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sananibrahimov.chatapplication.databinding.ActivityRegisteractivityBinding;

import java.util.HashMap;

public class Registeractivity extends AppCompatActivity {
      EditText email,username,password;
    FirebaseUser user;

      FirebaseAuth auth;
      FirebaseFirestore firestore;
      DatabaseReference data;
ActivityRegisteractivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisteractivityBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
         user=FirebaseAuth.getInstance().getCurrentUser();
         if (user!=null){
             Intent intent=new Intent(Registeractivity.this,MainActivity.class);
             startActivity(intent);
             finish();

         }
          firestore=FirebaseFirestore.getInstance();

        username=binding.username;
        password=binding.password;
        email=binding.email;
        auth=FirebaseAuth.getInstance();
    }
    public   void register(View view){
        String email=binding.email.getText().toString();
        String username=binding.username.getText().toString();
        String password=binding.password.toString();
      auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
          @Override
          public void onSuccess(AuthResult authResult) {

              Intent intent=new Intent(Registeractivity.this,MainActivity.class);
              startActivity(intent);
              finish();
              HashMap<String,String> map=new HashMap<>();
                map.put("email",email);
                map.put("passsword",password);
                        map.put("username",username);
                        firestore.collection("data").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Registeractivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            }
                        });



          }
      });
    }
}