package io.zirui.nccamera.view.base;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    public int space;

    public SpaceItemDecoration(int space){
        this.space = space;
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
