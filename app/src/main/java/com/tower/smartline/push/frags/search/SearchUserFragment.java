package com.tower.smartline.push.frags.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tower.smartline.common.app.MyApplication;
import com.tower.smartline.common.app.PresenterFragment;
import com.tower.smartline.common.widget.PortraitView;
import com.tower.smartline.common.widget.recycler.BaseRecyclerAdapter;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.presenter.search.ISearchContract;
import com.tower.smartline.factory.presenter.search.SearchUserPresenter;
import com.tower.smartline.factory.presenter.user.FollowPresenter;
import com.tower.smartline.factory.presenter.user.IFollowContract;
import com.tower.smartline.push.R;
import com.tower.smartline.push.activities.PersonalActivity;
import com.tower.smartline.push.activities.SearchActivity;
import com.tower.smartline.push.databinding.FragmentSearchUserBinding;

import com.bumptech.glide.Glide;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.drawable.LoadingCircleDrawable;
import net.qiujuer.genius.ui.drawable.LoadingDrawable;

import java.util.List;

/**
 * SearchUserFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/12 16:05
 */
public class SearchUserFragment extends PresenterFragment<ISearchContract.Presenter>
        implements SearchActivity.ISearchFragment, ISearchContract.UserView {
    private static final String TAG = SearchUserFragment.class.getName();

    private FragmentSearchUserBinding mBinding;

    private BaseRecyclerAdapter<UserCard> mAdapter;

    public SearchUserFragment() {
        // Required empty public constructor
    }

    @Override
    public ISearchContract.Presenter initPresenter() {
        return new SearchUserPresenter(this);
    }

    @NonNull
    @Override
    protected View initBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        if (mBinding == null) {
            mBinding = FragmentSearchUserBinding.inflate(inflater, container, false);
        }
        return mBinding.getRoot();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        // 初始化Recycler
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<UserCard>() {
                @Override
                protected int getItemViewType(int position, UserCard userCard) {
                    return R.layout.cell_search_list;
                }

                @NonNull
                @Override
                protected BaseRecyclerViewHolder<UserCard> onCreateViewHolder(View root, int viewType) {
                    return new SearchUserViewHolder(root);
                }
            };
        }
        mBinding.recycler.setAdapter(mAdapter);

        // 设置空布局
        setEmptyView(mBinding.empty, mBinding.recycler);
    }

    @Override
    protected void initData() {
        super.initData();

        // 进行一次空值模糊搜索
        search("");
    }

    @Override
    public void search(@Nullable String content) {
        getPresenter().search(content);
    }

    @Override
    public void searchUserSuccess(List<UserCard> userCards) {
        // 替换并显示新的搜索数据 若数据为空则显示空布局
        mAdapter.replace(userCards);
        hideLoading(mAdapter.getItemCount() > 0);
    }

    @Override
    protected void destroyBinding() {
        mBinding = null;
    }

    private class SearchUserViewHolder extends BaseRecyclerAdapter.BaseRecyclerViewHolder<UserCard>
            implements IFollowContract.View, View.OnClickListener {
        private PortraitView mPortrait;

        private TextView mName;

        private ImageView mFollow;

        private IFollowContract.Presenter mPresenter;

        private LoadingDrawable mLoadingDrawable;

        public SearchUserViewHolder(@NonNull View itemView) {
            super(itemView);
            mPortrait = itemView.findViewById(R.id.im_portrait);
            mName = itemView.findViewById(R.id.txt_name);
            mFollow = itemView.findViewById(R.id.im_follow);

            // Presenter初始化
            mPresenter = initPresenter();
        }

        @Override
        protected void onBind(@NonNull UserCard userCard) {
            if (mPortrait == null || mName == null || mFollow == null) {
                return;
            }
            mPortrait.setup(Glide.with(SearchUserFragment.this), userCard);
            mPortrait.setOnClickListener(this);
            mName.setText(userCard.getName());
            mFollow.setEnabled(!userCard.isFollow());
            mFollow.setOnClickListener(this);
        }

        @Override
        public IFollowContract.Presenter initPresenter() {
            return new FollowPresenter(this);
        }

        @Override
        public void showError(int str) {
            MyApplication.showToast(str);
            hideLoading();
        }

        @Override
        public void showLoading() {
            if (mLoadingDrawable == null) {
                // 初始化一个圆形的动画的Drawable
                int minSize = (int) Ui.dipToPx(getResources(), getResources().getDimension(R.dimen.len_22));
                int maxSize = (int) Ui.dipToPx(getResources(), getResources().getDimension(R.dimen.len_30));
                mLoadingDrawable = new LoadingCircleDrawable(minSize, maxSize);
                mLoadingDrawable.setBackgroundColor(0);
                int[] color = new int[]{UiCompat.getColor(getResources(), R.color.white_alpha_208)};
                mLoadingDrawable.setForegroundColor(color);
            }
            mFollow.setImageDrawable(mLoadingDrawable);
            mLoadingDrawable.start();
        }

        @Override
        public void hideLoading() {
            if (mFollow.getDrawable() instanceof LoadingDrawable) {
                // 停止动画 还原按钮
                ((LoadingDrawable) mFollow.getDrawable()).stop();
                mFollow.setImageResource(R.drawable.sel_opt_done_add);
            }
        }

        @Override
        public void setPresenter(IFollowContract.Presenter presenter) {
            mPresenter = presenter;
        }

        @Override
        public void followSuccess(UserCard userCard) {
            hideLoading();

            // 更新界面数据
            updateData(userCard);
        }

        private void onPortraitClick() {
            Log.i(TAG, "onPortraitClick: itemId: " + getItemId());
            if (getData() != null) {
                PersonalActivity.show(requireContext(), getData().getId());
            }
        }

        private void onFollowClick() {
            Log.i(TAG, "onFollowClick: itemId: " + getItemId());

            // 发起关注
            mPresenter.follow(getData().getId());
        }

        @Override
        public void onClick(View v) {
            if (v == null) {
                return;
            }
            int id = v.getId();
            if (id == mPortrait.getId()) {
                // 头像点击
                onPortraitClick();
            } else if (id == mFollow.getId()) {
                // 关注点击
                onFollowClick();
            } else {
                Log.w(TAG, "onClick: illegal param: " + id);
            }
        }
    }
}
