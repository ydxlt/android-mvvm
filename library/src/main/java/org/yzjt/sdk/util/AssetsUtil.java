package org.yzjt.sdk.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LT on 2018/12/31.
 */
public class AssetsUtil {

    /**
     * 加载资源文件
     *
     * @param path
     * @return
     */
    public static String getAssets(Context context, String path) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = context.getAssets().open(path);
            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }
}
