package com.abc.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static java.lang.Boolean.FALSE;

public class MainActivity extends AppCompatActivity
{
    private EditText name;
    private EditText password;
    private Button Login;
    private TextView attempt;
    private int c=5;
    private TextView regge;
    private FirebaseAuth autho;
    private ProgressDialog pd;
    private TextView forgotpass;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText)findViewById(R.id.etusername);
        password = (EditText)findViewById(R.id.etpassword);
        Login = (Button)findViewById(R.id.login);
        attempt = (TextView) findViewById(R.id.attempts);
        regge =(TextView)findViewById(R.id.reg);
        forgotpass=(TextView)findViewById(R.id.forgotpassword);
        attempt.setText("No of Attempts remaining:5");
        pd=new ProgressDialog(this);
        autho=FirebaseAuth.getInstance();
        FirebaseUser user=autho.getCurrentUser();
        if (user!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,secondActivity.class));
        }

        Login.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view)
            {
                validate(name.getText().toString(),password.getText().toString());
            }
        });
        regge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this,Registration.class));
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this,passwordactivity.class));
            }
        });


    }
    protected void validate(String un , String p)
    {
        pd.setMessage("Please Wait");
        pd.show();
        autho.signInWithEmailAndPassword(un, p).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    pd.dismiss();

                    //Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    checkp();

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    c--;
                    pd.dismiss();
                    attempt.setText("No Of Attempts Remaining: "+c);
                    if (c==0)
                    {
                        Login.setEnabled(false);
                    }
                }
            }
        });


    }
    private void checkp()
    {
        FirebaseUser fb=autho.getInstance().getCurrentUser();
        boolean eflag= fb.isEmailVerified();
        if(eflag)
        {
            finish();
            startActivity(new Intent(MainActivity.this,secondActivity.class));

        }
        else
        {
            Toast.makeText(this,"Please verify Your Email",Toast.LENGTH_SHORT).show();
            autho.signOut();
        }
    }
}