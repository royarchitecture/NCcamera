package io.zirui.nccamera.view.base.gesture_views;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.alexvasilkov.gestures.Settings;

import io.zirui.nccamera.view.base.gesture_views.settings.SettingsController;
import io.zirui.nccamera.view.base.gesture_views.settings.SettingsMenu;


public abstract class BaseSettingsActivity extends BaseActivity{

    private final SettingsMenu settingsMenu = new SettingsMenu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsMenu.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBarNotNull().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        settingsMenu.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        settingsMenu.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (settingsMenu.onOptionsItemSelected(item)) {
            supportInvalidateOptionsMenu();
            onSettingsChanged();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected SettingsController getSettingsController() {
        return settingsMenu;
    }

    protected abstract void onSettingsChanged();

    protected void setDefaultSettings(Settings settings) {
        settingsMenu.setValuesFrom(settings);
        invalidateOptionsMenu();
    }
}
