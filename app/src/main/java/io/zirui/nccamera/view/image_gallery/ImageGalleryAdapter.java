package io.zirui.nccamera.view.image_gallery;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.utils.ImageUtils;
import io.zirui.nccamera.utils.ModelUtils;
import io.zirui.nccamera.view.camera_panel.CameraPanelViewHolder;
import io.zirui.nccamera.view.image_detail.ImageActivity;
import io.zirui.nccamera.view.image_detail.ImageFragment;

public class ImageGalleryAdapter extends RecyclerView.Adapter{

    private List<Shot> data;
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
        ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        imageViewHolder.clickableCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickImageListener != null){
                    onClickImageListener.onClick(shot);
                }
            }
        });
        try {
            imageViewHolder.imageView.setImageBitmap(ImageUtils.getProperImage(ImageUtils.getThumnailFromImage(shot.path), shot.path));
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void prepend(Shot shot){
        this.data.add(0, shot);
        notifyDataSetChanged();
    }

    public void refresh(List<Shot> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public interface OnClickImageListener{
        void onClick(Shot shot);
    }
}
