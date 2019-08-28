package com.meysam.myinsta.Classes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.meysam.myinsta.R;

public class NewPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_newpost);

        findViewById(R.id.newpost_btn).setOnClickListener(v -> {
            selectFromGallery();
        });
    }

    private void selectFromGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), 01);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null && requestCode == 01) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewPostActivity.this);
                builder.setTitle("ALLERT");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", (a, b) ->
                        ((ImageView) findViewById(R.id.newpost_img)).setImageURI(data.getData()));
                builder.setNegativeButton("No", (a, b) -> {
                    selectFromGallery();
                    a.dismiss();
                });
                builder.setNeutralButton("Cancel", (a, b) ->
                        a.dismiss());
                builder.show();
            }
        }

    }
}

