package com.tower.smartline.push.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tower.smartline.common.app.ToolbarActivity;
import com.tower.smartline.push.R;
import com.tower.smartline.push.databinding.ActivitySearchBinding;
import com.tower.smartline.push.frags.search.SearchGroupFragment;
import com.tower.smartline.push.frags.search.SearchMainFragment;
import com.tower.smartline.push.frags.search.SearchUserFragment;

import com.google.android.material.tabs.TabLayout;

import net.qiujuer.genius.ui.compat.UiCompat;

/**
 * 搜索Activity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/7 15:11
 */
public class SearchActivity extends ToolbarActivity {
    private static final String TAG = SearchActivity.class.getName();

    private static final String EXTRA_TYPE = "EXTRA_TYPE";

    private static final int FRAGS_COUNT = 3;

    /**
     * 综合
     */
    public static final int TYPE_MAIN = 0;

    /**
     * 找人
     */
    public static final int TYPE_USER = 1;

    /**
     * 找群
     */
    public static final int TYPE_GROUP = 2;

    private ActivitySearchBinding mBinding;

    private Fragment[] mFragsArray = new Fragment[FRAGS_COUNT];

    private int mType;

    /**
     * 搜索Activity拉起入口
     *
     * @param context 上下文
     */
    public static void show(Context context, int type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (bundle != null) {
            mType = bundle.getInt(EXTRA_TYPE);
            if (mType == TYPE_MAIN || mType == TYPE_USER || mType == TYPE_GROUP) {
                return super.initArgs(bundle);
            }
        }
        return false;
    }

    @NonNull
    @Override
    protected View initBinding() {
        mBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        // ViewPager初始化
        mBinding.vpContainer.setAdapter(new SearchPagerAdapter(getSupportFragmentManager()));
        mBinding.vpContainer.setCurrentItem(mType, false);

        // TabLayout初始化
        mBinding.tlTab.setTabMode(TabLayout.MODE_FIXED);
        mBinding.tlTab.setTabTextColors(UiCompat.getColor(getResources(), R.color.textSecond),
                UiCompat.getColor(getResources(), R.color.textPrimary));
        mBinding.tlTab.setSelectedTabIndicatorColor(UiCompat.getColor(getResources(), R.color.colorAccent));
        mBinding.tlTab.setupWithViewPager(mBinding.vpContainer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 初始化带有搜索的菜单
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        View actionView = searchItem.getActionView();
        Object sysService = getSystemService(Context.SEARCH_SERVICE);
        if (actionView instanceof SearchView && sysService instanceof SearchManager) {
            ((SearchView) actionView).setSearchableInfo(
                    ((SearchManager) sysService).getSearchableInfo(getComponentName()));
            ((SearchView) actionView).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // 当点击提交按钮时
                    search(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    // 当键入文字改变时
                    return false;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 将搜索任务分配给对应的Fragment
     *
     * @param content 用户键入的内容
     */
    private void search(@Nullable String content) {
        Fragment curFragment = mFragsArray[mBinding.vpContainer.getCurrentItem()];
        if (curFragment instanceof ISearchFragment) {
            ((ISearchFragment) curFragment).search(content);
        }
    }

    private class SearchPagerAdapter extends FragmentPagerAdapter {
        public SearchPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == TYPE_MAIN) {
                if (mFragsArray[TYPE_MAIN] == null) {
                    mFragsArray[TYPE_MAIN] = new SearchMainFragment();
                }
                return mFragsArray[TYPE_MAIN];
            } else if (position == TYPE_USER) {
                if (mFragsArray[TYPE_USER] == null) {
                    mFragsArray[TYPE_USER] = new SearchUserFragment();
                }
                return mFragsArray[TYPE_USER];
            } else if (position == TYPE_GROUP) {
                if (mFragsArray[TYPE_GROUP] == null) {
                    mFragsArray[TYPE_GROUP] = new SearchGroupFragment();
                }
                return mFragsArray[TYPE_GROUP];
            } else {
                Log.w(TAG, "getItem: illegal param: " + position);
                return getItem(TYPE_MAIN);
            }
        }

        @Override
        public int getCount() {
            return FRAGS_COUNT;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == TYPE_MAIN) {
                return getString(R.string.title_search_main);
            } else if (position == TYPE_USER) {
                return getString(R.string.title_search_user);
            } else if (position == TYPE_GROUP) {
                return getString(R.string.title_search_group);
            } else {
                Log.w(TAG, "getPageTitle: illegal param: " + position);
                return "";
            }
        }
    }

    /**
     * 搜索的Fragment必须实现的接口
     */
    public interface ISearchFragment {
        /**
         * 搜索
         *
         * @param content 用户键入的内容
         */
        void search(@Nullable String content);
    }
}
