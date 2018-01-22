package io.zirui.nccamera.model;


import android.net.Uri;

import java.io.File;

public class Shot {
    public Uri uri;
    public String name;
    public String description;
    public File file;

    public Shot(File file){
        this.file = file;
    }

}
