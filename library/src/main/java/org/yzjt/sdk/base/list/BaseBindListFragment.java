package org.yzjt.sdk.base.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import org.yzjt.sdk.R;
import org.yzjt.sdk.base.BaseBindingRecyclerAdapter;
import org.yzjt.sdk.view.IContract;
import org.yzjt.sdk.mvp.LazyMvpFragment;
import org.yzjt.sdk.nozzle.LoadingListener;
import org.yzjt.sdk.nozzle.RequestCallback;
import org.yzjt.sdk.util.CollectionUtil;
import org.yzjt.sdk.util.StrictUtil;
import org.yzjt.sdk.weiget.loading.ProgressLoadingHelper;

import java.util.List;

/**
 * Created by LT on 2019/1/22.
 */
public abstract class BaseBindListFragment<T extends IContract.Presenter,D extends List> extends LazyMvpFragment<T> implements IContract.View, RequestCallback<D>, LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private ProgressLoadingHelper mProgressLoadingHelper;
    private boolean mRefresh = true;
    private int mPage = 1;
    private BaseBindingRecyclerAdapter mBaseRecyclerAdapter;

    @Override
    public abstract T initPresenter();

    @Override
    protected View getContentView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.layout_common_list,viewGroup,false);
    }

    @Override
    protected void initView() {
        configTitleBar(findViewById(R.id.title_bar),(Toolbar)findViewById(R.id.toolbar),(TextView)findViewById(R.id.tv_title),findViewById(R.id.status_bar));
        mRecyclerView = findViewById(R.id.recycler_view);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(this);
        mProgressLoadingHelper = new ProgressLoadingHelper(mContext);
        mProgressLoadingHelper.attachTo(mRefreshLayout,mRecyclerView);
        initRecyclerView(mRecyclerView);
    }

    protected void configTitleBar(View titleBar, Toolbar toolbar, TextView tvTitle, View statusBar){
        titleBar.setVisibility(View.GONE);
    }

    protected void initRecyclerView(RecyclerView recyclerView) {
        mBaseRecyclerAdapter = getRecyclerAdapter();
        StrictUtil.requireNotNull(mBaseRecyclerAdapter);
        mBaseRecyclerAdapter.setLoadingListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mBaseRecyclerAdapter);
    }

    protected abstract BaseBindingRecyclerAdapter getRecyclerAdapter();

    @Override
    public void onRequest() {
        if(mRefresh){
            mProgressLoadingHelper.showLoading("加载中...");
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onRefresh() {
        mRefresh = true;
        loadData(mPage = 1);
    }

    @Override
    public void onLoadMore() {
        mRefresh = false;
        loadData(mPage++);
    }

    protected abstract void loadData(int page);

    @Override
    public void onSuccess(List data) {
        if(mRefresh){
            if(CollectionUtil.isEmpty(data)){
                if(mBaseRecyclerAdapter != null && CollectionUtil.isEmpty(mBaseRecyclerAdapter.getData())){
                    // empty
                    mProgressLoadingHelper.showEmpty();
                }
            } else {
                mBaseRecyclerAdapter.refreshData(data);
            }
            mProgressLoadingHelper.hideLoading();
            mRefreshLayout.setRefreshing(false);
        } else {
            if(CollectionUtil.isEmpty(data)){
                if(mBaseRecyclerAdapter != null){
                    mBaseRecyclerAdapter.hasNoMoreData();
                }
            } else {
                if(mBaseRecyclerAdapter != null){
                    mBaseRecyclerAdapter.addDatas(data);
                    mBaseRecyclerAdapter.loadMoreComplete();
                }
            }
        }
    }

    @Override
    public void onFailure(String s) {
        if(mRefresh && mBaseRecyclerAdapter != null && CollectionUtil.isEmpty(mBaseRecyclerAdapter.getData())) {
            mProgressLoadingHelper.showError(s);
        }
    }

    @Override
    public void onComplete() {

    }
}
