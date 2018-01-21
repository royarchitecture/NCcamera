package io.zirui.nccamera.view.camera_panel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import io.zirui.nccamera.view.image_gallery.ImageGalleryAdapter;


public class CameraPanelFragment extends Fragment {

    public static final String PAGE_TITLE = "Current";

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
        ImageGalleryAdapter adapter = new ImageGalleryAdapter(new ArrayList<Shot>());
        recyclerView.setAdapter(adapter);
    }
}
