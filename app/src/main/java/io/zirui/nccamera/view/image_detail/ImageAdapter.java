package io.zirui.nccamera.view.image_detail;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import io.zirui.nccamera.model.Shot;


public class ImageAdapter extends RecyclerView.Adapter {

    private Shot shot;

    public ImageAdapter(Shot shot){
        this.shot = shot;
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
