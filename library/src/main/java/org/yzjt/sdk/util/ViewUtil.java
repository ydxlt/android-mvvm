package org.yzjt.sdk.util;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by LT on 2018/12/29.
 */
public class ViewUtil {

    /**
     * 查找recyclerView的最后一个可见的position
     * @param layoutManager
     * @return
     */
    public static int findRecyclerLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            return findStaggeredMax(lastVisibleItemPositions);
        }
        return -1;
    }

    /**
     * StaggeredGridLayoutManager时，查找position最大的列
     * @param lastVisiblePositions
     * @return
     */
    public static int findStaggeredMax(int[] lastVisiblePositions) {
        int max = lastVisiblePositions[0];
        for (int value : lastVisiblePositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 设置top drawable
     * @param compoundButton
     * @param top
     */
    public static void setCompoundDrawables(Button compoundButton, Drawable left, Drawable top, Drawable right, Drawable bottom){
        if(compoundButton == null){
            throw new IllegalArgumentException("compoundButton is null");
        }
        if(left != null) {
            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());
        }
        if(right != null) {
            right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
        }
        if(top != null) {
            top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        }
        if(bottom != null) {
            bottom.setBounds(0, 0, bottom.getMinimumWidth(), bottom.getMinimumHeight());
        }
        compoundButton.setCompoundDrawables(left,top,right,bottom);
    }

    /**
     * @param id
     * @param parent
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewById(int id, View parent){
        if(parent == null){
            return null;
        }
        return (T) parent.findViewById(id);
    }

    /**
     * @param name
     * @param parent
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewById(String name, View parent){
        if(parent == null){
            return null;
        }
        return (T) parent.findViewById(ResourceUtil.getId(parent.getContext(),name));
    }
}
