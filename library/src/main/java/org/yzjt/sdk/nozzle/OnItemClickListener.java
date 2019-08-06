package org.yzjt.sdk.nozzle;

import android.view.View;

/**
 * Created by LT on 2018/12/28.
 */
public interface OnItemClickListener<T> {
    void onClick(View view, T data, int position);
}
