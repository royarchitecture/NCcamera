package io.zirui.nccamera.view.image_gallery;

import android.view.View;
import android.widget.ImageView;

import com.bignerdranch.android.multiselector.MultiSelector;

import butterknife.BindView;
import io.zirui.nccamera.R;
import io.zirui.nccamera.view.base.BaseViewHolder;


public class ImageViewHolder extends BaseViewHolder implements View.OnLongClickListener{

    @BindView(R.id.shot_card_image) ImageView imageView;
    @BindView(R.id.shot_clickable_cover) View clickableCover;

    private MultiSelector mMultiSelector;

    public ImageViewHolder(View itemView, MultiSelector mMultiSelector) {
        super(itemView);
        this.mMultiSelector = mMultiSelector;
        itemView.setLongClickable(true);
    }

    @Override
    public boolean onLongClick(View view) {
        if (!mMultiSelector.isSelectable()) {
            mMultiSelector.setSelectable(true);
            mMultiSelector.setSelected(ImageViewHolder.this, true);
            return true;
        }
        return false;
    }

}
