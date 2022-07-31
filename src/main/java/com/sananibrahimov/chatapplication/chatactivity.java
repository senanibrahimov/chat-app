package com.sananibrahimov.chatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class chatactivity extends AppCompatActivity {
CircleImageView cicleimageview;
FirebaseUser firebaseUser;
FirebaseFirestore firestore;
Intent intent;
TextView textView;
ImageButton btn_send;
EditText btn_text;
DocumentReference documentReference2;


Messageadapter messageadapter;
ArrayList<chat> chatArrayList;
RecyclerView recyclerView;
    String username;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity);

        Toolbar toolbar=findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cicleimageview=findViewById(R.id.profileimage);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        firestore=FirebaseFirestore.getInstance();
        intent=getIntent();
        textView=findViewById(R.id.username3);
        btn_send=findViewById(R.id.imaebuutton);
        btn_text=findViewById(R.id.sentext);
        String userid=  intent.getStringExtra("userid1");

        recyclerView=findViewById(R.id.messagereycel);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);




        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=btn_text.getText().toString();
                if (!msg.equals("")){

                    senmessage(firebaseUser.getUid(),userid,msg);
                }
                else{
                    Toast.makeText(chatactivity.this,"you cant message",Toast.LENGTH_SHORT).show();

                }
                btn_text.setText("");
            }
        });



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





      firestore.collection("userid").addSnapshotListener(new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

              if (error!=null){
                  System.out.println("islemur");
              }
              else {

                  for (DocumentChange documentChange : value.getDocumentChanges()) {


                      String id = documentChange.getDocument().getData().get("id").toString();
                       username = documentChange.getDocument().getData().get("username").toString();
                      user user = new user(id, username);


                      if (user.getId().equals(userid)) {
                          textView.setText(user.getUsername());

                          if (user.getImageurl() == null) {
                              cicleimageview.setImageResource(R.mipmap.ic_launcher);
                              System.out.println("salala");
                          } else {
                              Glide.with(chatactivity.this).load(user.getImageurl()).into(cicleimageview);

                          }

                          readmessages(firebaseUser.getUid(),userid,user.getImageurl());
                      }
                  }
              }
          }
      });
    }

    private void senmessage(String sender,String recevier,String message){

         databaseReference= FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("reciver",recevier);
        hashMap.put("message",message);

        databaseReference.child("chats").push().setValue(hashMap);

    }



    private  void readmessages(String myid,String userid3,String imagerurl){
        chatArrayList=new ArrayList<>();
        databaseReference =FirebaseDatabase.getInstance().getReference("chats");
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               chatArrayList.clear();
               System.out.println("senan");

               for (DataSnapshot snapshot1:snapshot.getChildren()){

                   chat chat=snapshot1.getValue(chat.class);

                   System.out.println(chat.getMessage());
                   if (chat.getReciver().equals(myid)&& chat.getSender().equals(userid3) || chat.getReciver().equals(userid3) && chat.getSender().equals(myid)){

                       chatArrayList.add(chat);
                   }
                   messageadapter=new Messageadapter(chatArrayList,chatactivity.this,imagerurl);

                   recyclerView.setAdapter(messageadapter);


               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }

}

