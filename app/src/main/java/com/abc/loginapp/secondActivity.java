package com.abc.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class secondActivity extends AppCompatActivity
{
    private FirebaseAuth author;
    private RecyclerView mconvolist;
    private DatabaseReference mconvodatabase;
    private DatabaseReference mmessagedatabase;
    private DatabaseReference musersdatabase;
    private String mcurrentuserid;
    private View mmainview;

    public secondActivity()
    {}


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        author=FirebaseAuth.getInstance();

        mconvolist=(RecyclerView)findViewById(R.id.chatlist);
        mcurrentuserid=author.getCurrentUser().getUid();
        mconvodatabase= FirebaseDatabase.getInstance().getReference().child("chat").child(mcurrentuserid);
        mconvodatabase.keepSynced(true);
        musersdatabase=FirebaseDatabase.getInstance().getReference().child("user");
        mmessagedatabase=FirebaseDatabase.getInstance().getReference().child("messages").child(mcurrentuserid);
        musersdatabase.keepSynced(true);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mconvolist.setHasFixedSize(true);
        mconvolist.setLayoutManager(linearLayoutManager);




    }



    private void Logout()
    {
         author.signOut();
       finish();
        startActivity(new Intent(secondActivity.this,MainActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
       switch (item.getItemId())
       {
           case R.id.logoutMenu:
           {
               Logout();
               break;
           }
           case R.id.ProfileMenu:
           {
               startActivity(new Intent(secondActivity.this,ProfileActivity.class));
               break;
            }
           case R.id.usersMenu:
           {
               startActivity(new Intent(secondActivity.this,usersnexttry.class));
               break;
           }
           case R.id.NewsMenu:
           {
               startActivity(new Intent(secondActivity.this,Mainnewspage.class));
               break;
           }

       }
        return super.onOptionsItemSelected(item);
    }



 /*

    @Override
    public void onStart() {
        super.onStart();

        Query conversationQuery = mconvodatabase.orderByChild("timestamp");




        FirebaseRecyclerAdapter<convo, ConvViewHolder> firebaseConvAdapter = new FirebaseRecyclerAdapter<convo, ConvViewHolder>(
                convo.class,
                R.layout.student,
               student.class,
                conversationQuery
        ) {
            @NonNull
            @Override
            public ConvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull ConvViewHolder convViewHolder, int i, @NonNull convo convo) {

            }

            @Override
            protected void populateViewHolder(final ConvViewHolder convViewHolder, final convo conv, int i) {



                final String list_user_id = getRef(i).getKey();

                Query lastMessageQuery = mmessagedatabase.child(list_user_id).limitToLast(1);



                lastMessageQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        String data = dataSnapshot.child("message").getValue().toString();
                        convViewHolder.setMessage(data, conv.isSeen());

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                musersdatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("name").getValue().toString();
                       // String userThumb = dataSnapshot.child("thumb_image").getValue().toString();

                      /*  if(dataSnapshot.hasChild("online")) {

                            String userOnline = dataSnapshot.child("online").getValue().toString();
                            convViewHolder.setUserOnline(userOnline);

                        }*/

                  /*      convViewHolder.setName(userName);
                      //  convViewHolder.setUserImage(userThumb, getContext());

                        convViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                Intent chatIntent = new Intent(secondActivity.this, chatactivity.class);
                                chatIntent.putExtra("user_id", list_user_id);
                                chatIntent.putExtra("user_name", userName);
                                startActivity(chatIntent);

                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };

        mconvolist.setAdapter(firebaseConvAdapter);

    }

    public class ConvViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ConvViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setMessage(String message, boolean isSeen){

            TextView userStatusView = (TextView)findViewById(R.id.user_age);
            userStatusView.setText(message);

            if(!isSeen){
                userStatusView.setTypeface(userStatusView.getTypeface(), Typeface.BOLD);
            } else {
                userStatusView.setTypeface(userStatusView.getTypeface(), Typeface.NORMAL);
            }

        }

        public void setName(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.user_name);
            userNameView.setText(name);

        }




}*/

}
