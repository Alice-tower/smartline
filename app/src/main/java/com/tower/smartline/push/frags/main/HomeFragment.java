package com.tower.smartline.push.frags.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;

import com.tower.smartline.common.app.Fragment;
import com.tower.smartline.push.databinding.FragmentHomeBinding;

/**
 * HomeFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/4/23 0:35
 */
public class HomeFragment extends Fragment {
    private FragmentHomeBinding mBinding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    protected View initBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        if (mBinding == null) {
            mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        }
        return mBinding.getRoot();
    }

    @Override
    protected void initData() {
        super.initData();

        // TODO 后期统一处理权限，此处仅为调试图片选择器效果
        if (getContext() != null && getActivity() != null) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
        mBinding.galleryView.setup(LoaderManager.getInstance(this), count -> {
        });
    }

    @Override
    protected void destroyBinding() {
        mBinding = null;
    }
}