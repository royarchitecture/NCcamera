package io.zirui.nccamera.view.image_detail;

import android.view.View;
import android.widget.ImageView;

import io.zirui.nccamera.view.base.BaseViewHolder;


public class ImageViewHolder extends BaseViewHolder{

    ImageView image;

    public ImageViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView;
    }
}
