package com.meysam.myinsta.Classes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.meysam.myinsta.Activities.CommentsActivity;
import com.meysam.myinsta.Models.postItem;
import com.meysam.myinsta.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<postItem> list;

    public PostAdapter (Context context , List<postItem> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        postItem item = list.get(position);
        holder.des.setText(new String(Base64.decode(item.getDes(),Base64.DEFAULT)));
        holder.user.setText(item.getUser_id());
        holder.pic.setImageURI(Uri.parse(context.getString(R.string.image_address,item.getImage())));
        holder.date.setText(item.getDate());
        holder.id.setText(item.getId());
        holder.commentCount.setText(item.getCommentCount()+"");
        holder.comments.setOnClickListener(v->{
            Intent intent = new Intent(context, CommentsActivity.class);
            intent.putExtra("postid",item.getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView des , user,date,id,commentCount;
        private SimpleDraweeView pic;
        private ImageView comments;

        public ViewHolder(@NonNull View v) {
            super(v);

            des = v.findViewById(R.id.row_post_des);
            user = v.findViewById(R.id.row_post_user);
            pic = v.findViewById(R.id.row_post_img);
            date = v.findViewById(R.id.row_post_date);
            id = v.findViewById(R.id.row_post_id);
            commentCount = v.findViewById(R.id.row_post_comment_count);
            comments = v.findViewById(R.id.row_post_comment);
        }
    }

}
