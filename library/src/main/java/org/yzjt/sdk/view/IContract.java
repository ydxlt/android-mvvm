package org.yzjt.sdk.view;

/**
 * Created by LT on 2018/12/21.
 */
public interface IContract {

    interface Presenter<V>{
        void initData();
        /**
         * 绑定view
         * @param view
         */
        void attachView(V view);
        /**
         * 解除绑定
         */
        void detachView();
    }

    interface View {

    }
}
