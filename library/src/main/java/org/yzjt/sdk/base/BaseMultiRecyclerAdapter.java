package org.yzjt.sdk.base;

import android.content.Context;
import android.view.ViewGroup;
import org.yzjt.sdk.nozzle.MultiItemType;

import java.util.List;

/**
 * Created by LT on 2018/12/17.
 */
public abstract class BaseMultiRecyclerAdapter<T> extends BaseRecyclerAdapter<T>{

    protected MultiItemType<T> mMultiItemType;

    public BaseMultiRecyclerAdapter(Context context, List<T> data,MultiItemType<T> multiItemType) {
        super(context, data);
        mMultiItemType = multiItemType;
    }

    @Override
    protected int getLayoutId(int viewType, ViewGroup viewGroup) {
        return mMultiItemType.getLayoutId(viewType);
    }

    @Override
    protected int getCommonItemViewType(int position) {
        return mMultiItemType.getItemViewType(getItem(position),position);
    }
}
