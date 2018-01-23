package io.zirui.nccamera.view.image_gallery;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.storage.Storage;
import io.zirui.nccamera.view.base.BaseFragment;


public class ImageGalleryFragment extends BaseFragment {

    public static final String PAGE_TITLE = "Gallery";

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private ImageGalleryAdapter adapter;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

    @NonNull
    public static ImageGalleryFragment newInstance(){
        return new ImageGalleryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new ImageGalleryDecoration(getResources().getDimensionPixelSize((R.dimen.spacing_small))));
        Storage storage = Storage.getInstance(getActivity());
        adapter = new ImageGalleryAdapter(Storage.loadData(storage.storageDir));
        recyclerView.setAdapter(adapter);
    }

    public void addShot(){
        LoadShotTask loadShotTask = new LoadShotTask();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            loadShotTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            loadShotTask.execute();
    }

    private class LoadShotTask extends AsyncTask<Void, Void, Shot>{

        @Override
        protected Shot doInBackground(Void... voids) {
            try {
                return new Shot(Storage.currentFile, Storage.mCurrentPhotoPath);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Shot shot) {
            if (shot != null){
                adapter.prepend(shot);
            }else{
                Snackbar.make(getView(), "Error", Snackbar.LENGTH_LONG).show();
            }
        }
    }

}
