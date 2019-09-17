package com.meysam.myinsta.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meysam.myinsta.Models.postItem;
import com.meysam.myinsta.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<postItem> list;

    public CommentAdapter(List<postItem> list) {
        this.list = list;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        postItem item = list.get(position);
        holder.date.setText(item.getDate());
        holder.comment.setText(item.getComment());
        holder.username.setText(item.getUser_id());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username, comment, date;

        public ViewHolder(@NonNull View v) {
            super(v);

            date = v.findViewById(R.id.row_comment_date);
            username = v.findViewById(R.id.row_comment_username);
            comment = v.findViewById(R.id.row_comment_comment);
        }
    }

}
