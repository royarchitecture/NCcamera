package io.zirui.nccamera.storage;


import android.content.Context;
import android.content.Intent;

import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;

public class ShotSharer {

    private final Context context;
    private final Shot shot;

    public ShotSharer(Context context, Shot shot){
        this.context = context;
        this.shot = shot;
    }

    public void shareImage(){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shot.title + " " + shot.uri.toString());
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_shot)));
    }
}
