package org.yzjt.sdk.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LT on 2018/12/29.
 */
public class ResourceUtil {

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "id");
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getLayoutId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "layout");
    }

    /**
     * @param context
     * @param id
     * @param parent
     * @param <T>
     * @return
     */
    public static <T extends View> T getLayout(Context context, int id, ViewGroup parent){
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return (T) LayoutInflater.from(context).inflate(id,parent);
    }

    /**
     * @param context
     * @param resourceName
     * @param parent
     * @param <T>
     * @return
     */
    public static <T extends View> T getLayout(Context context, String resourceName, ViewGroup parent){
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return (T) LayoutInflater.from(context).inflate(getLayoutId(context,resourceName),parent);
    }

    /**
     * @param context
     * @param resourceName
     * @param <T>
     * @return
     */
    public static <T extends View> T getLayout(Context context, String resourceName){
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return (T) LayoutInflater.from(context).inflate(getLayoutId(context,resourceName),null);
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getStringId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "string");
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static String getString(Context context, String resourceName) {
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return context.getResources().getString(getIdentifierByType(context, resourceName, "string"));
    }



    /**
     * @param context
     * @param strId
     * @return
     */
    public static String getString(Context context, int strId) {
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return context.getResources().getString(strId);
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getDrawableId(Context context, String resourceName) {
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return getIdentifierByType(context, resourceName, "drawable");
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getMipmapId(Context context, String resourceName) {
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return getIdentifierByType(context, resourceName, "mipmap");
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static Drawable getDrawable(Context context, String resourceName) {
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return context.getResources().getDrawable(getIdentifierByType(context, resourceName, "drawable"));
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static Drawable getMipmap(Context context, String resourceName) {
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return context.getResources().getDrawable(getIdentifierByType(context, resourceName, "mipmap"));
    }

    /**
     * @param context
     * @param id
     * @return
     */
    public static Drawable getDrawable(Context context, int id) {
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return context.getResources().getDrawable(id);
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getColorId(Context context, String resourceName) {
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return getIdentifierByType(context, resourceName, "color");
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getColor(Context context, String resourceName) {
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return context.getResources().getColor(getColorId(context,resourceName));
    }

    /**
     * @param context
     * @param id
     * @return
     */
    public static int getColor(Context context, int id) {
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return context.getResources().getColor(id);
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getDimenId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "dimen");
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getAttrId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "attr");
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getStyleId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "style");
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getAnimId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "anim");
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getArrayId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "array");
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getIntegerId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "integer");
    }

    /**
     * @param context
     * @param resourceName
     * @return
     */
    public static int getBoolId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "bool");
    }

    /**
     * @param context
     * @param resourceName
     * @param defType
     * @return
     */
    private static int getIdentifierByType(Context context, String resourceName, String defType) {
        if(context == null){
            throw new IllegalArgumentException("Context is null!");
        }
        return context.getResources().getIdentifier(resourceName,
                defType,
                context.getPackageName());
    }
}
