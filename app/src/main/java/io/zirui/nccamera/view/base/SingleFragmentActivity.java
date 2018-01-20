package io.zirui.nccamera.view.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.zirui.nccamera.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
    }
}
