package io.zirui.nccamera.view.image_gallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.zirui.nccamera.R;
import io.zirui.nccamera.model.Shot;


public class ImageGalleryFragment extends Fragment {

    public static final String KEY_PAGE = "page";

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @NonNull
    public static ImageGalleryFragment newInstance(int page){
//        Bundle args = new Bundle();
//        args.putInt(KEY_PAGE, page);
//
//        ImageGalleryFragment imageGalleryFragment = new ImageGalleryFragment();
//        imageGalleryFragment.setArguments(args);
        return new ImageGalleryFragment();
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
//        int page = getArguments().getInt(KEY_PAGE);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ImageGalleryAdapter adapter = new ImageGalleryAdapter(new ArrayList<Shot>());
        recyclerView.setAdapter(adapter);
    }
}
