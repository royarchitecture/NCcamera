package io.zirui.nccamera.view.camera_panel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.utils.ImageUtils;


public class CameraPanelAdapter extends RecyclerView.Adapter {

    private List<Shot> data;
//    private HashMap<String, Integer> map;

    public CameraPanelAdapter(List<Shot> data){
        this.data = data;
//        this.map = new HashMap<>();
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
        cameraPanelViewHolder.imageView.setImageBitmap(ImageUtils.getProperImage(ImageUtils.getThumbnailFromImage(shot.path), shot.path));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void prepend(Shot shot){
//        this.map.put(shot.path, data.size());
        this.data.add(0, shot);
        notifyDataSetChanged();
    }

    public void refresh(String path){
//        if (map.containsKey(path)){

//            data.remove((int) map.get(path));
//            map.remove(path);
//        }
        for (int i = 0; i < data.size(); i++){
            if(data.get(i).path.equals(path)){
                data.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }
}
