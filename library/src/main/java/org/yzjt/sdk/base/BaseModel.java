package org.yzjt.sdk.base;

/**
 * Created by LT on 2019/2/14.
 */
public class BaseModel {

    public static final int TYPE_BANNER = 1;
    public static final int TYPE_MENU = 2;
    public static final int TYPE_DIGITAL_GOODS = 3;
    public static final int TYPE_INDICATOR = 4;

    public String name;
    public String icon;
    public int span;
    public int type;
    public Object data;
    public int position;

    @Override
    public String toString() {
        return "BaseModel{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", span=" + span +
                ", type=" + type +
                ", data=" + data +
                ", position=" + data +
                '}';
    }
}
