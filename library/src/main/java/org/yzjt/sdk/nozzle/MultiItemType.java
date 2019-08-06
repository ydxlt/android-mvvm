package org.yzjt.sdk.nozzle;

/**
 * Created by LT on 2018/12/21.
 */
public interface MultiItemType<T> {
    int getLayoutId(int position);
    int getItemViewType(T t, int position);
}
