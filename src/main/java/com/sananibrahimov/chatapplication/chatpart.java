package com.sananibrahimov.chatapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class chatpart extends Fragment {

    adapter adapter;
    ArrayList<user> userArrayList;
    RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    ArrayList<String> stringArrayList;
    FirebaseFirestore firebaseFirestore;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chatpart, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.recycelview45);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        stringArrayList=new ArrayList<>();

        databaseReference=FirebaseDatabase.getInstance().getReference("chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stringArrayList.clear();
                 for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                     chat  chat=dataSnapshot.getValue(chat.class);

                     if (chat.getSender().equals(firebaseUser.getUid())){

                         stringArrayList.add(chat.getReciver());
                     }
                     if (chat.getReciver().equals(firebaseUser.getUid())){

                         stringArrayList.add(chat.getSender());
                     }

                 }
                readchatts();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

   public void readchatts(){
        userArrayList=new ArrayList<>();
       firebaseFirestore=FirebaseFirestore.getInstance();

       firebaseFirestore.collection("userid").addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

               userArrayList.clear();
              for (DocumentChange documentChange: value.getDocumentChanges()){
                  String id=documentChange.getDocument().getData().get("id").toString();
                  String username=documentChange.getDocument().getData().get("username").toString();
                  user user=new user(id,username);

                  for (String id2:stringArrayList){

                       if (user.getId().equals(id2)){
                           if (userArrayList.size()!=0){
                               for (int i=0;i<userArrayList.size();i++){
                                   user user4=userArrayList.get(i);
                                   if (!user.getId().equals(user4.getId())){
                                       userArrayList.add(user);

                                   }
                               }
                           }
                           else {
                               userArrayList.add(user);
                           }
                       }


                  }

              }


               adapter=new adapter(userArrayList,getContext());

              recyclerView.setAdapter(adapter);


           }
       });
   }
}