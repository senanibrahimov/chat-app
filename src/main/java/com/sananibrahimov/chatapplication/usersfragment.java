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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class usersfragment extends Fragment {

ArrayList<user> musers;
private RecyclerView recyclerView;
 adapter adapter;
    String isAttendance;
    String isCalender;
    FirebaseUser firebaseUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usersfragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.recycelview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        musers=new ArrayList<>();
        adapter=new adapter(musers,getContext());

        readusers();

    }

    private void readusers() {

         firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();

        firestore.collection("userid").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    System.out.println("salam");

                } else {
                    for (DocumentChange d : value.getDocumentChanges()) {


                        isAttendance = d.getDocument().getData().get("username").toString();
                        isCalender = d.getDocument().getData().get("id").toString();
                       user user = new user(isCalender, isAttendance);


                        if (!user.getId().equals(firebaseUser.getUid())){
                            musers.add(user);

                        }

                    }
                    adapter = new adapter(musers, getContext());
                    recyclerView.setAdapter(adapter);


                }
            }
        });
    }
}