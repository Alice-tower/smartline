package com.tower.smartline.push.frags.media;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;

import com.tower.smartline.push.databinding.FragmentGalleryBinding;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * 图片选择Fragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/4/30 7:10
 */
public class GalleryFragment extends BottomSheetDialogFragment {
    private FragmentGalleryBinding mBinding;

    private OnSelectedListener mListener;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new BottomSheetDialog(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentGalleryBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.galleryView.setup(LoaderManager.getInstance(this), count -> {
            // 选中一个图片就隐藏窗口
            if (count > 0) {
                dismiss();
                Uri[] uris = mBinding.galleryView.getSelectedUris();
                if (mListener != null && uris != null && uris.length > 0) {
                    mListener.onSelectedImage(uris[0]);

                    // 取消引用，加快内存回收
                    mListener = null;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    /**
     * 设置事件监听并返回自己
     *
     * @param listener 选中图片监听器
     * @return GalleryFragment自己
     */
    public GalleryFragment setListener(OnSelectedListener listener) {
        mListener = listener;
        return this;
    }

    /**
     * 选中图片监听器
     */
    public interface OnSelectedListener {
        /**
         * 当图片被选中时
         *
         * @param uri 选中图片的资源标志
         */
        void onSelectedImage(Uri uri);
    }
}