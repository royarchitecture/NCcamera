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
import io.zirui.nccamera.storage.Storage;

public class Camera {

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    public static void takePhoto(@NonNull Activity activity){
        try {
            dispatchTakePictureIntent(activity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dispatchTakePictureIntent(@NonNull Activity activity) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile;
            try {
                photoFile = Storage.createImageFile(activity);
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity.getBaseContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        Storage.createImageFile(activity));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

}
