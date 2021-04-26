package com.tower.smartline.push.frags.main;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;

import com.tower.smartline.common.app.Fragment;
import com.tower.smartline.common.widget.GalleryView;
import com.tower.smartline.push.R;

import butterknife.BindView;

/**
 * HomeFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/4/23 0:35
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.galleryView)
    GalleryView mGalleryView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
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
        mGalleryView.setup(LoaderManager.getInstance(this), count -> {
        });
    }
}