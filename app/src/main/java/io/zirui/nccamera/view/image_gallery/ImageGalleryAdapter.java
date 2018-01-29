package io.zirui.nccamera.view.image_gallery;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;

public class ImageGalleryAdapter extends RecyclerView.Adapter{

    public List<Shot> data;
    private OnClickImageListener onClickImageListener;

    public ImageGalleryAdapter(List<Shot> data, OnClickImageListener onClickImageListener){
        this.data = data;
        this.onClickImageListener = onClickImageListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shot_card, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Shot shot = data.get(position);
        final ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        Glide.with(imageViewHolder.imageView.getContext())
                .load(new File(Uri.parse(shot.path).getPath()))
                .thumbnail(0.5f)
                .into(imageViewHolder.imageView);
        imageViewHolder.clickableCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickImageListener.onClick(holder.getAdapterPosition(), shot);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void prepend(Shot shot){
        this.data.add(0, shot);
        notifyDataSetChanged();
    }

    public void refreshAll(List<Shot> shots){
        this.data.clear();
        this.data = shots;
        notifyDataSetChanged();
    }

    public interface OnClickImageListener{
        void onClick(int position, Shot shot);
    }
}
