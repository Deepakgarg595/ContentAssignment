package com.example.content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.content.dataModel.ContentModel;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {
    Context context;
    ArrayList<ContentModel> result;

    public ContentAdapter(Context context) {
        this.context = context;
        result = new ArrayList<>();
    }

    @NonNull
    @Override
    public ContentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ContentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentAdapter.MyViewHolder holder, final int position) {
        String url = result.get(position).getThumbnail().getDomain()+"/"+result.get(position).getThumbnail().getBasePath()+"/0/"+result.get(position).getThumbnail().getKey();
        Glide.with(context)
                .load(url)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void updateList(ArrayList<ContentModel> result) {
        this.result = result;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
