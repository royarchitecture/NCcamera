package io.zirui.nccamera.view.image_viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.storage.ShotLoader;
import io.zirui.nccamera.storage.Storage;
import io.zirui.nccamera.utils.ModelUtils;


public class ImageViewPagerFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Shot>>{

    public static final String EXTRA_INITIAL_POS = "initial_pos";
    public static final String EXTRA_IMAGES = "images";

    public ImageViewPagerAdapter adapter;

    @BindView(R.id.shot_view_pager) ViewPager viewPager;

    public static ImageViewPagerFragment newInstance(Bundle bundle){
        ImageViewPagerFragment imageViewPagerFragment = new ImageViewPagerFragment();
//        Bundle args = new Bundle();
//        args.putInt(EXTRA_INITIAL_POS, currentPos);
        imageViewPagerFragment.setArguments(bundle);
        return imageViewPagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(R.id.loader_id_media_store_data2, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Shot> data = ModelUtils.toObject(getArguments().getString(EXTRA_IMAGES), new TypeToken<List<Shot>>(){});
        int currentPos = getArguments().getInt(EXTRA_INITIAL_POS);
        adapter = new ImageViewPagerAdapter(getChildFragmentManager(), data);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPos);
    }

    public void deleteCurrentItem(){
        Storage.deleteFile(adapter.shots.get(viewPager.getCurrentItem()).file);
    }

    @Override
    public Loader<List<Shot>> onCreateLoader(int id, Bundle args) {
        return new ShotLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Shot>> loader, List<Shot> data) {
//        adapter.refresh(data);

//        viewPager.setCurrentItem(currentPos);
    }

    @Override
    public void onLoaderReset(Loader<List<Shot>> loader) {

    }
}
