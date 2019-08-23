package com.meysam.myinsta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.meysam.myinsta.Classes.MySharedPreference;
import com.meysam.myinsta.R;

public class HomeActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn = findViewById(R.id.home_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreference.getInstance(HomeActivity.this).clearSharedPreference();
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                HomeActivity.this.finish();
            }
        });
    }
}
