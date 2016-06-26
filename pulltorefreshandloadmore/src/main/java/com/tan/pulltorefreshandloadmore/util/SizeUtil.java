package com.tan.pulltorefreshandloadmore.util;

import android.content.Context;

/**
 * User: Tanranran(tanjuran@gmail.com)
 * Date: 2016-06-21
 * Time: 15:12
 */
public class SizeUtil {

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
