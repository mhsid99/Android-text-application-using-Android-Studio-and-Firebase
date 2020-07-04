package com.abc.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class usersnexttry extends AppCompatActivity  {


    ListView lv;
    public FirebaseAuth auth;
    public FirebaseDatabase getref;
    Map EmailtoUid = new HashMap();
    FirebaseListAdapter adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersnexttry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv=(ListView) findViewById(R.id.listview);


        final Query query = FirebaseDatabase.getInstance().getReference().child("user");
        FirebaseListOptions< student>options = new FirebaseListOptions.Builder<student>()
                .setLayout(R.layout.student)       //doubt
                .setLifecycleOwner(usersnexttry.this)
                .setQuery(query,student.class)
                .build();

        adaptor = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {

                TextView user_name = v.findViewById(R.id.user_name);
                TextView user_age = v.findViewById(R.id.user_age);
                student std= (student) model;
                String dedo_please=getRef(position).getKey();
                user_name.setText("Name: "+std.getUser_name().toString());
                user_age.setText("Age: "+std.getUser_age().toString());
                String Emailid=std.getUser_email().toString();
                EmailtoUid.put(Emailid,dedo_please);

            }


        };
    /*    lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = lv.getItemAtPosition(position);
                String str=(String)o;//As you are using Default String Adapter
/* write you handling code like...
// do whatever u want to do with 'f' File object
            }
        });*/
        lv.setAdapter(adaptor);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //  DatabaseReference fd1;
                 // FirebaseAuth mauth1;
                  //fd1=FirebaseDatabase.getInstance().getReference();
                  //mauth1=FirebaseAuth.getInstance();

                student yourModel;
              // String uid=firebaseRef.getAuth().getUid();
                yourModel = (student) lv.getAdapter().getItem(i);
                String karado=yourModel.getUser_name();
                String karado1=yourModel.getUser_email();
                String dedo_please= (String) EmailtoUid.get(karado1);
               // String dedo_please=
                //final Query query2 = FirebaseDatabase.getInstance().getReference().child()
              // Toast.makeText(usersnexttry.this,karado2,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(),chatactivity.class);

                intent.putExtra("user_name",karado);
               intent.putExtra("user_age",dedo_please);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        adaptor.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptor.stopListening();
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
