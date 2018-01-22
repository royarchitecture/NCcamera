package io.zirui.nccamera.view.camera_panel;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;


public class CameraPanelAdapter extends RecyclerView.Adapter {

    private List<Shot> data;

    public CameraPanelAdapter(List<Shot> data){
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shot_card, parent, false);
        return new CameraPanelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Shot shot = data.get(position);
        CameraPanelViewHolder cameraPanelViewHolder = (CameraPanelViewHolder) holder;
        File file = new File(shot.uri.getPath());
        try {
            InputStream ims = new FileInputStream(file);
            cameraPanelViewHolder.imageView.setImageBitmap(BitmapFactory.decodeStream(ims));
        } catch (FileNotFoundException e) {
            return;
        }
//        cameraPanelViewHolder.imageView.setImageResource(R.drawable.shot_placeholder);
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
