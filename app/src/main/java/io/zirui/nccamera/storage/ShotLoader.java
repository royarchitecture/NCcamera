package io.zirui.nccamera.storage;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.zirui.nccamera.model.Shot;

public class ShotLoader extends AsyncTaskLoader<List<Shot>> {

    private static final String[] IMAGE_PROJECTION =
            new String[] {
                    MediaStore.Images.ImageColumns._ID,
                    MediaStore.Images.ImageColumns.DATE_TAKEN,
                    MediaStore.Images.ImageColumns.DATE_MODIFIED,
                    MediaStore.Images.ImageColumns.MIME_TYPE,
                    MediaStore.Images.ImageColumns.ORIENTATION,
            };

    private List<Shot> cached;
    private boolean observerRegistered = false;
    private final ForceLoadContentObserver forceLoadContentObserver = new ForceLoadContentObserver();
    private ShotSaver shotSaver;

    public ShotLoader(Context context) {
        super(context);
        shotSaver = ShotSaver.getInstance(context);
    }

    public void deliverResult(List<Shot> data) {
        if (!isReset() && isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (cached != null) {
            deliverResult(cached);
        }
        if (takeContentChanged() || cached == null) {
            forceLoad();
        }
        registerContentObserver();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();
        cached = null;
        unregisterContentObserver();
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
        unregisterContentObserver();
    }

    @Override
    public List<Shot> loadInBackground() {
        List<Shot> data = queryImages();
        Collections.sort(data, new Comparator<Shot>() {
            @Override
            public int compare(Shot mediaStoreData, Shot mediaStoreData2) {
                return Long.valueOf(mediaStoreData2.dateTaken).compareTo(mediaStoreData.dateTaken);
            }
        });
        return data;
    }

    private List<Shot> queryImages() {
        return query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns.DATE_MODIFIED,
                MediaStore.Images.ImageColumns.MIME_TYPE, MediaStore.Images.ImageColumns.ORIENTATION,
                Shot.Type.IMAGE);
    }

    private List<Shot> query(Uri contentUri, String[] projection, String sortByCol,
                                       String idCol, String dateTakenCol, String dateModifiedCol, String mimeTypeCol,
                                       String orientationCol, Shot.Type type) {
        final List<Shot> data = new ArrayList<>();
        String selection = MediaStore.Images.Media.DATA + " like ?";
        String path = shotSaver.getAlbumDir().toString();
        String[] selectionArgs = {path + "%"};
        // Cursor cursor = getContext().getContentResolver()
        //         .query(contentUri, projection, null, null, sortByCol + " DESC");
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Files.getContentUri( "external" ) ,projection,
                selection, selectionArgs, sortByCol + " DESC");

        if (cursor == null) {
            return data;
        }

        try {
            final int idColNum = cursor.getColumnIndexOrThrow(idCol);
            final int dateTakenColNum = cursor.getColumnIndexOrThrow(dateTakenCol);
            final int dateModifiedColNum = cursor.getColumnIndexOrThrow(dateModifiedCol);
            final int mimeTypeColNum = cursor.getColumnIndex(mimeTypeCol);
            final int orientationColNum = cursor.getColumnIndexOrThrow(orientationCol);

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColNum);
                long dateTaken = cursor.getLong(dateTakenColNum);
                String mimeType = cursor.getString(mimeTypeColNum);
                long dateModified = cursor.getLong(dateModifiedColNum);
                int orientation = cursor.getInt(orientationColNum);

                data.add(new Shot(id, Uri.withAppendedPath(contentUri, Long.toString(id)),
                        mimeType, dateTaken, dateModified, orientation, type));
            }
        } finally {
            cursor.close();
        }

        return data;
    }

    private void registerContentObserver() {
        if (!observerRegistered) {
            ContentResolver cr = getContext().getContentResolver();
            cr.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false,
                    forceLoadContentObserver);
            cr.registerContentObserver(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, false,
                    forceLoadContentObserver);

            observerRegistered = true;
        }
    }

    private void unregisterContentObserver() {
        if (observerRegistered) {
            observerRegistered = false;

            getContext().getContentResolver().unregisterContentObserver(forceLoadContentObserver);
        }
    }
}
