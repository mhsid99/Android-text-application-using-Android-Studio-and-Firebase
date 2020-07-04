package com.abc.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Mainnewspage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainnewspage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu newsmenu)
    {
        getMenuInflater().inflate(R.menu.newsmenu, newsmenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.EspnMenu:
            {
                startActivity(new Intent(Mainnewspage.this,Sportsnewsact.class));

                break;
            }
            case R.id.ignMenu:
            {
                startActivity(new Intent(Mainnewspage.this,ignnewsact.class));
                break;
            }

            case R.id.SciencentechMenu:
            {
                startActivity(new Intent(Mainnewspage.this,technewsact.class));
                break;
            }
            case R.id.cnnMenu:
            {
                startActivity(new Intent(Mainnewspage.this,newsact.class));
                break;
            }


        }
        return super.onOptionsItemSelected(item);
    }

}
