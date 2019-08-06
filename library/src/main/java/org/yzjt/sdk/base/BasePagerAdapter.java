package org.yzjt.sdk.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * Created by admin on 2018/5/17.
 */

public abstract class BasePagerAdapter<T> extends PagerAdapter {

    protected List<T> mData;
    protected Context mContext;
    protected OnItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public BasePagerAdapter(List<T> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = bindView(container,position,mData.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(view,position,mData.get(position));
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    protected abstract View bindView(ViewGroup container, int position, T t);

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T t);
    }
}
