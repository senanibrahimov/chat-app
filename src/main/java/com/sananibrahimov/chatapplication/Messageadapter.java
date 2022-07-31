package com.sananibrahimov.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sananibrahimov.chatapplication.databinding.LeftReycelrowBinding;
import com.sananibrahimov.chatapplication.databinding.RightReycelrowBinding;
import com.sananibrahimov.chatapplication.databinding.UsersReycelrowBinding;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Messageadapter extends RecyclerView.Adapter<Messageadapter.viewholder> {


    public  static final int MSG_TYPE_LEFT=0;
    public  static final int MSG_TYPE_RGHIT=1;

    ArrayList<chat> mchat;
     Context context;
    FirebaseUser firebaseUser;


    String imageurl;

    public Messageadapter(ArrayList<chat> mchat, Context context,String imageurl) {
        this.mchat = mchat;
        this.context = context;
        this.imageurl=imageurl;
    }

    @NonNull
    @Override
    public Messageadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RGHIT){

            View view=LayoutInflater.from(context).inflate(R.layout.right_reycelrow,parent,false);
            return   new Messageadapter.viewholder(view);
        }
        else{
            System.out.println(viewType);
            View  view=LayoutInflater.from(context).inflate(R.layout.left_reycelrow,parent,false);
            return new Messageadapter.viewholder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull Messageadapter.viewholder holder, int position) {
             chat chat=mchat.get(position);
             holder.textView.setText(chat.getMessage());






    }

    @Override
    public int getItemCount() {


        return mchat.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
       TextView textView;
       CircleImageView circleImageView;

        public viewholder(View itemview) {
                 super(itemview);
        textView=itemview.findViewById(R.id.show);
        circleImageView=itemview.findViewById(R.id.image);









        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (mchat.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RGHIT;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }
}
