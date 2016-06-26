package com.tan.pulltorefreshandloadmore.mode;

/**
 * User: Tanranran(tanjuran@gmail.com)
 * Date: 2016-06-21
 * Time: 11:37
 */
public enum LoadMoreMode {
    /**
     * 点击加载更多
     */
    CLICK,
    /**
     * 滑动到底部加载跟多
     */
    SCROLL;

    public static LoadMoreMode mapIntToValue(int modeInt) {
        switch (modeInt) {
            case 0x0:
            default:
                return CLICK;
            case 0x1:
                return SCROLL;
        }
    }
}
