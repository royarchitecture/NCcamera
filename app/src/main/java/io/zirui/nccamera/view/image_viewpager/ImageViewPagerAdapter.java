package io.zirui.nccamera.view.image_viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.view.image_detail.ImageFragment;


public class ImageViewPagerAdapter extends FragmentStatePagerAdapter {

    public List<Shot> shots;

    public ImageViewPagerAdapter(FragmentManager fm, List<Shot> shots) {
        super(fm);
        this.shots = shots;
    }

    @Override
    public Fragment getItem(int position) {
        Shot shot = shots.get(position);
        return ImageFragment.newInstance(shot);
    }

    @Override
    public int getCount() {
        return shots.size();
    }

    public void refresh(List<Shot> data){
        this.shots.clear();
        this.shots.addAll(data);
        notifyDataSetChanged();
    }
}
