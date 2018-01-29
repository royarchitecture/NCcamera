package io.zirui.nccamera.view.image_viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.storage.Storage;
import io.zirui.nccamera.utils.ModelUtils;
import io.zirui.nccamera.view.base.SingleFragmentActivity;
import io.zirui.nccamera.view.image_detail.ImageFragment;


public class ImageViewPagerActivity extends SingleFragmentActivity {
    public static final String KEY_SHOT_TITLE = "shot_title";
    public static final String KEY_SHOT_DELETE = "shot_delete";

    private ImageViewPagerFragment fragment;

    @NonNull
    @Override
    protected Fragment newFragment() {
        Bundle bundle = getIntent().getExtras();
        fragment = ImageViewPagerFragment.newInstance(bundle.getInt(ImageViewPagerFragment.EXTRA_INITIAL_POS),
                                          ModelUtils.toObject(bundle.getString(ImageViewPagerFragment.EXTRA_IMAGES), new TypeToken<List<Shot>>(){
                                          }));
        return fragment;
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
            fragment.deleteCurrentItem();
            Intent resultData = new Intent();
            resultData.putExtra(KEY_SHOT_DELETE, true);
            setResult(RESULT_OK, resultData);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
