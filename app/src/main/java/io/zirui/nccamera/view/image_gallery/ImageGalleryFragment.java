package io.zirui.nccamera.view.image_gallery;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.storage.ShotLoader;
import io.zirui.nccamera.storage.ShotSaver;
import io.zirui.nccamera.utils.ModelUtils;
import io.zirui.nccamera.view.image_viewpager.ImageViewPagerActivity;
import io.zirui.nccamera.view.image_viewpager.ImageViewPagerFragment;

import static android.app.Activity.RESULT_OK;

public class ImageGalleryFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Shot>> {

    public static final int REQ_CODE_IMAGE_DETAIL_EDIT = 100;
    public static final int MATRIX_NUMBER = 3;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private ImageGalleryAdapter adapter;

    @NonNull
    public static ImageGalleryFragment newInstance(){
        return new ImageGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(R.id.loader_id_media_store_data1, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), MATRIX_NUMBER));
        recyclerView.addItemDecoration(new ImageGalleryDecoration(getResources().getDimensionPixelSize((R.dimen.spacing_xsmall))));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_IMAGE_DETAIL_EDIT && resultCode == RESULT_OK) {
            if(data.getBooleanExtra(ImageViewPagerActivity.KEY_SHOT_DELETE, false)){
                ShotSaver shotSaver = ShotSaver.getInstance(getContext());
                shotSaver.handleBigCameraPhoto();
            }
        }
    }

    @Override
    public Loader<List<Shot>> onCreateLoader(int id, Bundle args) {
        return new ShotLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Shot>> loader, List<Shot> data) {
        adapter =
                new ImageGalleryAdapter(getContext(), data, new ImageGalleryAdapter.OnClickImageListener() {
                    @Override
                    public void onClick(int position, List<Shot> data) {
                        Intent intent = new Intent(getContext(), ImageViewPagerActivity.class);
                        intent.putExtra(ImageViewPagerFragment.EXTRA_INITIAL_POS, position);
                        intent.putExtra(ImageViewPagerFragment.EXTRA_IMAGES, ModelUtils.toString(data, new TypeToken<List<Shot>>(){}));
                        startActivityForResult(intent, ImageGalleryFragment.REQ_CODE_IMAGE_DETAIL_EDIT);
                    }
                });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Shot>> loader) {
        // Do nothing.
    }
}
