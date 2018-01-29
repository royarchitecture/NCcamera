package io.zirui.nccamera.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.zirui.nccamera.R;
import io.zirui.nccamera.camera.Camera;
import io.zirui.nccamera.view.image_gallery.ImageGalleryFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, ImageGalleryFragment.newInstance())
                .commit();

        // App starts with camera interface
        Camera.takePhoto(MainActivity.this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera.takePhoto(MainActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageGalleryFragment imageGallery_page = (ImageGalleryFragment) getSupportFragmentManager().findFragmentById(R.id.content);
            imageGallery_page.addShot();
        }
    }

}
