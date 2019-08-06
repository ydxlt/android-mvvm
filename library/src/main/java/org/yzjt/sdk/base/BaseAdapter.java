package org.yzjt.sdk.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import org.yzjt.sdk.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LT on 2018/12/17.
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    protected Context mContext;
    protected List<T> mData;

    public BaseAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null?0:mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData == null?null:mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void addData(T t){
        if(mData == null){
            mData = new ArrayList<>();
        }
        mData.add(t);
        notifyDataSetChanged();
    }

    public void refreshData(List<T> dataList){
        if(!CollectionUtil.isEmpty(mData)){
            mData.clear();
        }
        addDatas(dataList);
    }


    public void addDatas(List<T> dataList){
        if(CollectionUtil.isEmpty(dataList)){
            return;
        }
        if(CollectionUtil.isEmpty(mData)){
            mData = new ArrayList<>();
        }
        mData.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public abstract View getView(int position, View view, ViewGroup viewGroup);
}
