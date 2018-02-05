package io.zirui.nccamera.view.image_detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;


public class ImageFragment extends Fragment {

    public static final String KEY_SHOT = "shot";
    public static final String KEY_DELETE = "delete";

    @BindView(R.id.detailed_image) PhotoView imageView;

    public static ImageFragment newInstance(Shot shot){
        ImageFragment imageFragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_SHOT, shot);
        imageFragment.setArguments(args);
        return imageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shot_item_photoview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Shot shot = getArguments().getParcelable(KEY_SHOT);
        Uri uri = shot.uri;
        Glide.with(getActivity())
                .asBitmap()
                .load(uri)
                .into(imageView);
    }
}

