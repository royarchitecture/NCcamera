package io.zirui.nccamera.model;


import android.net.Uri;

import java.io.File;

public class Shot {
    public Uri uri;
    public String name;
    public String path;
    public File file;
    public String title;

    public Shot(){}

    public Shot(File file, String path){
        this.file = file;
        this.path = path;
        String[] sections = path.split("/");
        this.name = sections[sections.length - 1].split(".")[0];
        this.title = "Nature!";
    }

}
