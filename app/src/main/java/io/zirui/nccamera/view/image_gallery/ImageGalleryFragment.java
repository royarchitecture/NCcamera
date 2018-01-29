package io.zirui.nccamera.view.image_gallery;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.storage.Storage;
import io.zirui.nccamera.utils.ModelUtils;
import io.zirui.nccamera.view.image_detail.ImageActivity;
import io.zirui.nccamera.view.image_detail.ImageFragment;
import io.zirui.nccamera.view.image_viewpager.ImageViewPagerActivity;
import io.zirui.nccamera.view.image_viewpager.ImageViewPagerFragment;

import static android.app.Activity.RESULT_OK;


public class ImageGalleryFragment extends Fragment {

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
        setRetainInstance(true);
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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new ImageGalleryDecoration(getResources().getDimensionPixelSize((R.dimen.spacing_small))));
        Storage storage = Storage.getInstance(getActivity());
        List<Shot> firstData = Storage.loadData(storage.storageDir);
        System.out.println("--------------------------" + firstData.size() + "--------------------------------");
        adapter = new ImageGalleryAdapter(firstData, new ImageGalleryAdapter.OnClickImageListener() {
            @Override
            public void onClick(int position, Shot shot) {
                Intent intent = new Intent(getContext(), ImageViewPagerActivity.class);
                intent.putExtra(ImageActivity.KEY_SHOT_TITLE, shot.title);
                intent.putExtra(ImageViewPagerFragment.EXTRA_INITIAL_POS, position);
                intent.putExtra(ImageViewPagerFragment.EXTRA_IMAGES, ModelUtils.toString(adapter.data, new TypeToken<List<Shot>>(){}));
                startActivityForResult(intent, ImageGalleryFragment.REQ_CODE_IMAGE_DETAIL_EDIT);
            }
        });
        recyclerView.setAdapter(adapter);
        System.out.println("--------------------------adapter setup--------------------------------");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_IMAGE_DETAIL_EDIT && resultCode == RESULT_OK) {
            if(data.getBooleanExtra(ImageViewPagerActivity.KEY_SHOT_DELETE, false))
            refreshShots();
        }
    }

    private void refreshShots(){
        RefreshShotTask refreshShotTask = new RefreshShotTask();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            refreshShotTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            refreshShotTask.execute();
    }

    public void addShot(){
        LoadShotTask loadShotTask = new LoadShotTask();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            loadShotTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            loadShotTask.execute();
    }

    private class RefreshShotTask extends AsyncTask<Void, Void, List<Shot>>{

        @Override
        protected List<Shot> doInBackground(Void... voids) {
            Storage storage = Storage.getInstance(getActivity());
            return Storage.loadData(storage.storageDir);
        }

        @Override
        protected void onPostExecute(List<Shot> shots) {
            if (shots != null){
                adapter.refreshAll(shots);
            }else{
                Snackbar.make(getView(), "Error", Snackbar.LENGTH_LONG).show();
            }
        }
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
