package io.zirui.nccamera.view.image_gallery;


import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.utils.ImageUtils;
import io.zirui.nccamera.view.camera_panel.CameraPanelViewHolder;

public class ImageGalleryAdapter extends RecyclerView.Adapter{

    private List<Shot> data;

    public ImageGalleryAdapter(List<Shot> data){
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shot_card, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Shot shot = data.get(position);
        ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
//        imageViewHolder.imageView.setImageResource(R.drawable.shot_placeholder);
        try {
//            InputStream ims = new FileInputStream(shot.file);
//            imageViewHolder.imageView.setImageBitmap(BitmapFactory.decodeStream(ims));
            imageViewHolder.imageView.setImageBitmap(ImageUtils.getThumnailFromImage(shot.path));
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
}
