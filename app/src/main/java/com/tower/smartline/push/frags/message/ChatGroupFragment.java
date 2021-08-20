package com.tower.smartline.push.frags.message;

import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/**
 * ChatGroupFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/19 15:24
 */
public class ChatGroupFragment extends ChatFragment {
    private static final String TAG = ChatGroupFragment.class.getName();

    public ChatGroupFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    protected MenuItem initToolbarMenu(Toolbar toolbar) {
        return null;
    }

    @Override
    protected void onPortraitClick() {
        Log.i(TAG, "onPortraitClick");
        // TODO 群组模块
    }
}
