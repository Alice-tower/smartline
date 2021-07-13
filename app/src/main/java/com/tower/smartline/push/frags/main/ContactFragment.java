package com.tower.smartline.push.frags.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.tower.smartline.common.app.BaseFragment;
import com.tower.smartline.push.databinding.FragmentContactBinding;

/**
 * ContactFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/4/23 0:35
 */
public class ContactFragment extends BaseFragment {
    private FragmentContactBinding mBinding;

    public ContactFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    protected View initBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        if (mBinding == null) {
            mBinding = FragmentContactBinding.inflate(inflater, container, false);
        }
        return mBinding.getRoot();
    }

    @Override
    protected void destroyBinding() {
        mBinding = null;
    }
}