package io.zirui.nccamera.camera;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;

import io.zirui.nccamera.BuildConfig;
import io.zirui.nccamera.storage.ShotSaver;

public class Camera {

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    public static void takePhoto(@NonNull Activity activity, ShotSaver shotSaver){
        try {
            dispatchTakePictureIntent(activity, shotSaver);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dispatchTakePictureIntent(@NonNull Activity activity, ShotSaver shotSaver) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile;
            try {
                photoFile = shotSaver.createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity.getBaseContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

}
