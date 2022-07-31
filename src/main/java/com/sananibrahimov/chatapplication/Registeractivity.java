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
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Registeractivity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
TextInputEditText username,email,password;
Button register;
FirebaseFirestore firestore;
Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);


        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.enterpassword);
        register=findViewById(R.id.registerof);
        firestore=FirebaseFirestore.getInstance();
         toolbar=findViewById(R.id.toolbar);
        firebaseAuth=FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String Btn_username=username.getText().toString();
               String Btn_email=email.getText().toString();
               String Btn_password=password.getText().toString();

               if (Btn_email.isEmpty() || !Btn_email.contains("@")){
                   Toast.makeText(Registeractivity.this, "gamili duz daxil ele", Toast.LENGTH_SHORT).show();
               }
            else   if (Btn_username.isEmpty() || Btn_username.length()<8){

                   Toast.makeText(Registeractivity.this, "isdifadeci adi 7 den yuxari olmalidi", Toast.LENGTH_SHORT).show();
               }
               else if (Btn_password.isEmpty()|| password.length()<6){
                   Toast.makeText(Registeractivity.this, "paswordun uzunlugu en az 6 olmalidi", Toast.LENGTH_SHORT).show();


               }
               else{
                   register(Btn_username,Btn_email,Btn_password);
               }


           }
       });

    }
public void register(String username,String email,String password){

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

           if (task.isSuccessful()){
               FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
               Toast.makeText(Registeractivity.this, "isdiyir", Toast.LENGTH_SHORT).show();

               String userid=firebaseUser.getUid();

               HashMap<String,Object> hashMap=new HashMap<>();
               hashMap.put("id",userid);
               hashMap.put("email",email);
               hashMap.put("username",username);
               hashMap.put("password",password);

               firestore.collection("userid").add(hashMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentReference> task) {
                     if (task.isSuccessful()){
                         Intent intent=new Intent(Registeractivity.this,MainActivity.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(intent);

                     }
                     else{
                         Toast.makeText(Registeractivity.this, "you can't register mail or password", Toast.LENGTH_SHORT).show();
                     }
                   }
               });





           }
           else{
               Toast.makeText(Registeractivity.this, "siz register oluna bilmirsiz", Toast.LENGTH_SHORT).show();
           }
            }
        });

}


}