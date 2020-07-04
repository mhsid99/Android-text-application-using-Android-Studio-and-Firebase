package com.abc.loginapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Registration extends AppCompatActivity
{
    private EditText newname, newpassword, newemail,user_age;
    private Button regis;
    private TextView login;
    private FirebaseAuth auth;
    private ImageView user_pic;
    String name,email, password,age;
    private FirebaseStorage fs;
    private static int PICK_IMAGE=123;
    Uri imagepath;
    private StorageReference sr;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK &&data.getData()!=null)
        {
            imagepath=data.getData();
            try {
                Bitmap bp= MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);
                user_pic.setImageBitmap(bp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        set();
        auth= FirebaseAuth.getInstance();
        fs=FirebaseStorage.getInstance();

        sr=fs.getReference();
        user_pic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });

        regis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
              if(validate())
              {
                  String ue= newemail.getText().toString().trim();
                  String p= newpassword.getText().toString().trim();
                  auth.createUserWithEmailAndPassword(ue,p).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                  {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task)
                      {
                          if(task.isSuccessful())
                          {
                             // Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                              sendemail();
                              //startActivity(new Intent(Registration.this,MainActivity.class));

                          }
                          else
                          {
                              Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
              }
            }
        });
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(Registration.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void set()
    {
        newname=(EditText)findViewById(R.id.rname);
        newpassword=(EditText)findViewById(R.id.rpassword);
        newemail=(EditText)findViewById(R.id.remailid);
        login=(TextView)findViewById(R.id.resignup);
        regis=(Button)findViewById(R.id.register);
        user_age=(EditText)findViewById(R.id.age);
        user_pic=(ImageView)findViewById(R.id.propic);
    }
    private boolean validate()
    {
        boolean result=false;
        name=newname.getText().toString();
         password=newpassword.getText().toString();
         email=newemail.getText().toString();
         age=user_age.getText().toString();
        if(name.isEmpty()|| password.isEmpty()|| email.isEmpty()|| age.isEmpty() || imagepath ==null)
        {
            Toast.makeText(this,"Please Enter all the Details",Toast.LENGTH_SHORT).show();
        }
        else
        {
            result= true;
        }
        return result;
    }
    private void sendemail()
    {
        FirebaseUser fb=auth.getInstance().getCurrentUser();
        if(fb!=null)
        {
            fb.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        senddata();
                        Toast.makeText(Registration.this,"Successfully Registered. Verification Mail Sent",Toast.LENGTH_SHORT).show();
                        auth.signOut();
                        finish();
                        startActivity(new Intent(Registration.this,MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(Registration.this,"Verification Mail Not Sent,Please try again later",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void senddata()
    {
        FirebaseDatabase fd =FirebaseDatabase.getInstance();
        DatabaseReference dataref = fd.getReference("user").child(auth.getUid());
        StorageReference imgref=sr.child(auth.getUid()).child("Images").child("Profile Picture");
        UploadTask ut= imgref.putFile(imagepath);
        ut.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(Registration.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(Registration.this, "Upload Successful", Toast.LENGTH_SHORT).show();
            }
        });
        UserProfile up = new UserProfile( age,  email,  name);
        dataref.setValue(up);
    }
}
