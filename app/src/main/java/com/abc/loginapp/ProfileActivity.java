package com.abc.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity
{
    private ImageView profilepic;
    private TextView profilename,profileage,profileemail;
    private Button profileupdate,changepassword;
    private FirebaseAuth auth ;
    private FirebaseDatabase fd;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilepic=findViewById(R.id.propic);
        profilename=findViewById(R.id.proname);
        profileage=findViewById(R.id.proage);
        profileemail=findViewById(R.id.proemail);
        profileupdate=findViewById(R.id.probutton);
        changepassword=findViewById(R.id.changepassword);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth =FirebaseAuth.getInstance();

        fd=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        DatabaseReference dataref= fd.getReference("user").child(auth.getUid());

        StorageReference storageReference=firebaseStorage.getReference();
        storageReference.child(auth.getUid()).child("Images/Profile Picture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri)
            {
                Picasso.get().load(uri).fit().centerCrop().into(profilepic);
            }
        });

        dataref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                UserProfile up=dataSnapshot.getValue(UserProfile.class);
                profilename.setText("Name: "+up.getUser_name());
                profileage.setText("Age: "+up.getUser_age());
                profileemail.setText("Email ID: "+up.getUser_email());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(ProfileActivity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        profileupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(ProfileActivity.this,UpdateProfile.class));
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(ProfileActivity.this,UpdatePassword.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
