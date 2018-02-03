package io.zirui.nccamera.model;


import android.net.Uri;

import java.io.File;
import java.util.Random;

public class Shot {

//    public static final Creator<Shot> CREATOR = new Creator<Shot>() {
//        @Override
//        public MediaStoreData createFromParcel(Parcel parcel) {
//            return new MediaStoreData(parcel);
//        }
//
//        @Override
//        public MediaStoreData[] newArray(int i) {
//            return new MediaStoreData[i];
//        }
//    };

    public String name;
    public String path;
    public File file;
    public String title;

    public long rowId;
    public Uri uri;
    public String mimeType;
    public long dateModified;
    public int orientation;
    private Type type;
    public long dateTaken;

    public Shot(long rowId, Uri uri, String mimeType, long dateTaken, long dateModified,
                   int orientation, Type type) {
        this.rowId = rowId;
        this.uri = uri;
        this.dateModified = dateModified;
        this.mimeType = mimeType;
        this.orientation = orientation;
        this.type = type;
        this.dateTaken = dateTaken;
    }

    public Shot(){}

    public Shot(File file, String path){
        this.file = file;
        this.path = path;
        Random rand = new Random();
        this.name = rand.nextInt() + "image";
        this.title = "Nature!";
    }

    public String toString() {
        return "MediaStoreData{"
                + "rowId=" + rowId
                + ", uri=" + uri
                + ", mimeType='" + mimeType + '\''
                + ", dateModified=" + dateModified
                + ", orientation=" + orientation
                + ", type=" + type
                + ", dateTaken=" + dateTaken
                + '}';
    }

    public enum Type {
        IMAGE,
    }

}
