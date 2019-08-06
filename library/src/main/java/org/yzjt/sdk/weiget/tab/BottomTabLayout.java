package org.yzjt.sdk.weiget.tab;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import org.yzjt.sdk.util.PixelUtils;
import org.yzjt.sdk.util.ResourceUtil;

import java.util.List;

/**
 * Created by LT on 2018/12/22.
 */
public class BottomTabLayout extends RadioGroup implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    private List<TabItem> mMenuItemList;
    private ViewPager mViewPager;

    public BottomTabLayout(Context context) {
        this(context,null);
    }

    public BottomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setOnCheckedChangeListener(this);
    }

    /**
     * attach to ViewPager
     * @param viewPager
     */
    public void attachTo(ViewPager viewPager){
        mViewPager = viewPager;
        if(mViewPager == null){
            throw new NullPointerException("you should attach to viewPager,see attachTo method.");
        }
        mViewPager.addOnPageChangeListener(this);
    }

    public void setAdapter(FragmentPagerAdapter adapter){
        if(mViewPager == null){
            throw new NullPointerException("you should attach to viewPager,see attachTo method.");
        }
        if(mViewPager.getAdapter() == null){
            mViewPager.setAdapter(adapter);
        }
    }

    public void select(int position){
        if(mMenuItemList == null || mMenuItemList.size() <= position){
            return;
        }
        for(TabItem tabItem : mMenuItemList) {
            RadioButton radioButton = tabItem.radioButton;
            if(radioButton != null){
                radioButton.setTextColor(tabItem.normalColor);
                setDrawableTop(radioButton,tabItem.normalId);
            }
        }
        TabItem tabItem = mMenuItemList.get(position);
        tabItem.radioButton.setTextColor(tabItem.selectColor);
        setDrawableTop(tabItem.radioButton,tabItem.selectId);
        if(mViewPager == null){
            throw new NullPointerException("you should attach to viewPager,see attachTo method.");
        }
        PagerAdapter adapter = mViewPager.getAdapter();
        if(adapter != null && adapter.getCount() > position) {
            mViewPager.setCurrentItem(position);
        }
    }

    private void setDrawableTop(RadioButton radioButton, int iconId){
        if(radioButton != null){
            Drawable top = ResourceUtil.getDrawable(getContext(),iconId);
            top.setBounds(0,0,top.getMinimumWidth(),top.getMinimumHeight());
            radioButton.setCompoundDrawables(null,top,null,null);
        }
    }

    public void setTabItems(List<TabItem> tabItems){
        mMenuItemList = tabItems;
        for(int i=0;i<mMenuItemList.size();i++) {
            TabItem tabItem = mMenuItemList.get(i);
            addTabItem(tabItem,i);
        }
    }

    private void addTabItem(TabItem tabItem,int position) {
        RadioButton radioButton = new RadioButton(getContext());
        Drawable top = ResourceUtil.getDrawable(getContext(),tabItem.normalId);
        top.setBounds(0,0,top.getMinimumWidth(),top.getMinimumHeight());
        radioButton.setCompoundDrawables(null,top,null,null);
        radioButton.setTextColor(tabItem.normalColor);
        radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        radioButton.setText(tabItem.name);
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setButtonDrawable(null);
        radioButton.setId(position);
        LayoutParams layoutParams = new LayoutParams(0, PixelUtils.dp2px(getContext(),49));
        layoutParams.weight = 1;
        addView(radioButton,layoutParams);
        tabItem.radioButton = radioButton;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        select(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        select(id);
    }

    public static class TabItem {
        public int selectId;
        public int normalId;
        public int normalColor;
        public int selectColor;
        public String name;
        public RadioButton radioButton;

        public TabItem(int selectId, int normalId, int normalColor, int selectColor, String name) {
            this.selectId = selectId;
            this.normalId = normalId;
            this.normalColor = normalColor;
            this.selectColor = selectColor;
            this.name = name;
        }
    }
}
