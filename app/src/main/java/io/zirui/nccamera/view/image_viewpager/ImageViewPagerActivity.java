package io.zirui.nccamera.view.image_viewpager;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.storage.ShotDeletor;
import io.zirui.nccamera.storage.ShotSharer;
import io.zirui.nccamera.view.base.SingleFragmentActivity;


public class ImageViewPagerActivity extends SingleFragmentActivity {
    public static final String KEY_SHOT_TITLE = "shot_title";
    public static final String KEY_SHOT_DELETE = "shot_delete";

    private ImageViewPagerFragment fragment;

    @NonNull
    @Override
    protected Fragment newFragment() {
        fragment = ImageViewPagerFragment.newInstance(getIntent().getExtras());
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
        menu.findItem(R.id.action_share).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                new ShotSharer(getApplicationContext(), fragment.getCurrentItem()).shareImage();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                List<Shot> shotsForDeletion = new ArrayList<>();
                shotsForDeletion.add(fragment.getCurrentItem());
                new ShotDeletor(shotsForDeletion, getApplicationContext()).execute();
                break;
        }
        finish();
        return super.onOptionsItemSelected(item);
    }
}
