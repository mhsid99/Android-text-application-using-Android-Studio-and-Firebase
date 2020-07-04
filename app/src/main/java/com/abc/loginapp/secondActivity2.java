package com.abc.loginapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.abc.loginapp.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class secondActivity2 extends AppCompatActivity {

    private FirebaseAuth author;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
        //author=FirebaseAuth.getInstance();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        author=FirebaseAuth.getInstance();
    }


    private void Logout()
    {
        author.signOut();
        // finish();
        startActivity(new Intent(secondActivity2.this,MainActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logoutMenu:
            {
                Logout();
                break;
            }
            case R.id.ProfileMenu:
            {
                startActivity(new Intent(secondActivity2.this,ProfileActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}


