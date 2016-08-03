package com.tan.pulltorefreshandloadmore.sample.adapter;

import android.content.Context;
import android.util.TypedValue;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * User: Tanranran(tanjuran@gmail.com)
 * Date: 2016-08-01
 * Time: 18:04
 */
public class ListDataAdapter extends SuperAdapter<String> {
    private int resourceId;
    public ListDataAdapter(Context context, List<String> list, int layoutResId) {
        super(context, list, layoutResId);
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        resourceId=outValue.resourceId;
    }
    public void onBind(SuperViewHolder holder, int viewType, int position, String item) {
        holder.setText(android.R.id.text1, item);
        holder.itemView.setBackgroundResource(resourceId);
    }
}
