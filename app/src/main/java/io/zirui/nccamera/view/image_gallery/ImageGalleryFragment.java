package io.zirui.nccamera.view.image_gallery;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.storage.Storage;
import io.zirui.nccamera.view.MainActivity;
import io.zirui.nccamera.view.camera_panel.CameraPanelFragment;
import io.zirui.nccamera.view.image_detail.ImageActivity;
import io.zirui.nccamera.view.image_detail.ImageFragment;
import io.zirui.nccamera.view.image_viewpager.ImageViewPagerFragment;

import static android.app.Activity.RESULT_OK;


public class ImageGalleryFragment extends Fragment {

    public static final String TAG = ImageFragment.class.getSimpleName();
    public static final String PAGE_TITLE = "Gallery";

    public static final int REQ_CODE_IMAGE_DETAIL_EDIT = 100;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private ImageGalleryAdapter adapter;

    @NonNull
    public static ImageGalleryFragment newInstance(){
        return new ImageGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        System.out.println("---------------------ImageGalleryFragment is ok--------------------");
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new ImageGalleryDecoration(getResources().getDimensionPixelSize((R.dimen.spacing_small))));
        Storage storage = Storage.getInstance(getActivity());
        adapter = new ImageGalleryAdapter(Storage.loadData(storage.storageDir), new ImageGalleryAdapter.OnClickImageListener() {
            @Override
            public void onClick(int position, Shot shot, ImageView imageView) {
                ImageViewPagerFragment imageViewPagerFragment = ImageViewPagerFragment.newInstance(position, new ArrayList<>(adapter.data));
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(TAG)
                        .replace(R.id.content, imageViewPagerFragment)
                        .commit();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_CODE_IMAGE_DETAIL_EDIT){
            String shot_path = data.getStringExtra(ImageActivity.KEY_IMAGE_DETAIL_PATH);
            if(shot_path != null){
                refreshShots(shot_path);
            }
        }
    }

    public void addShot(){
        LoadShotTask loadShotTask = new LoadShotTask();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            loadShotTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            loadShotTask.execute();
    }

    public void refreshShots(String shot_path){
        Storage storage = Storage.getInstance(getActivity());
        adapter.refresh(storage.loadData(storage.storageDir));
        CameraPanelFragment cameraPanel_page = (CameraPanelFragment) getActivity()
                                                                    .getSupportFragmentManager()
                                                                    .findFragmentByTag("android:switcher:" + R.id.content + ":" + MainActivity.SECTION_CURRENT);
        cameraPanel_page.refreshShots(shot_path);
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
