package io.zirui.nccamera.model;



import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.Random;

public class Shot implements Parcelable {

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
        this.title = "Nature!";
    }

    public Shot(){}

    public Shot(File file, String path){
        this.file = file;
        this.path = path;
        Random rand = new Random();
        this.name = rand.nextInt() + "image";
        this.title = "Nature!";
    }

    protected Shot(Parcel in) {
        name = in.readString();
        path = in.readString();
        title = in.readString();
        rowId = in.readLong();
        uri = in.readParcelable(Uri.class.getClassLoader());
        mimeType = in.readString();
        dateModified = in.readLong();
        orientation = in.readInt();
        dateTaken = in.readLong();
    }

    public static final Creator<Shot> CREATOR = new Creator<Shot>() {
        @Override
        public Shot createFromParcel(Parcel in) {
            return new Shot(in);
        }

        @Override
        public Shot[] newArray(int size) {
            return new Shot[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(path);
        parcel.writeString(title);
        parcel.writeLong(rowId);
        parcel.writeParcelable(uri, i);
        parcel.writeString(mimeType);
        parcel.writeLong(dateModified);
        parcel.writeInt(orientation);
        parcel.writeLong(dateTaken);
    }

    public enum Type {
        IMAGE,
    }

}
