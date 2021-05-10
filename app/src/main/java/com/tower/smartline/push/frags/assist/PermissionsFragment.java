package com.tower.smartline.push.frags.assist;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.tower.smartline.common.app.Application;
import com.tower.smartline.push.R;
import com.tower.smartline.push.databinding.FragmentPermissionsBinding;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import java.util.List;
import java.util.Objects;

/**
 * 权限申请弹出框
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/9 5:44
 */
public class PermissionsFragment extends BottomSheetDialogFragment
        implements EasyPermissions.PermissionCallbacks, View.OnClickListener {
    private static final String TAG = PermissionsFragment.class.getName();

    // 申请权限回调标识
    private static final int REQUEST_CODE = 1;

    // 是否拥有全部权限
    private static boolean sHasAllPermissions = false;

    private FragmentPermissionsBinding mBinding;


    public PermissionsFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new BottomSheetDialog(Objects.requireNonNull(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentPermissionsBinding.inflate(inflater, container, false);
        mBinding.btnConfirm.setOnClickListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        // 初始化、后台切回都需要刷新权限状态
        refreshState();
    }

    /**
     * 检查是否具有所有的权限
     *
     * @param context 上下文
     * @param manager FragmentManager
     * @return 是否具有所有的权限
     */
    public static boolean hasAllPermissions(Context context, FragmentManager manager) {
        sHasAllPermissions = hasNetworkPerm(context)
                && hasReadPerm(context)
                && hasWritePerm(context)
                && hasRecordAudioPerm(context);
        if (!sHasAllPermissions) {
            show(manager, false);
        }
        return sHasAllPermissions;
    }

    private void refreshState() {
        Context context = getContext();
        mBinding.imStatePermissionNetwork
                .setVisibility(hasNetworkPerm(context) ? View.VISIBLE : View.GONE);
        mBinding.imStatePermissionRead
                .setVisibility(hasReadPerm(context) ? View.VISIBLE : View.GONE);
        mBinding.imStatePermissionWrite
                .setVisibility(hasWritePerm(context) ? View.VISIBLE : View.GONE);
        mBinding.imStatePermissionRecordAudio
                .setVisibility(hasRecordAudioPerm(context) ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取是否有网络权限
     *
     * @param context 上下文
     * @return 是否有网络权限
     */
    private static boolean hasNetworkPerm(Context context) {
        return EasyPermissions.hasPermissions(context,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE);
    }

    /**
     * 获取是否有读取权限
     *
     * @param context 上下文
     * @return 是否有读取权限
     */
    private static boolean hasReadPerm(Context context) {
        return EasyPermissions.hasPermissions(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    /**
     * 获取是否有写入权限
     *
     * @param context 上下文
     * @return 是否有写入权限
     */
    private static boolean hasWritePerm(Context context) {
        return EasyPermissions.hasPermissions(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 获取是否有录音权限
     *
     * @param context 上下文
     * @return 是否有录音权限
     */
    private static boolean hasRecordAudioPerm(Context context) {
        return EasyPermissions.hasPermissions(context,
                Manifest.permission.RECORD_AUDIO);
    }

    /**
     * 私有show方法
     *
     * @param manager FragmentManager
     * @param isSelf 是否是自身调用
     */
    private static void show(FragmentManager manager, boolean isSelf) {
        // 避免界面多次onResume导致弹出框累积
        if ((isSelf || manager.findFragmentByTag(TAG) == null) && !sHasAllPermissions) {
            new PermissionsFragment()
                    .show(manager, TAG);
        }
    }

    @AfterPermissionGranted(REQUEST_CODE)
    private void requestPerms() {
        String[] perms = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };
        if (EasyPermissions.hasPermissions(Objects.requireNonNull(getContext()), perms)) {
            Application.showToast(R.string.label_permission_ok);
            refreshState();
        } else {
            EasyPermissions.requestPermissions(this,
                    getString(R.string.label_permission_desc), REQUEST_CODE, perms);
        }
    }

    private void onConfirmClick() {
        Log.i(TAG, "onConfirmClick");
        requestPerms();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 权限回调事件向EasyPermissions传递
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        // 权限弹框不允许意外关闭
        FragmentActivity activity = getActivity();
        if (activity != null) {
            show(activity.getSupportFragmentManager(), true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // 存在永久被拒绝的权限，则引导用户前往应用设置界面
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .build()
                    .show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBinding.btnConfirm.getId()) {
            // 确定点击
            onConfirmClick();
        } else {
            Log.w(TAG, "onClick: illegal param");
        }
    }
}