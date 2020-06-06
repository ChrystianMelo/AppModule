package com.example.appmodule.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmodule.R;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {
    private Context ct;
    private String[] title;
    private String[] content;
    private int[] images;

    public FeedAdapter(Context ct, String[] title, String[] content, int[] images){
        this.ct = ct;
        this.title = title;
        this.content = content;
        this.images = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.ct);
        View view = inflater.inflate(R.layout.feed_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.v1.setText(this.title[position]);
        holder.v2.setText(this.content[position]);
        holder.img.setImageResource(this.images[position]);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView v1,v2;
        ImageView img;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            v1 = itemView.findViewById(R.id.txt_feed_title);
            v2 = itemView.findViewById(R.id.txt_feed_comments);
            img = itemView.findViewById(R.id.img_feed_post);
        }
    }
}
