package com.meysam.myinsta.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.meysam.myinsta.Classes.MySharedPreference;
import com.meysam.myinsta.Fragments.LoginFragment;
import com.meysam.myinsta.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (MySharedPreference.getInstance(this).getIsLogin()){
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            MainActivity.this.finish();
        }

        getSupportFragmentManager().beginTransaction().add(R.id.main_container , new LoginFragment()).commit();

    }
}
