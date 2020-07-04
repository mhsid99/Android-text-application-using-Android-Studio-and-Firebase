package com.abc.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class passwordactivity extends AppCompatActivity {
    private EditText emailpassword;
    private Button passwordreset;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordactivity);
        emailpassword=(EditText)findViewById(R.id.passwordemail);
        passwordreset=(Button)findViewById(R.id.passwordreset);
        auth=FirebaseAuth.getInstance();
        passwordreset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email=emailpassword.getText().toString().trim();
                if(email.equals(""))
                {
                    Toast.makeText(passwordactivity.this,"Please enter your registered email ID", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(passwordactivity.this,"Password reset link sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(passwordactivity.this, MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(passwordactivity.this,"Reset link Failed,Please check your connection and try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
