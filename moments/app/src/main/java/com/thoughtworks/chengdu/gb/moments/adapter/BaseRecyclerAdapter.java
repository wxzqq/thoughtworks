package com.thoughtworks.chengdu.gb.moments.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtworks.chengdu.gb.moments.interfaces.OnItemClickListener;
import com.thoughtworks.chengdu.gb.moments.interfaces.OnItemViewClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    protected List<T> mData = new ArrayList<>();
    protected LayoutInflater mInflater;
    protected OnItemClickListener<T> listener;
    protected OnItemViewClickListener mItemViewListener;
    protected Context mContext;

    public BaseRecyclerAdapter(Context context) {

        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        clear();
    }

    public void setOnItemListener(OnItemViewClickListener listener) {
        this.mItemViewListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }


    public void clear() {
        this.mData.clear();
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }


    /**
     * 刷新
     *
     * @param mData
     */
    public void refresh(List<T> mData) {
        if (mData == null || mData.isEmpty()) {
            clear();
        } else {
            this.mData.clear();
            this.mData.addAll(mData);
            notifyDataSetChanged();
        }
    }

    /**
     * 加载更多
     *
     * @param datas
     */
    public void loadMore(List<T> datas) {
        if (datas == null) {
            return;
        } else {
            this.mData.addAll(datas);
        }
        notifyDataSetChanged();
    }

    /**
     * 加载更多
     *
     * @param data
     */
    public void add(T data) {
        if (data == null) {
            return;
        } else {
            this.mData.add(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        onBindChildHolder((BaseHolder) viewHolder, position);

    }

    protected abstract void onBindChildHolder(BaseHolder holder, int childPosition);

    protected abstract BaseHolder onCreateHolder(ViewGroup viewGroup);

    protected static class BaseHolder extends RecyclerView.ViewHolder {
        public BaseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
