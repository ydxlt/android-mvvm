package org.yzjt.sdk.util;

import java.util.Collection;

/**
 * Created by LT on 2018/12/17.
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection collection){
        return collection == null || collection.isEmpty();
    }
}
