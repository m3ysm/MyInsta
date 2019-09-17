package com.meysam.myinsta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.widget.EmojiEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.meysam.myinsta.Classes.CommentAdapter;
import com.meysam.myinsta.Classes.MySharedPreference;
import com.meysam.myinsta.Data.RetrofitClient;
import com.meysam.myinsta.Models.JsonResponseModel;
import com.meysam.myinsta.Models.postModel;
import com.meysam.myinsta.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private EmojiEditText comment;
    private ImageView send;
    private String postid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.comments_recycler);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(false);
        recyclerView.setLayoutManager(lm);
        send = findViewById(R.id.comments_send);
        comment = findViewById(R.id.comments_comment);

        postid = getIntent().getExtras().getString("postid", "0");
        if (postid.isEmpty() || postid.equals(0)) {
            Toast.makeText(this, "Error,Try Again!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

        getData();
        onClicks();

    }

    private void onClicks() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = comment.getText().toString();
                if (!c.isEmpty()) {
                    RetrofitClient.getInstance(CommentsActivity.this).getApi()
                            .postComment(MySharedPreference.getInstance(CommentsActivity.this).getUser(), c, postid)
                            .enqueue(new Callback<JsonResponseModel>() {
                                @Override
                                public void onResponse(Call<JsonResponseModel> call, Response<JsonResponseModel> response) {
                                    if (response.isSuccessful()) {
                                        comment.setText("");
                                        getData();
                                    } else {
                                        Toast.makeText(CommentsActivity.this, "Error,Try Again!", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<JsonResponseModel> call, Throwable t) {
                                    if (postid.isEmpty() || postid.equals(0)) {
                                        Toast.makeText(CommentsActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    private void getData() {

        RetrofitClient.getInstance(this).getApi().getComments(postid)
                .enqueue(new Callback<postModel>() {
                    @Override
                    public void onResponse(Call<postModel> call, Response<postModel> response) {
                        if (response.isSuccessful()) {
                            adapter = new CommentAdapter(response.body().getData());
                            recyclerView.setAdapter(adapter);
                        } else {
                            if (postid.isEmpty() || postid.equals(0)) {
                                Toast.makeText(CommentsActivity.this, "Error,Try Again!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<postModel> call, Throwable t) {
                        if (postid.isEmpty() || postid.equals(0)) {
                            Toast.makeText(CommentsActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }
                });
    }
}
