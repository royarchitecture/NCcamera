package io.zirui.nccamera.view.image_gallery;


import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

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
    private ZoomImageListener zoomImageListener;

    public ImageGalleryAdapter(List<Shot> data, ZoomImageListener zoomImageListener){
        this.data = data;
        this.zoomImageListener = zoomImageListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shot_gallery, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Shot shot = data.get(position);
        final ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        imageViewHolder.thumbnailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageListener.zoomImageFromThumb(imageViewHolder.thumbnail,
                                                     shot,
                                                     imageViewHolder.expandedImageView,
                                                     imageViewHolder.container);
            }
        });
        try {
            imageViewHolder.thumbnail.setImageBitmap(ImageUtils.getThumnailFromImage(shot.path));
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

    public interface ZoomImageListener{
        void zoomImageFromThumb(final View thumbView, Shot shot, final ImageView expandedImageView, FrameLayout container);
    }
}
