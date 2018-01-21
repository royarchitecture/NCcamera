package io.zirui.nccamera.view.image_gallery;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import io.zirui.nccamera.model.Shot;

public class ImageGalleryAdapter extends RecyclerView.Adapter{

    private List<Shot> data;

    public ImageGalleryAdapter(List<Shot> data){
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
