package io.zirui.nccamera.view.base;


import android.view.View;

import com.bignerdranch.android.multiselector.SwappingHolder;

import butterknife.ButterKnife;

public class BaseViewHolder extends SwappingHolder{

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}