package org.yzjt.sdk.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by LT on 2019/1/15.
 */
public class IOUtil {

    /**
     * 关闭流
     * @param closeable
     */
    public static void close(Closeable closeable){
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
