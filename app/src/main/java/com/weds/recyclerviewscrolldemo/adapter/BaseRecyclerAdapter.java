package com.weds.recyclerviewscrolldemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gengguanglong on 2016/11/30.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int TYPE_ITEM = 0;  //普通Item View
    public List<T> mData;
    public Context mContext;
    public LayoutInflater mInflater;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;
    private int size;

    public BaseRecyclerAdapter(Context context, List<T> list) {
        mData = (list != null) ? list : new ArrayList<T>();
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    private int count;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LogUtils.i("RecyclerView创建view","====="+(count++)+"=====");
        if (viewType == TYPE_ITEM) {
            final RecyclerViewHolder holder = new RecyclerViewHolder(mContext, mInflater.inflate
                    (getItemLayoutId(viewType), parent, false));
            if (mClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                    }
                });
            }
            if (mLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mLongClickListener.onItemLongClick(holder.itemView, holder
                                .getLayoutPosition());
                        return true;
                    }
                });
            }
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData != null && mData.size() > position) {
            bindData(holder, position, mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        size = mData.size();
        return size;
    }

    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    /**
     * 替换全部数据
     *
     * @param list
     */
    public void replaceList(List<T> list) {
//        clear();
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 清理全部数据
     */
    public void clear() {
        mData.clear();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

    abstract public int getItemLayoutId(int viewType);

    abstract public void bindData(RecyclerView.ViewHolder holder, int position, T item);

    public interface OnItemClickListener {
        public void onItemClick(View itemView, int pos);
    }

    public interface OnItemLongClickListener {
        public void onItemLongClick(View itemView, int pos);
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

}
