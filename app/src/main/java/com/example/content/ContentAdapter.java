package com.example.content;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.content.dataModel.ContentModel;
import com.example.content.utility.ImageCache;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {
    private final ImageCache imageCache;
    Context context;
    ArrayList<ContentModel> result;

    public ContentAdapter(Context context) {
        this.context = context;
        result = new ArrayList<>();
        imageCache = new ImageCache(context);

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
        imageCache.loadBitmap(url, new ImageCache.ImageLoadCallback() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap) {
                holder.image.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bitmap != null) {
                            holder.image.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        });
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
