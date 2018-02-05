package io.zirui.nccamera.view.image_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.reflect.TypeToken;

import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.storage.Storage;
import io.zirui.nccamera.utils.ModelUtils;
import io.zirui.nccamera.view.base.SingleFragmentActivity;


public class ImageActivity extends SingleFragmentActivity {

    public static final String KEY_SHOT_TITLE = "shot_title";

    public Bundle bundle;

    @NonNull
    @Override
    protected Fragment newFragment() {
        Shot shot = new Shot();
        return ImageFragment.newInstance(shot);
    }

    @NonNull
    @Override
    protected String getActivityTitle() {
        return getIntent().getStringExtra(KEY_SHOT_TITLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete){
            Shot shot = new ModelUtils().toObject(bundle.getString(ImageFragment.KEY_SHOT), new TypeToken<Shot>(){});
            Storage.deleteFile(shot.file);
            Intent resultData = new Intent();
            setResult(RESULT_OK, resultData);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

