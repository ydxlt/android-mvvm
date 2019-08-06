package org.yzjt.sdk.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import org.yzjt.sdk.R;
import org.yzjt.sdk.nozzle.LoadingListener;
import org.yzjt.sdk.nozzle.OnItemClickListener;
import org.yzjt.sdk.util.CollectionUtil;
import org.yzjt.sdk.util.LogUtil;
import org.yzjt.sdk.util.ResourceUtil;
import org.yzjt.sdk.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LT on 2018/12/17.
 */
public abstract class BaseBindingRecyclerAdapter<T,H extends ViewDataBinding> extends RecyclerView.Adapter<BaseBindingRecyclerAdapter.BindingViewHolder<H>>{

    private static final String TAG = "BaseBindingRecyclerAdapter";

    public static final int TYPE_FOOTER = -1;
    public static final int STATE_LOAD_NO_MORE = 1;
    public static final int STATE_LOAD_LOADING = 2;
    public static final int STATE_LOAD_COMPLETE = 3;
    public static final int STATE_LOAD_ERROR = 4;

    protected Context mContext;
    protected List<T> mData;
    protected OnItemClickListener<T> mOnItemClickListener;

    protected LoadingListener mLoadingListener;
    protected View.OnClickListener mLoadErrorClickListener;
    private int mLoadState;
    protected boolean mAutoLoadMore = true;

    public BaseBindingRecyclerAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public BindingViewHolder<H> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogUtil.d(TAG, "onCreateViewHolder: "+viewType);
        if(viewType == TYPE_FOOTER){
            View loadMoreView = LayoutInflater.from(mContext).inflate(getLoadMoreLayoutId(),parent,false);
            loadMoreView.setVisibility(View.GONE);
            H binding = DataBindingUtil.bind(loadMoreView);
            BindingViewHolder<H> hBindingViewHolder = new BindingViewHolder<>(binding.getRoot());
            hBindingViewHolder.setDataBinding(binding);
            return hBindingViewHolder;
        } else {
            int layoutId = getLayoutId(viewType,parent);
            View view = getLayoutView(viewType,parent);
            H binding = null;
            if (layoutId != -1) {
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent,
                        false);
            } else if (view != null) {
                binding = DataBindingUtil.bind(view);
            }
            BindingViewHolder<H> holder = new BindingViewHolder<H>(binding.getRoot());
            holder.setDataBinding(binding);
            return holder;
        }
    }

    protected int getLoadMoreLayoutId() {
        return ResourceUtil.getLayoutId(mContext,"layout_load_more");
    }

    protected View getLayoutView(int position, ViewGroup parent) {
        return null;
    }

    protected int getLayoutId(int position, ViewGroup parent) {
        return -1;
    }

    public void setLoadingListener(LoadingListener loadingListener) {
        mLoadingListener = loadingListener;
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<H> viewHolder, final int position){
        int itemViewType = getItemViewType(position);
        LogUtil.d(TAG, "onBindViewHolder: "+itemViewType);
        viewHolder.getDataBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onClick(view,getItem(position),position);
                }
            }
        });
        if(itemViewType == TYPE_FOOTER) {
            bindLoadView(viewHolder);
        } else {
            bindView(viewHolder, position);
        }
    }

    protected void bindLoadView(final BindingViewHolder viewHolder) {
        if(viewHolder != null && viewHolder.itemView != null){
            View progress_bar = viewHolder.itemView.findViewById(ResourceUtil.getId(mContext, "progress_bar"));
            TextView tv_load_text = (TextView) viewHolder.itemView.findViewById(ResourceUtil.getId(mContext, "tv_load_text"));
            if(mLoadErrorClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLoadState == STATE_LOAD_ERROR) {
                            mLoadErrorClickListener.onClick(viewHolder.itemView);
                        }
                    }
                });
            }
            switch (mLoadState){
                case STATE_LOAD_COMPLETE:
                    viewHolder.itemView.setVisibility(View.GONE);
                    viewHolder.itemView.setEnabled(true);
                    break;
                case STATE_LOAD_LOADING:
                    viewHolder.itemView.setVisibility(View.VISIBLE);
                    if(progress_bar != null){
                        progress_bar.setVisibility(View.VISIBLE);
                    }
                    if(tv_load_text != null) {
                        tv_load_text.setText(ResourceUtil.getString(mContext, R.string.load_more_loading));
                    }
                    viewHolder.itemView.setEnabled(false);
                    break;
                case STATE_LOAD_NO_MORE:
                    viewHolder.itemView.setVisibility(View.VISIBLE);
                    if(progress_bar != null){
                        progress_bar.setVisibility(View.GONE);
                    }
                    if(tv_load_text != null) {
                        tv_load_text.setText(ResourceUtil.getString(mContext, R.string.load_more_no_more));
                    }
                    viewHolder.itemView.setEnabled(true);
                    break;
                case STATE_LOAD_ERROR:
                    viewHolder.itemView.setVisibility(View.VISIBLE);
                    if(progress_bar != null){
                        progress_bar.setVisibility(View.GONE);
                    }
                    if(tv_load_text != null) {
                        tv_load_text.setText(ResourceUtil.getString(mContext, R.string.load_more_error));
                    }
                    viewHolder.itemView.setEnabled(true);
                    break;
            }
        }
    }

    protected abstract void bindView(BindingViewHolder<H> viewHolder,int position);

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount() - 1){
            return TYPE_FOOTER;
        }
        return getCommonItemViewType(position);
    }

    private void setLoadMoreState(int loadState){
        mLoadState = loadState;
        notifyItemChanged(getItemCount() - 1);
    }

    public void hasNoMoreData(){
        setLoadMoreState(STATE_LOAD_NO_MORE);
    }

    public void loadMoreComplete(){
        setLoadMoreState(STATE_LOAD_COMPLETE);
    }

    public void loadMoreError(){
        setLoadMoreState(STATE_LOAD_ERROR);
    }

    public void loadMore(){
        setLoadMoreState(STATE_LOAD_LOADING);
    }

    protected int getCommonItemViewType(int position){
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mData == null?1:mData.size()+1;
    }

    public T getItem(int position){
        return mData == null?null:mData.get(position);
    }

    public void addData(T t){
        if(mData == null){
            mData = new ArrayList<>();
        }
        mData.add(t);
        notifyDataSetChanged();
    }

    public void addData(T t,int position){
        if(mData == null){
            mData = new ArrayList<>();
        }
        mData.add(position,t);
        notifyDataSetChanged();
    }

    public void refreshData(List<? extends T> dataList){
        if(!CollectionUtil.isEmpty(mData)){
            mData.clear();
        }
        addDatas(dataList);
    }


    public void addDatas(List<? extends T> dataList){
        if(CollectionUtil.isEmpty(dataList)){
            return;
        }
        if(CollectionUtil.isEmpty(mData)){
            mData = new ArrayList<>();
        }
        int start = mData.size() - 1;
        mData.addAll(dataList);
        notifyItemRangeChanged(start,dataList.size());
    }

    public void addDatas(List<? extends T> dataList,int position){
        if(CollectionUtil.isEmpty(dataList)){
            return;
        }
        if(CollectionUtil.isEmpty(mData)){
            mData = new ArrayList<>();
        }
        mData.addAll(position,dataList);
        notifyDataSetChanged();
    }

    /**
     * StaggeredGridLayoutManager模式时，HeaderView、FooterView可占据一行
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(BindingViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (position == getItemCount() -1) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * GridLayoutManager模式时， HeaderView、FooterView可占据一行，判断RecyclerView是否到达底部
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position == BaseBindingRecyclerAdapter.this.getItemCount() - 1 ? gridManager.getSpanCount() : getSpanCount(position);
                }
            });
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mAutoLoadMore && ViewUtil.findRecyclerLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                        if(mLoadingListener != null){
                            mLoadingListener.onLoadMore();
                            loadMore();
                        }
                    }
                }
            }
        });
    }

    protected int getSpanCount(int position){
        return 1;
    }

    public List<T> getData() {
        return mData;
    }

    public static class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

        private T mDataBinding;

        public BindingViewHolder(View itemView) {
            super(itemView);
        }

        public T getDataBinding() {
            return mDataBinding;
        }

        public void setDataBinding(T dataBinding) {
            mDataBinding = dataBinding;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setLoadErrorClickListener(View.OnClickListener loadErrorClickListener) {
        mLoadErrorClickListener = loadErrorClickListener;
    }
}
