package com.meysam.myinsta.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.button.MaterialButton;
import com.meysam.myinsta.Classes.MySharedPreference;
import com.meysam.myinsta.Data.RetrofitClient;
import com.meysam.myinsta.Models.JsonResponseModel;
import com.meysam.myinsta.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPostActivity extends AppCompatActivity {

    private String path;
    private Bitmap bitmap;
    private MaterialButton save, back, imageSelect;
    private EditText des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_newpost);

        init();


    }

    private void init() {
        imageSelect = findViewById(R.id.newpost_btn);
        save = findViewById(R.id.newPost_save);
        back = findViewById(R.id.newPost_back);
        des = findViewById(R.id.newPost_des);

        onClicks();

    }

    private void onClicks() {
        save.setOnClickListener(v -> {
            try {
                byte[] d = des.getText().toString().getBytes("UTF-8");
                sendNewPost(Base64.encodeToString(d, Base64.DEFAULT));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        });


        back.setOnClickListener(v -> {
            onBackPressed();
        });


        imageSelect.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(NewPostActivity.this);
            builder.setTitle("Select Image");
            builder.setMessage("Choos an image from:");
            builder.setPositiveButton("Camera", (a, b) -> {
                selectFromCamera();
                a.dismiss();
            });
            builder.setNegativeButton("Gallery", (a, b) -> {
                selectFromGallery();
                a.dismiss();
            });
            builder.setNeutralButton("Cancel", (a, b) ->
                    a.dismiss());
            builder.show();
        });
    }

    private String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);

    }

    private void selectFromCamera() {

        Intent intent = new Intent();
        intent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, "com.meysam.myinsta.fileprovider", createFile()));
            startActivityForResult(intent, 02);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createFile() throws IOException {
        String date = new SimpleDateFormat("_yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        File a = File.createTempFile(MySharedPreference.getInstance(this).getUser() + date, ".jpg",
                getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        path = a.getAbsolutePath();
        return a;
    }

    private void selectFromGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), 01);
    }

    private void sendNewPost(String des) {
        String picname = new SimpleDateFormat("_yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String username = MySharedPreference.getInstance(this).getUser();

        RetrofitClient.getInstance(NewPostActivity.this).getApi()
                .newPost(username, des, toBase64(bitmap), username + picname)
                .enqueue(new Callback<JsonResponseModel>() {
                    @Override
                    public void onResponse(Call<JsonResponseModel> call, Response<JsonResponseModel> response) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(NewPostActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(NewPostActivity.this, "error", Toast.LENGTH_SHORT).show();
//                        }
                        switch (response.code()) {
                            case 201:
                                Toast.makeText(NewPostActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                            case 406:
                                Toast.makeText(NewPostActivity.this, "error406", Toast.LENGTH_SHORT).show();
                            case 407:
                                Toast.makeText(NewPostActivity.this, "error407", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponseModel> call, Throwable t) {
                        Toast.makeText(NewPostActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null && requestCode == 01) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewPostActivity.this);
                builder.setTitle("ALLERT");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", (a, b) -> {
                    try {
                        CropImage.activity(data.getData())
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .setAutoZoomEnabled(true)
                                .setAllowRotation(true)
                                .setAllowFlipping(true)
                                .setActivityTitle("Crop Image")
                                .setCropShape(CropImageView.CropShape.RECTANGLE)
                                .setFixAspectRatio(true)
                                .start(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                builder.setNegativeButton("No", (a, b) -> {
                    selectFromGallery();
                    a.dismiss();
                });
                builder.setNeutralButton("Cancel", (a, b) ->
                        a.dismiss());
                builder.show();

            } else if (requestCode == 02) {
                ((ImageView) findViewById(R.id.newpost_img)).setImageURI(Uri.parse(path));
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    ((ImageView) findViewById(R.id.newpost_img)).setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}

