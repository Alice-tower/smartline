package com.tower.smartline.common.widget.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tower.smartline.common.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/11/24 3:42
 */
public abstract class RecyclerAdapter<Data>
        extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {
    private final List<Data> mDataList;

    private AdapterListener<Data> mListener;

    /**
     * 构造方法
     */
    public RecyclerAdapter() {
        this(null);
    }

    /**
     * 构造方法
     *
     * @param listener 点击监听
     */
    public RecyclerAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<Data>(), listener);
    }

    /**
     * 构造方法
     *
     * @param dataList 数据
     * @param listener 点击监听
     */
    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }

    /**
     * 复写默认的布局类型ViewType
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
    public MyViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 得到LayoutInflater用于把XML初始化为View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // 把XML id为viewType的文件初始化为一个root View
        View root = inflater.inflate(viewType, parent, false);

        // 通过子类必须实现的方法得到一个ViewHolder
        MyViewHolder<Data> holder = onCreateViewHolder(root, viewType);

        // 设置View的Tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);

        // 设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        // 进行界面注解绑定
        holder.unbinder = ButterKnife.bind(holder, root);

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
    protected abstract MyViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 绑定数据到一个Holder上
     *
     * @param holder   ViewHolder
     * @param position 坐标
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder<Data> holder, int position) {
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
    public void onClick(View v) {
        Object objectHolder = v.getTag(R.id.tag_recycler_holder);
        if (mListener != null && objectHolder instanceof MyViewHolder) {
            MyViewHolder<Data> viewHolder = (MyViewHolder<Data>) objectHolder;

            // 得到ViewHolder当前对应的适配器当中的坐标
            int pos = viewHolder.getAdapterPosition();

            // 回调方法
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Object objectHolder = v.getTag(R.id.tag_recycler_holder);
        if (mListener != null && objectHolder instanceof MyViewHolder) {
            MyViewHolder<Data> viewHolder = (MyViewHolder<Data>) objectHolder;

            // 得到ViewHolder当前对应的适配器当中的坐标
            int pos = viewHolder.getAdapterPosition();

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
        // 当item点击时触发
        void onItemClick(MyViewHolder<Data> holder, Data data);

        // 当item长按时触发
        void onItemLongClick(MyViewHolder<Data> holder, Data data);
    }

    /**
     * 自定义的ViewHolder
     *
     * @param <Data> 泛型类型
     */
    public static abstract class MyViewHolder<Data> extends RecyclerView.ViewHolder {
        private Unbinder unbinder;

        private AdapterCallback<Data> callback;

        protected Data mData;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         *
         * @param data 绑定的数据
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 触发绑定数据时的回调
         *
         * @param data 绑定的数据
         */
        protected abstract void onBind(Data data);

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
    }
}
