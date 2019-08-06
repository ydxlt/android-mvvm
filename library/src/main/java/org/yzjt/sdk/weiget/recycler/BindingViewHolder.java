package org.yzjt.sdk.weiget.recycler;

import android.view.View;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by LT on 2018/12/21.
 */
public class BindingViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding mDataBinding;

    public BindingViewHolder(View itemView) {
        super(itemView);
    }

    public ViewDataBinding getDataBinding() {
        return mDataBinding;
    }

    public void setDataBinding(ViewDataBinding dataBinding) {
        mDataBinding = dataBinding;
    }
}
