package io.zirui.nccamera.storage;


import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.zirui.nccamera.model.Shot;


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

    public static List<Shot> loadData(File file) {
        return getAllFiles(file, "jpg");
    }

    private static List<Shot> getAllFiles(File f, String _type) {
        if (!f.exists()) {
            return new ArrayList<>();
        }
        File[] files = f.listFiles();
        if(files==null){
            return new ArrayList<>();
        }
        List<Shot> fileList = new ArrayList<>();
        for (File _file : files) {
            if(_file.isFile() && _file.getName().endsWith(_type)){
                String filePath = _file.getAbsolutePath();
                try {
                    fileList.add(new Shot(_file, filePath));
                }catch (Exception e){
                }
            }
        }
        return fileList;
    }

    public static void createImageFile(@NonNull Activity activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getStorageDir(activity);
        currentFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = currentFile.getAbsolutePath();
    }

    public static void deleteFile(File file) {
        file.delete();
    }
}
