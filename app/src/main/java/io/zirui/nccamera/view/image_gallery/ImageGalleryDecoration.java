package io.zirui.nccamera.view.image_gallery;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.zirui.nccamera.view.base.SpaceItemDecoration;


public class ImageGalleryDecoration extends SpaceItemDecoration {

    public ImageGalleryDecoration(int space){
        super(space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
        outRect.left = space;

        if (parent.getChildAdapterPosition(view) <= ImageGalleryFragment.MATRIX_NUMBER - 1){
            outRect.top = space;
        }

        if ((parent.getChildAdapterPosition(view) + 1) % ImageGalleryFragment.MATRIX_NUMBER == 0){
            outRect.right = space;
        }
    }
}
