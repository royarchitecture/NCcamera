package io.zirui.nccamera.view.image_gallery;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.utils.ImageUtils;

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
        imageViewHolder.imageView.setImageBitmap(ImageUtils.getProperImage(ImageUtils.getThumbnailFromImage(shot.path), shot.path));
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
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnClickImageListener{
        void onClick(Shot shot);
    }
}
