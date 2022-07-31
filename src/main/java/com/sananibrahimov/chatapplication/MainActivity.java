package com.sananibrahimov.chatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    CircleImageView imageView;
    TextView username;
FirebaseAuth auth;
    FirebaseUser user1;
    FirebaseFirestore firestore;
    ArrayList<user> userArrayList;
    Toolbar toolbar1;
    TabLayout tabLayout;
    ViewPager viewPager;
    String isAttendance;
    String isCalender;
    user user;
    DocumentReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         toolbar1=findViewById(R.id.toolbarmain);
        this.setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("");


        imageView=findViewById(R.id.profileimage);
        username=findViewById(R.id.username3);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        user1= auth.getCurrentUser();
        userArrayList=new ArrayList<>();
          tabLayout=findViewById(R.id.tablayout);
          viewPager=findViewById(R.id.viewpager);

       String id2= user1.getUid();




        firestore.collection("userid").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {

                } else {
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                      isAttendance = documentChange.getDocument().getData().get("username").toString();
                       isCalender = documentChange.getDocument().getData().get("id").toString();
                        if (id2.equals(isCalender)) {
                            username.setText(isAttendance);
                        }

                        user=new user(isCalender,isAttendance);
                        userArrayList.add(user);
                    }


                    if (user.getImageurl()==null){

                        imageView.setImageResource(R.drawable.sanan);
                    }
                    else{
                        Glide.with(MainActivity.this).load(user.getImageurl()).into(imageView);
                    }
                }
            }
        });




       viewpageradapter viewpageradapter=new viewpageradapter(getSupportFragmentManager());

         viewPager.setAdapter(viewpageradapter);

         tabLayout.setupWithViewPager(viewPager);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.logout){
            auth.signOut();
            Intent intent=new Intent(MainActivity.this,startactivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}