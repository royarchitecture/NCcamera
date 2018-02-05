package io.zirui.nccamera.storage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.List;

import io.zirui.nccamera.model.Shot;

public class ShotDeletor extends AsyncTask<Void, Void, Boolean> {

    private List<Shot> shots;
    private Context context;

    public ShotDeletor(List<Shot> shots, Context context){
        this.shots = shots;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean hasError = false;
        try {
            for(Shot shot : shots){
                if (!deleteShot(shot.uri)){
                    hasError = true;
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            hasError = true;
        }
        return hasError;
    }

    private boolean deleteShot(Uri uri){
        boolean result = false;
        int numRows = context.getContentResolver().delete(uri, null, null);
        if (numRows == 1){
            lauchFileScan();
            result = true;
        }
        return result;
    }

    private void lauchFileScan() {
        Intent mediaScanIntent = new Intent(
                "android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        mediaScanIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.sendBroadcast(mediaScanIntent);
    }

}
