package com.abc.loginapp;

import android.graphics.Color;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class messageadapter extends RecyclerView.Adapter<messageadapter.messageviewholder>
{
    private List<messages> mmessagelist;
    private FirebaseAuth mauth;
    private DatabaseReference muserdatabase;

    public messageadapter(List<messages> mmessagelist)
    {
        this.mmessagelist= mmessagelist;
    }

    @Override
    public messageviewholder onCreateViewHolder( ViewGroup parent, int viewType)
    {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout ,parent,false);
        return new messageviewholder(v);
    }


    public class messageviewholder extends RecyclerView.ViewHolder
    {
        public TextView messagetext;
        public ImageView messageimage;
        //public TextView timetext;
        public messageviewholder(View view)
        {
            super(view);
            messagetext=(TextView)view.findViewById(R.id.message_text_layout);
            messageimage=(ImageView)view.findViewById(R.id.gandmedanda);
        }
    }
    @Override
    public void onBindViewHolder(final messageviewholder viewholder, int i)
    {
        mauth=FirebaseAuth.getInstance();
        String current_user_id=mauth.getCurrentUser().getUid();
        messages c= mmessagelist.get(i);
        String from_user=c.getFrom();
        String messge_type=c.getType();

        muserdatabase= FirebaseDatabase.getInstance().getReference().child("user").child(from_user);
        muserdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name= dataSnapshot.child("user_name").getValue().toString();
              //  String image =dataSnapshot.child("Image").getValue().toString();

              //  Picasso.get(viewholder.messageimage.getContext()).load(image).placeholder(R.drawable.de)


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //if(messge_type.equals("text")){

        if(from_user.equals(current_user_id))
        {
            viewholder.messagetext.setBackgroundColor(Color.WHITE);
            viewholder.messagetext.setTextColor(Color.BLACK);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)viewholder.messagetext.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            //params.addRule(RelativeLayout.LEFT_OF, R.id.id_to_be_left_of);

            viewholder.messagetext.setLayoutParams(params);
        }
        else
        {
            viewholder.messagetext.setBackgroundResource(R.drawable.message_text_background);
            viewholder.messagetext.setTextColor(Color.WHITE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)viewholder.messagetext.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            //params.addRule(RelativeLayout.LEFT_OF, R.id.id_to_be_left_of);

            viewholder.messagetext.setLayoutParams(params);

         //   viewholder.messagetext.setGravity(Gravity.LEFT | Gravity.START);
        }
        viewholder.messagetext.setText(c.getMessage());

        viewholder.messageimage.setVisibility(View.INVISIBLE);
       /* }else {
            viewholder.messagetext.setVisibility(View.INVISIBLE);
            Picasso.get().load(c.getMessage()).into(viewholder.messageimage);
        }*/


    }

    @Override
    public int getItemCount() {
        return mmessagelist.size();
    }


}
