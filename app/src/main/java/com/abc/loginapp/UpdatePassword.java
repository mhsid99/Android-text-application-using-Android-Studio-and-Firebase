package com.abc.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity
{
    private Button update;
    private EditText newPassword;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        update=findViewById(R.id.updatepassword);
        newPassword=findViewById(R.id.newpassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String usernewpassword=newPassword.getText().toString();
                firebaseUser.updatePassword(usernewpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(UpdatePassword.this,"Password Successfully Updated",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(UpdatePassword.this,"Update Failed",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });



    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
