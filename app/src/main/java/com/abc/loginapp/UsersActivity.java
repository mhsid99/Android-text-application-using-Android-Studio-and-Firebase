package com.abc.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UsersActivity extends AppCompatActivity
{
    private RecyclerView nuserslist;
    private DatabaseReference nusersdatabase;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        nusersdatabase= FirebaseDatabase.getInstance().getReference().child("logindemo-b5059");
        nuserslist=(RecyclerView) findViewById(R.id.userslist);
        nuserslist.setHasFixedSize(true);
        auth=FirebaseAuth.getInstance();

        nuserslist.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String db=" ";
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    db=db+postSnapshot.getValue();


                }
                Toast.makeText(UsersActivity.this,db,Toast.LENGTH_SHORT).show();
                Log.d("dbhamza123", db);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*


    }

   /* @Override
    protected void onStart()
    {
        super.onStart();
     /*   FirebaseRecyclerAdapter<Users,Userviewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, Userviewholder>(
                Users.class,
                R.layout.userslayout,
                Userviewholder.class,
                nusersdatabase

        ) {
            @Override
            protected void onBindViewHolder(@NonNull Userviewholder userviewholder, int i, @NonNull Users users) {

            }

            @NonNull
            @Override
            public Userviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
    }
    public class Userviewholder extends RecyclerView.ViewHolder
    {
        View mView;

        public Userviewholder(@NonNull View itemView)
        {
            super(itemView);
            mView=itemView;
        }
    }*/
    //*


}
