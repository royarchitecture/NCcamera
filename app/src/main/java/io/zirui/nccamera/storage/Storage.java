package io.zirui.nccamera.storage;


import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Storage {

    public static String mCurrentPhotoPath;
    public static File currentFile;

    public File storageDir;

    private static Storage sInstance;

    public static String folderName = "NCcamera";

    private Context context;

    private Storage(Context context){
        this.context = context;
        this.storageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), folderName);
    }

    public static synchronized Storage getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Storage(context.getApplicationContext());
        }
        return sInstance;
    }

    public static File getStorageDir(@NonNull Activity activity){
        File storageDir = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), folderName);
        if(!storageDir.exists()){
            storageDir.mkdir();
        }
        return storageDir;
    }

    public static List<File> loadData(File file) {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return new ArrayList<File>(Arrays.asList(file.listFiles()));
    }

    public static File createImageFile(@NonNull Activity activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getStorageDir(activity);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
