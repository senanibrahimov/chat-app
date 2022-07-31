package com.sananibrahimov.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sananibrahimov.chatapplication.databinding.UsersReycelrowBinding;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.viewholder> {

    ArrayList<user> musers;
    private Context context;

    public adapter(ArrayList<user> musers, Context context) {
        this.musers = musers;
        this.context = context;
    }

    @NonNull
    @Override
    public adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UsersReycelrowBinding binding=UsersReycelrowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new adapter.viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.viewholder holder, int position) {

   holder.binding.usernameidusers.setText(musers.get(position).username);
       if (musers.get(position).getImageurl()==null){
           holder.binding.profileimageusers.setImageResource(R.mipmap.ic_launcher);
       }
       else{
           Glide.with(context).load(R.drawable.sanan).into(holder.binding.profileimageusers);
       }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,chatactivity.class);
                intent.putExtra("userid1",musers.get(holder.getAdapterPosition()).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {


        return musers.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        UsersReycelrowBinding binding;
        public viewholder( UsersReycelrowBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
