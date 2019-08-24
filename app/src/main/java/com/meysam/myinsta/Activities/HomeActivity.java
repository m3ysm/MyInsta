package com.meysam.myinsta.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.meysam.myinsta.Classes.MySharedPreference;
import com.meysam.myinsta.Classes.newPostDialog;
import com.meysam.myinsta.R;

import java.io.File;

public class HomeActivity extends AppCompatActivity {

    Button btn;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    private void init(){
        fab =findViewById(R.id.home_fab);
        btn = findViewById(R.id.home_btn);

        onClicks();
    }

    private void onClicks(){
        btn.setOnClickListener(v->{
            MySharedPreference.getInstance(HomeActivity.this).clearSharedPreference();
            startActivity(new Intent(HomeActivity.this,MainActivity.class));
            HomeActivity.this.finish();
        });

        fab.setOnClickListener(v-> {
            if (checkPermission() != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showExplanation();
                } else if (!MySharedPreference.getInstance(HomeActivity.this).getWriteExternal()) {
                    requestPermission();
                    MySharedPreference.getInstance(HomeActivity.this).setWriteExternal();
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }else {
                gotoDialog();
            }
        });
    }

    private void showExplanation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Permission Needed");
        builder.setMessage("Please Allow This Permission");
        builder.setPositiveButton("Allow",(a,b)->{
            requestPermission();
        });
        builder.setNegativeButton("Dont Allow",(a,b)->{
           a.dismiss();
        });
        builder.show();
    }

    private void gotoDialog() {
    }

    private int checkPermission(){
        return ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123){
            if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                gotoDialog();
            }
        }
    }
}