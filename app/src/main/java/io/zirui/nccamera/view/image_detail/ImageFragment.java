package io.zirui.nccamera.view.image_detail;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.reflect.TypeToken;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.utils.ImageUtils;
import io.zirui.nccamera.utils.ModelUtils;


public class ImageFragment extends Fragment {

    public static final String KEY_SHOT = "shot";
    public static final String KEY_DELETE = "delete";

    @BindView(R.id.detailed_image) PhotoView imageView;

    public static ImageFragment newInstance(Shot shot){
        ImageFragment imageFragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(KEY_SHOT, ModelUtils.toString(shot, new TypeToken<Shot>(){}));
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
        final Shot shot = new ModelUtils().toObject(getArguments().getString(KEY_SHOT), new TypeToken<Shot>(){});
        System.out.println("----------------------" + shot.path + "----------------------");
        imageView.setImageBitmap(ImageUtils.getProperImage(ImageUtils.getThumbnailFromImage(shot.path), shot.path));
//        Glide.with(getActivity())
//                .asBitmap()
//                .load(new File(Uri.parse(shot.path).getPath()))
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        startPostponedEnterTransition();
//                        imageView.setImageBitmap(resource);
//                    }
//                });
    }
}

