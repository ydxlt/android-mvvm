package org.yzjt.sdk.base;

import android.content.Context;
import android.view.ViewGroup;
import androidx.databinding.ViewDataBinding;
import org.yzjt.sdk.nozzle.MultiItemType;

import java.util.List;

/**
 * Created by LT on 2018/12/17.
 */
public abstract class BaseMultiBindingRecyclerAdapter<T,H extends ViewDataBinding> extends BaseBindingRecyclerAdapter<T,H> {

    protected MultiItemType<T> mMultiItemType;

    public BaseMultiBindingRecyclerAdapter(Context context, List<T> data, MultiItemType<T> multiItemType) {
        super(context, data);
        mMultiItemType = multiItemType;
    }

    @Override
    protected int getLayoutId(int viewType, ViewGroup parent) {
        return mMultiItemType.getLayoutId(viewType);
    }

    @Override
    protected int getCommonItemViewType(int position) {
        return mMultiItemType.getItemViewType(getItem(position),position);
    }
}
