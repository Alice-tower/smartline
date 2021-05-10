package com.tower.smartline.push.frags.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

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
    protected void destroyBinding() {
        mBinding = null;
    }
}