package org.yzjt.sdk.weiget.loading;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LT on 2019/3/12.
 */
public interface LoadingHelper {

    /**
     * 依附的视图
     * @param parent
     * @param brother 兄弟节点
     */
    void attachTo(ViewGroup parent, View brother);

    /**
     * 获取根布局
     * @return
     */
    View getView();

    /**
     * 显示加载中
     * @param msg
     */
    void showLoading(String msg);

    /**
     * 隐藏加载中
     */
    void hideLoading();

    /**
     * 显示加载错误
     * @param msg
     */
    void showError(String msg);

    void showEmpty();

    /**
     * 设置加载错误点击事件
     * @param onErrorClickListener
     */
    void setOnErrorClickListener(View.OnClickListener onErrorClickListener);
}
