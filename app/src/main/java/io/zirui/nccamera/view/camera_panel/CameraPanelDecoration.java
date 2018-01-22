package io.zirui.nccamera.view.camera_panel;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.zirui.nccamera.view.base.SpaceItemDecoration;


public class CameraPanelDecoration extends SpaceItemDecoration {

    public CameraPanelDecoration(int space){
        super(space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;

        if (parent.getChildAdapterPosition(view) == 0){
            outRect.top = space;
        }
    }
}
