package org.yzjt.sdk.util;

/**
 * Created by LT on 2019/7/18.
 */
public class StrictUtil {

    /**
     * 不能为空的类型
     *
     * @param object
     */
    public static void requireNotNull(Object object){
        if(object == null){
            throw new NullPointerException("The " + object.getClass().getName() + " require not Null.");
        }
    }
}
