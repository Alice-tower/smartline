package com.tower.smartline.push;

import android.view.View;

import androidx.annotation.NonNull;

import com.tower.smartline.common.app.Activity;
import com.tower.smartline.push.activities.MainActivity;
import com.tower.smartline.push.databinding.ActivityLauncherBinding;
import com.tower.smartline.push.frags.assist.PermissionsFragment;

public class LauncherActivity extends Activity {
    private ActivityLauncherBinding mBinding;
    @NonNull
    @Override
    protected View initBinding() {
        mBinding = ActivityLauncherBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 检查权限
        if (PermissionsFragment.hasAllPermissions(this, getSupportFragmentManager())) {
            MainActivity.show(this);
            finish();
        }
    }
}