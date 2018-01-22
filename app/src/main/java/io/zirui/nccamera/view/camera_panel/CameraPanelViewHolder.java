package io.zirui.nccamera.view.camera_panel;


import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import io.zirui.nccamera.R;
import io.zirui.nccamera.view.base.BaseViewHolder;

public class CameraPanelViewHolder extends BaseViewHolder{

    @BindView(R.id.shot_card_image) ImageView imageView;

    public CameraPanelViewHolder(View itemView) {
        super(itemView);
    }
}
