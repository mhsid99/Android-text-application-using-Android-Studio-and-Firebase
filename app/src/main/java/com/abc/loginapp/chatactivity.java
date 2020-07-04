package com.abc.loginapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class chatactivity extends AppCompatActivity {


    private String mchatuser;
    private  String muserid;
    private ImageButton sendbutt,sendimage;
    private EditText msg;
    private LinearLayout ll;
    private DatabaseReference mrootref;
    private FirebaseAuth mauth;
    private String mcurrentuserid;
    private RecyclerView mmsglist;
    private final List<messages> messagesList= new ArrayList<>();
    private LinearLayoutManager mlinearlayout;
    private StorageReference mImageStorage;
    private messageadapter madapter;
    private static final int GALLERY_PICK=1;

 //   private DatabaseReference mrootref;
   // private Toolbar mchattoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity);
        Intent intent=getIntent();
        ll=(LinearLayout)findViewById(R.id.chat_layout);
        mchatuser = getIntent().getStringExtra("user_name");
        muserid=getIntent().getStringExtra("user_age");
        sendbutt=(ImageButton)findViewById(R.id.sendbutt);
        msg=(EditText) findViewById(R.id.msg);
        sendimage=(ImageButton)findViewById(R.id.add);

        madapter= new messageadapter(messagesList);

        mrootref=FirebaseDatabase.getInstance().getReference();
        mauth=FirebaseAuth.getInstance();
        mcurrentuserid=mauth.getCurrentUser().getUid();
        mmsglist=(RecyclerView)findViewById(R.id.msglist);
        mlinearlayout=new LinearLayoutManager(this);
        mmsglist.setHasFixedSize(true);
        mmsglist.setLayoutManager(mlinearlayout);

        mmsglist.setAdapter(madapter);

        loadmessages();
     //   mchattoolbar=(Toolbar)findViewById(R.id.chat_app_bar);
      //  setSupportActionBar(mchattoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        getSupportActionBar().setTitle(mchatuser);

        mrootref.child("chat").child(mcurrentuserid).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                    if(!dataSnapshot.hasChild(muserid))
                    {
                        Map chataddmap =new HashMap();
                        chataddmap.put("seen",false);
                        chataddmap.put("timestamp", ServerValue.TIMESTAMP);

                        Map chatusermap= new HashMap();
                        chatusermap.put("chat/"+ mcurrentuserid + "/" + muserid,chataddmap);
                        chatusermap.put("chat/"+ muserid +"/"+ mcurrentuserid,chataddmap);
                        mrootref.updateChildren(chatusermap, new DatabaseReference.CompletionListener()
                        {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
                            {
                                if(databaseError!=null )
                                {
                                    Log.d("CHAT_LOG",databaseError.getMessage().toString());
                                }
                            }
                        });
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        sendbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sendmessage();
            }
        });

        sendimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent galleryIntent= new Intent();
                galleryIntent.setType("image/*");//*
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent,"SELECT IMAGE"),GALLERY_PICK);
            }
        });




    }

    private void loadmessages()
    {
        mrootref.child("messages").child(mcurrentuserid).child(muserid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                messages message=dataSnapshot.getValue(messages.class);//*
                messagesList.add(message);
                madapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendmessage()
    {
        String message=msg.getText().toString();
        if(!TextUtils.isEmpty(message))
        {
            String currentuserref="messages/"+ mcurrentuserid+"/"+ muserid;
            String chatuserref="messages/"+ muserid+"/"+ mcurrentuserid;

            DatabaseReference user_message_push = mrootref.child("messages").child(mcurrentuserid).child(muserid).push();

            String pushid=user_message_push.getKey();

            Map messagemap=new HashMap();
            messagemap.put("message",message);
            messagemap.put("type","text");
            messagemap.put("seen",false);
            messagemap.put("time",ServerValue.TIMESTAMP);
            messagemap.put("from",mcurrentuserid);

            Map messageusermap = new HashMap();
            messageusermap.put(currentuserref + "/" + pushid,messagemap);
            messageusermap.put(chatuserref + "/" + pushid,messagemap);

            msg.setText("");

            mrootref.updateChildren(messageusermap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                    if(databaseError!=null )
                    {
                        Log.d("CHAT_LOG",databaseError.getMessage().toString());
                    }
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==GALLERY_PICK && resultCode==RESULT_OK)
        {
            Uri imageUri= data.getData();
            final String current_user_ref="messages/" + mcurrentuserid +"/" + muserid;
            final String chat_user_ref ="messages/" + muserid +"/" + mcurrentuserid;

            DatabaseReference user_message_push= mrootref.child("messages").child(mcurrentuserid).child(muserid).push();

            final String push_id=user_message_push.getKey();

            StorageReference filepath= mImageStorage.child("messages_images").child(push_id + ".jpg");

            filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if(task.isSuccessful()){
                        String downloaad_url = task.getResult().getMetadata().getReference().getDownloadUrl().toString();


                        Map messagemap=new HashMap();
                        messagemap.put("message",downloaad_url);
                        messagemap.put("type","image");
                        messagemap.put("seen",false);
                        messagemap.put("time",ServerValue.TIMESTAMP);
                        messagemap.put("from",mcurrentuserid);

                        Map messageusermap = new HashMap();
                        messageusermap.put(current_user_ref + "/" + push_id,messagemap);
                        messageusermap.put(chat_user_ref + "/" + push_id,messagemap);

                        msg.setText("");

                        mrootref.updateChildren(messageusermap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                                if(databaseError!=null )
                                {
                                    Log.d("CHAT_LOG",databaseError.getMessage().toString());
                                }
                            }
                        });


                    }

                }
            });
        }
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
