package com.tan.pulltorefreshandloadmore.impl;

import android.view.View;

/**
 * User: Tanranran(tanjuran@gmail.com)
 * Date: 2016-06-21
 * Time: 11:34
 */
public interface ILoadMoreView {
    /**
     * 显示普通布局
     */
    void showNormal();

    /**
     * 显示点击布局
     */
    void showNormalClick();

    /**
     * 显示已经加载完成，没有更多数据的布局
     */
    void showNoMore();

    /**
     * 显示正在加载中的布局
     */
    void showLoading();

    /**
     * 显示加载失败的布局
     */
    void showFail();

    /**
     * 隐藏布局
     */
    void hideView();

    /**
     * 显示布局
     */
    void showView();

    /**
     * 获取FooterView
     * @return
     */
    View getFooterView();


}
