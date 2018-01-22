package io.zirui.nccamera.view.camera_panel;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;
import io.zirui.nccamera.storage.Storage;
import io.zirui.nccamera.view.MainActivity;
import io.zirui.nccamera.view.base.BaseFragment;

public class CameraPanelFragment extends BaseFragment {

    public static final String PAGE_TITLE = "Current";

    private CameraPanelAdapter adapter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @NonNull
    public static CameraPanelFragment newInstance(){
        return new CameraPanelFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new CameraPanelDecoration(getResources().getDimensionPixelSize((R.dimen.spacing_small))));
        adapter = new CameraPanelAdapter(new ArrayList<Shot>());
        recyclerView.setAdapter(adapter);
    }

    private List<Shot> mockData(){
        List<Shot> data = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            data.add(new Shot(null));
        }
        return data;
    }

    public void addShot(){
        NewShotTask newShotTask = new NewShotTask();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            newShotTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            newShotTask.execute();
    }

    private class NewShotTask extends AsyncTask<Void, Void, Shot>{

        private Uri uri;

        private NewShotTask(){
            this.uri = Uri.parse(Storage.mCurrentPhotoPath);
        }

        @Override
        protected Shot doInBackground(Void... params) {
            try{
                return new Shot(uri);
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
