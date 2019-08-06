package org.yzjt.sdk.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;

/**
 * Created by LT on 2018/12/22.
 */
public class TextFragment extends BaseFragment {

    private String mTitle = "title";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            mTitle = arguments.getString("title");
        }
    }

    @Override
    protected View getContentView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setText(mTitle);
        return textView;
    }

    @Override
    protected void initView() {

    }
}
