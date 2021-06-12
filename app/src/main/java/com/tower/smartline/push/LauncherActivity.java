package com.tower.smartline.push;

import android.app.AlertDialog;
import android.view.View;

import androidx.annotation.NonNull;

import com.tower.smartline.common.Config;
import com.tower.smartline.common.app.Activity;
import com.tower.smartline.push.activities.AccountActivity;
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

        // 检查网络配置相关参数 引导开发者配置Config参数
        if (Config.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.launcher_alert_title)
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage(R.string.launcher_alert_message)
                    .setPositiveButton(R.string.launcher_alert_confirm, (dialog, which) -> finish())
                    .create().show();
            return;
        }

        // 检查权限 引导用户开启所需的权限
        if (PermissionsFragment.hasAllPermissions(this, getSupportFragmentManager())) {
            AccountActivity.show(this);
            finish();
        }
    }
}