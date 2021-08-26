package com.tower.smartline.common.widget.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tower.smartline.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * RecyclerView适配器
 *
 * @param <Data> RecyclerView数据集合中的数据类型
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/11/24 3:42
 */
public abstract class BaseRecyclerAdapter<Data>
        extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerViewHolder<Data>>
        implements MyAdapterCallback<Data>, View.OnClickListener, View.OnLongClickListener {
    private final List<Data> mDataList;

    private AdapterListener<Data> mListener;

    /**
     * 构造方法
     */
    public BaseRecyclerAdapter() {
        this(null);
    }

    /**
     * 构造方法
     *
     * @param listener 点击监听
     */
    public BaseRecyclerAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<>(), listener);
    }

    /**
     * 构造方法
     *
     * @param dataList 数据
     * @param listener 点击监听
     */
    public BaseRecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }

    /**
     * 覆写默认的布局类型ViewType
     *
     * @param position 坐标
     * @return 布局类型，约定为XML布局的Id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 得到布局的类型
     *
     * @param position 坐标
     * @param data     当前的数据
     * @return XML布局的Id，用于创建ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 创建一个ViewHolder
     *
     * @param parent   RecyclerView
     * @param viewType 布局类型，约定为XML布局的Id
     * @return ViewHolder
     */
    @NonNull
    @Override
    public BaseRecyclerViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 得到LayoutInflater用于把XML初始化为View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // 把XML id为viewType的文件初始化为一个root View
        View root = inflater.inflate(viewType, parent, false);

        // 通过子类必须实现的方法得到一个ViewHolder
        BaseRecyclerViewHolder<Data> holder = onCreateViewHolder(root, viewType);

        // 设置View的Tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);

        // 设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        // 绑定callback
        holder.callback = this;

        return holder;
    }

    /**
     * 得到一个新的ViewHolder
     *
     * @param root     根布局
     * @param viewType 布局类型，约定为XML布局的Id
     * @return ViewHolder
     */
    @NonNull
    protected abstract BaseRecyclerViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 绑定数据到一个Holder上
     *
     * @param holder   ViewHolder
     * @param position 坐标
     */
    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder<Data> holder, int position) {
        // 得到需要绑定的数据
        Data data = mDataList.get(position);

        // 将绑定数据传给Holder，如何绑定由Holder自己实现
        holder.bind(data);
    }

    /**
     * 得到当前集合的数据量
     *
     * @return 当前集合的数据量
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 得到当前集合
     *
     * @return List<Data>
     */
    public List<Data> getItems() {
        return mDataList;
    }

    /**
     * 插入一条数据并通知刷新
     *
     * @param data 插入数据
     */
    public final void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一堆数据并通知该段集合刷新
     *
     * @param dataList 插入数据
     */
    @SafeVarargs
    public final void add(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeInserted(startPos, dataList.length);
        }
    }

    /**
     * 插入一堆数据并通知该段集合刷新
     *
     * @param dataList 插入数据
     */
    public final void add(Collection<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(startPos, dataList.size());
        }
    }

    /**
     * 清空数据并刷新
     */
    public final void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 清空数据，替换为新的集合并刷新
     *
     * @param dataList 替换数据
     */
    public final void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (dataList != null && dataList.size() > 0) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    @Override
    public void update(Data data, @NonNull BaseRecyclerViewHolder<Data> holder) {
        // 得到ViewHolder当前对应的适配器当中的坐标
        int pos = holder.getBindingAdapterPosition();
        if (pos >= 0) {
            // 进行数据的移除与更新，通知界面刷新
            mDataList.set(pos, data);
            notifyItemChanged(pos);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onClick(@NonNull View v) {
        Object objectHolder = v.getTag(R.id.tag_recycler_holder);
        if (mListener != null && objectHolder instanceof BaseRecyclerAdapter.BaseRecyclerViewHolder) {
            BaseRecyclerViewHolder<Data> viewHolder = (BaseRecyclerViewHolder<Data>) objectHolder;

            // 得到ViewHolder当前对应的适配器当中的坐标
            int pos = viewHolder.getBindingAdapterPosition();

            // 回调方法
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onLongClick(@NonNull View v) {
        Object objectHolder = v.getTag(R.id.tag_recycler_holder);
        if (mListener != null && objectHolder instanceof BaseRecyclerAdapter.BaseRecyclerViewHolder) {
            BaseRecyclerViewHolder<Data> viewHolder = (BaseRecyclerViewHolder<Data>) objectHolder;

            // 得到ViewHolder当前对应的适配器当中的坐标
            int pos = viewHolder.getBindingAdapterPosition();

            // 回调方法
            this.mListener.onItemLongClick(viewHolder, mDataList.get(pos));

            return true;
        }
        return false;
    }

    /**
     * 设置适配器的监听
     *
     * @param adapterListener AdapterListener
     */
    public void setListener(AdapterListener<Data> adapterListener) {
        this.mListener = adapterListener;
    }

    /**
     * 自定义监听器
     *
     * @param <Data>
     */
    public interface AdapterListener<Data> {
        /**
         * 当item点击时触发
         *
         * @param holder MyViewHolder
         * @param data   被点击项的数据
         */
        void onItemClick(@NonNull BaseRecyclerViewHolder<Data> holder, Data data);

        /**
         * 当item长按时触发
         *
         * @param holder MyViewHolder
         * @param data   被长按项的数据
         */
        void onItemLongClick(@NonNull BaseRecyclerViewHolder<Data> holder, Data data);
    }

    /**
     * 自定义的ViewHolder
     *
     * @param <Data> RecyclerView数据集合中的数据类型
     */
    public static abstract class BaseRecyclerViewHolder<Data> extends RecyclerView.ViewHolder {
        private MyAdapterCallback<Data> callback;

        private Data mData;

        public BaseRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         *
         * @param data 绑定的数据
         */
        private void bind(Data data) {
            this.mData = data;
            if (data != null) {
                onBind(data);
            }
        }

        /**
         * 触发绑定数据时的回调
         *
         * @param data 绑定的数据
         */
        protected abstract void onBind(@NonNull Data data);

        /**
         * Holder自己对自己对应的Data进行更新操作
         *
         * @param data Data数据
         */
        public void updateData(Data data) {
            if (this.callback != null) {
                this.callback.update(data, this);
            }
        }

        public Data getData() {
            return mData;
        }
    }
}
