package io.zirui.nccamera.view.image_viewpager;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.utils.ModelUtils;


public class ImageViewPagerFragment extends Fragment {

    private static final String EXTRA_INITIAL_POS = "initial_pos";
    private static final String EXTRA_IMAGES = "images";

    @BindView(R.id.shot_view_pager) ViewPager viewPager;

    public static ImageViewPagerFragment newInstance(int currentPos, List<Shot> shots){
        ImageViewPagerFragment imageViewPagerFragment = new ImageViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_INITIAL_POS, currentPos);
        args.putString(EXTRA_IMAGES, ModelUtils.toString(shots, new TypeToken<List<Shot>>(){}));
        imageViewPagerFragment.setArguments(args);
        return imageViewPagerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
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
        int currentPos = getArguments().getInt(EXTRA_INITIAL_POS);
        List<Shot> shots = ModelUtils.toObject(getArguments().getString(EXTRA_IMAGES), new TypeToken<List<Shot>>(){});
        ImageViewPagerAdapter imageViewPagerAdapter = new ImageViewPagerAdapter(getChildFragmentManager(), shots);
        viewPager.setAdapter(imageViewPagerAdapter);
        viewPager.setCurrentItem(currentPos);
    }
}
