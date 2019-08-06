package org.yzjt.sdk.weiget.recycler;

import android.content.Context;
import android.graphics.Color;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.yzjt.sdk.util.LogUtil;

/**
 * Created by LT on 2019/1/19.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "ViewHolder";

    protected SparseArray<View> mViews;
    protected View mConvertView;

    public ViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        this.mConvertView = itemView;
    }

    /**
     * 创建ViewHolder
     * @param context
     * @param parent
     * @param layoutId
     * @return
     */
    public static ViewHolder create(Context context, ViewGroup parent, int layoutId){
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 创建ViewHolder
     * @param itemView
     * @return
     */
    public static ViewHolder create(View itemView){
        return new ViewHolder(itemView);
    }

    /**
     * 通过Id得到view
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if( view == null ){
            view = mConvertView.findViewById(viewId);
            if(view == null){
                // 没找到，返回空
                return null;
            }
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    public ViewHolder setOnClickListener(int viewId, View.OnClickListener onClickListener){
        View view = getView(viewId);
        if(view != null && onClickListener != null){
            view.setOnClickListener(onClickListener);
        }
        return this;
    }

    public ViewHolder setText(int viewId, String title) {
        return setText(viewId,title, Color.WHITE);
    }

    public ViewHolder setText(int viewId, Spanned spanned) {
        TextView textView = getView(viewId);
        if(textView != null){
            textView.setText(spanned);
        }
        return this;
    }



    public ViewHolder setText(int viewId, String title, int color) {
        TextView textView = getView(viewId);
        if(textView != null){
            textView.setText(title);
            textView.setBackgroundColor(color);
        }
        return this;
    }

    public ViewHolder setImageUrl(Context context, int viewId, String url) {
        LogUtil.d(TAG,"url:"+url);
        ImageView imageView = getView(viewId);
        if(imageView != null && !TextUtils.isEmpty(url)){
            Glide.with(context)
                    .load(url)
                    .into(imageView);
        }
        return this;
    }

    public ViewHolder setImageUrl(Context context, ImageView imageView, String url) {
        if(imageView != null && !TextUtils.isEmpty(url)){
            Glide.with(context)
                    .load(url)
                    .into(imageView);
        }
        return this;
    }

    public ViewHolder setImageResId(int id, int redId) {
        ImageView imageView = getView(id);
        if(imageView != null){
            imageView.setImageResource(redId);
        }
        return this;
    }

    public ViewHolder setOnItemClickListener(final View.OnClickListener onItemClickListener) {
        if(mConvertView != null && onItemClickListener != null){
            mConvertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(view);
                }
            });
        }
        return this;
    }

    public ViewHolder setVisibility(int viewId, int visible) {
        View view = getView(viewId);
        if(view != null){
            view.setVisibility(visible);
        }
        return this;
    }
}

