package com.tower.smartline.push.frags.message;

import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.tower.smartline.push.R;
import com.tower.smartline.push.activities.PersonalActivity;

/**
 * ChatUserFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/19 15:24
 */
public class ChatUserFragment extends ChatFragment {
    private static final String TAG = ChatUserFragment.class.getName();

    public ChatUserFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    protected MenuItem initToolbarMenu(Toolbar toolbar) {
        if (toolbar == null) {
            return null;
        }
        toolbar.inflateMenu(R.menu.chat_user);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_person) {
                onPortraitClick();
                return true;
            }
            return false;
        });
        return toolbar.getMenu().findItem(R.id.action_person);
    }

    @Override
    protected void onPortraitClick() {
        Log.i(TAG, "onPortraitClick");
        PersonalActivity.show(requireContext(), mReceiverId, true);
    }
}
