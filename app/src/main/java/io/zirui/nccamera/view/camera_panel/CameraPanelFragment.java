package io.zirui.nccamera.view.camera_panel;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.storage.Storage;

public class CameraPanelFragment extends Fragment {

    public static final String PAGE_TITLE = "Current";

    private CameraPanelAdapter adapter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @NonNull
    public static CameraPanelFragment newInstance(){
        return new CameraPanelFragment();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new CameraPanelDecoration(getResources().getDimensionPixelSize((R.dimen.spacing_small))));
        adapter = new CameraPanelAdapter(new ArrayList<Shot>());
        recyclerView.setAdapter(adapter);
    }

    public void addShot(){
        NewShotTask newShotTask = new NewShotTask();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            newShotTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            newShotTask.execute();
    }

    public void refreshShots(String path){
        adapter.refresh(path);
    }

    private class NewShotTask extends AsyncTask<Void, Void, Shot>{

        @Override
        protected Shot doInBackground(Void... params) {
            try{
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
                Snackbar.make(getView(), "You should take a shot", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
