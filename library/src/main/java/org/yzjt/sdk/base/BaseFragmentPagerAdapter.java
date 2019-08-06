package org.yzjt.sdk.base;

import android.content.Context;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by LT on 2018/12/22.
 */
public class BaseFragmentPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {

    protected Context mContext;
    protected List<T> mPageList;

    public BaseFragmentPagerAdapter(FragmentManager fm, Context context, List<T> pageList) {
        super(fm);
        mContext = context;
        mPageList = pageList;
    }

    @Override
    public T getItem(int position) {
        return mPageList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return mPageList == null ? 0:mPageList.size();
    }
}
