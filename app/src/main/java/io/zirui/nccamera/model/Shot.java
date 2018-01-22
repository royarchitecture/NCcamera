package io.zirui.nccamera.model;


import android.net.Uri;

public class Shot {
    public Uri uri;
    public String name;
    public String description;

    public Shot(Uri uri){
        this.uri = uri;
    }

}
