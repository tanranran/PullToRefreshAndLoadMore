package com.tan.pulltorefreshandloadmore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tan.pulltorefreshandloadmore.R;
import com.tan.pulltorefreshandloadmore.impl.ILoadMoreView;
import com.tan.pulltorefreshandloadmore.util.SizeUtil;

/**
 * User: Tanranran(tanjuran@gmail.com)
 * Date: 2016-06-21
 * Time: 11:45
 */
public class DefaultLoadMoreView extends LinearLayout implements ILoadMoreView {

    private TextView mTvMessage;
    private ProgressBar mPbLoading;
    private int padding;

    public DefaultLoadMoreView(Context context) {
        super(context);
        init(context);
    }

    public DefaultLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DefaultLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setGravity(Gravity.CENTER);
        this.padding= SizeUtil.dip2px(context,10);
        this.setPadding(padding,padding,padding,padding);
        this.inflate(context, R.layout.load_more_default, this);
        mPbLoading = (ProgressBar) findViewById(R.id.pb_load_more);
        mTvMessage = (TextView) findViewById(R.id.tv_load_more_msg);
        mTvMessage.setText(getResources().getText(R.string.loading_view_loading));

    }

    public void showNormal() {
        mPbLoading.setVisibility(View.GONE);
        mTvMessage.setText(R.string.loading_view_click_loading_more);
    }

    public void showNormalClick() {
        mPbLoading.setVisibility(View.GONE);
        mTvMessage.setText(R.string.loading_view_click_loading_more);
    }

    public void showNoMore() {
        mPbLoading.setVisibility(View.GONE);
        mTvMessage.setText(R.string.loading_view_no_more);
    }

    public void showLoading() {
        mPbLoading.setVisibility(View.VISIBLE);
        mTvMessage.setText(R.string.loading_view_loading);
    }

    public void showFail() {
        mPbLoading.setVisibility(View.GONE);
        mTvMessage.setText(R.string.loading_view_net_error);
    }

    public void hideView() {
        setPadding(0,-333,0,0);
        setVisibility(GONE);
    }

    public void showView() {
        setPadding(padding,padding,padding,padding);
        setVisibility(VISIBLE);
    }

    public View getFooterView() {
        return this;
    }

}
