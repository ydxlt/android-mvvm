package org.yzjt.sdk.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import org.yzjt.sdk.nozzle.LoadingListener;
import org.yzjt.sdk.nozzle.OnItemClickListener;
import org.yzjt.sdk.util.CollectionUtil;
import org.yzjt.sdk.util.ResourceUtil;
import org.yzjt.sdk.util.ViewUtil;
import org.yzjt.sdk.weiget.recycler.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>p为数据模型，Model类</p>
 * Created by LT on 2018/12/17.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder>{
    protected final String TAG = getClass().getSimpleName();
    public static final int TYPE_FOOTER = -1;
    public static final int STATE_LOAD_NO_MORE = 1;
    public static final int STATE_LOAD_LOADING = 2;
    public static final int STATE_LOAD_COMPLETE = 3;
    public static final int STATE_LOAD_ERROR = 4;

    protected Context mContext;
    protected List<T> mData;
    protected OnItemClickListener mOnItemClickListener;
    protected LoadingListener mLoadingListener;
    protected View.OnClickListener mLoadErrorClickListener;
    private int mLoadState = STATE_LOAD_COMPLETE;
    protected boolean mAutoLoadMore = true;

    public BaseRecyclerAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType == TYPE_FOOTER){
            View loadMoreView = LayoutInflater.from(mContext).inflate(getLoadMoreLayoutId(),viewGroup,false);
            return ViewHolder.create(loadMoreView);
        } else {
            int layoutId = getLayoutId(viewType,viewGroup);
            View layoutView = getLayoutView(viewType,viewGroup);
            if (layoutId != -1) {
                return ViewHolder.create(mContext, viewGroup, layoutId);
            } else if (layoutView != null) {
                return ViewHolder.create(layoutView);
            }
            return null;
        }
    }

    protected int getLoadMoreLayoutId() {
        return ResourceUtil.getLayoutId(mContext,"item_load_more");
    }

    protected View getLayoutView(int position, ViewGroup viewGroup) {
        return null;
    }

    protected int getLayoutId(int position, ViewGroup viewGroup) {
        return -1;
    }

    public void setLoadingListener(LoadingListener loadingListener) {
        mLoadingListener = loadingListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position){
        int itemViewType = getItemViewType(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    protected void bindLoadView(final ViewHolder viewHolder) {
        if(viewHolder != null && viewHolder.itemView != null){
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
                    viewHolder.setVisibility(ResourceUtil.getId(mContext, "progress_bar"), View.VISIBLE);
                    viewHolder.setText(ResourceUtil.getId(mContext, "tv_load_text"), "loading...");
                    viewHolder.itemView.setEnabled(false);
                    break;
                case STATE_LOAD_NO_MORE:
                    viewHolder.itemView.setVisibility(View.VISIBLE);
                    viewHolder.setVisibility(ResourceUtil.getId(mContext, "progress_bar"), View.GONE);
                    viewHolder.setText(ResourceUtil.getId(mContext, "tv_load_text"), ResourceUtil.getString(mContext,"no_more"));
                    viewHolder.itemView.setEnabled(true);
                    break;
                case STATE_LOAD_ERROR:
                    viewHolder.itemView.setVisibility(View.VISIBLE);
                    viewHolder.setVisibility(ResourceUtil.getId(mContext, "progress_bar"), View.GONE);
                    viewHolder.setText(ResourceUtil.getId(mContext, "tv_load_text"),
                            ResourceUtil.getString(mContext,"load_wrong"));
                    viewHolder.itemView.setEnabled(true);
                    break;
            }
        }
    }

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

    protected abstract void bindView(ViewHolder viewHolder, int position);

    @Override
    public int getItemCount() {
        return mData == null?1:mData.size() + 1;
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

    /**
     * StaggeredGridLayoutManager模式时，HeaderView、FooterView可占据一行
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
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
                    if (position == getItemCount() -1) {
                        return gridManager.getSpanCount();
                    }
                    return getSpanCount(position);
                }
            });
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mAutoLoadMore && mLoadState != STATE_LOAD_NO_MORE && ViewUtil.findRecyclerLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setLoadErrorClickListener(View.OnClickListener loadErrorClickListener) {
        mLoadErrorClickListener = loadErrorClickListener;
    }
}
