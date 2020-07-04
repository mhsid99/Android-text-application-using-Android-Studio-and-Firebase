package com.abc.loginapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class UpdateProfile extends AppCompatActivity
{
    private EditText newusername,newuseremail,newuserage;
    private Button save;
    private FirebaseAuth auth;
    private FirebaseDatabase fd;
    private ImageView updateprofilepic;

    private static int PICK_IMAGE=123;
    Uri imagepath;
    private FirebaseStorage firebaseStorage;
   // private StorageReference storageReference;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK &&data.getData()!=null)
        {
            imagepath=data.getData();
            try {
                Bitmap bp= MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);
                updateprofilepic.setImageBitmap(bp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        newusername=findViewById(R.id.etUserNameUpdate);
        newuseremail=findViewById(R.id.etUserEmailUpdate);
        newuserage=findViewById(R.id.etAgeUpdate);
        save=findViewById(R.id.btnSave);
        updateprofilepic=findViewById(R.id.ivProfileUpdate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        fd= FirebaseDatabase.getInstance();
        final DatabaseReference dataref= fd.getReference("user").child(auth.getUid());
        dataref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                UserProfile up=dataSnapshot.getValue(UserProfile.class);
                newusername.setText(up.getUser_name());
                newuserage.setText(up.getUser_age());
                newuseremail.setText(up.getUser_email());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(UpdateProfile.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
        firebaseStorage= FirebaseStorage.getInstance();
        final StorageReference storageReference=firebaseStorage.getReference();
        storageReference.child(auth.getUid()).child("Images/Profile Picture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri)
            {
                Picasso.get().load(uri).fit().centerCrop().into(updateprofilepic);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name=newusername.getText().toString();
                String age=newuserage.getText().toString();
                String email=newuseremail.getText().toString();

                UserProfile userProfile=new UserProfile(age,email,name);

                dataref.setValue(userProfile);
                StorageReference imgref=storageReference.child(auth.getUid()).child("Images").child("Profile Picture");
                UploadTask ut= imgref.putFile(imagepath);
                ut.addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(UpdateProfile.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        Toast.makeText(UpdateProfile.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        });

        updateprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
