package com.tan.pulltorefreshandloadmore.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;

import com.tan.pulltorefreshandloadmore.R;
import com.tan.pulltorefreshandloadmore.impl.ILoadMoreView;
import com.tan.pulltorefreshandloadmore.impl.OnLoadMoreListener;
import com.tan.pulltorefreshandloadmore.impl.OnScrollBottomListener;
import com.tan.pulltorefreshandloadmore.mode.LoadMoreMode;
import com.tan.pulltorefreshandloadmore.view.DefaultLoadMoreView;

import java.lang.reflect.Constructor;

/**
 * User: Tanranran(tanjuran@gmail.com)
 * Date: 2016-06-22
 * Time: 15:03
 */
public class ExpandableListViewLoadMore extends ExpandableListView implements OnScrollBottomListener {

    /**
     * 加载更多UI
     */
    ILoadMoreView mLoadMoreView;

    /**
     * 加载更多方式，默认滑动到底部加载更多
     */
    LoadMoreMode mLoadMoreMode = LoadMoreMode.SCROLL;
    /**
     * 加载更多lock
     */
    private boolean mLoadMoreLock;
    /**
     * 是否可以加载跟多
     */
    boolean mHasLoadMore = true;
    /**
     * 是否加载失败
     */
    private boolean mHasLoadFail;

    /**
     * 加载更多事件回调
     */
    private OnLoadMoreListener mOnLoadMoreListener;

    /**
     * 没有更多了是否隐藏loadMoreView
     */
    private boolean mNoLoadMoreHideView;

    /**
     * 表示是否是第一次添加footerView
     */

    private boolean mAddLoadMoreFooterFlag;

    /**
     * 表示是否footView 显示状态
     */
    private boolean mHasLoadMoreViewShowState;

    public ExpandableListViewLoadMore(Context context) {
        super(context);
        init(context, null);
    }

    public ExpandableListViewLoadMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandableListViewLoadMore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadMore);
        mLoadMoreMode = LoadMoreMode.SCROLL;
        if (a.hasValue(R.styleable.LoadMore_loadMoreMode)) {
            mLoadMoreMode = LoadMoreMode.mapIntToValue(a.getInt(R.styleable.LoadMore_loadMoreMode, 0x01));
        } else {
            mLoadMoreMode = LoadMoreMode.SCROLL;
        }
        if (a.hasValue(R.styleable.LoadMore_noLoadMoreHideView)) {
            mNoLoadMoreHideView = a.getBoolean(R.styleable.LoadMore_noLoadMoreHideView, false);
        } else {
            mNoLoadMoreHideView = false;
        }
        if (a.hasValue(R.styleable.LoadMore_loadMoreView)) {
            try {
                String loadMoreViewName = a.getString(R.styleable.LoadMore_loadMoreView);
                Class clazz = Class.forName(loadMoreViewName);
                Constructor c = clazz.getConstructor(Context.class);
                ILoadMoreView loadMoreView = (ILoadMoreView) c.newInstance(context);
                mLoadMoreView = loadMoreView;
            } catch (Exception e) {
                e.printStackTrace();
                mLoadMoreView = new DefaultLoadMoreView(context);
            }
        } else {
            mLoadMoreView = new DefaultLoadMoreView(context);
        }
        mLoadMoreView = new DefaultLoadMoreView(context);
        mLoadMoreView.getFooterView().setOnClickListener(new OnMoreViewClickListener());
        setOnScrollListener(new ListViewOnScrollListener());
        a.recycle();
    }

    public void setAdapter(ListAdapter adapter) {
        if (!mAddLoadMoreFooterFlag) {
            mAddLoadMoreFooterFlag = true;
            addFooterView(mLoadMoreView.getFooterView());
        }
        super.setAdapter(adapter);
        if( adapter instanceof BaseAdapter) {
            try {
                adapter.unregisterDataSetObserver(mDataObserver);
            } catch (Exception e){}
            adapter.registerDataSetObserver(mDataObserver);
        }
    }

    public void onScrollBottom() {
        if(mHasLoadMore && mLoadMoreMode == LoadMoreMode.SCROLL) {
            executeLoadMore();
        }
    }

    /**
     * 设置LoadMoreView(需要在setAdapter之前)
     * @param loadMoreView
     */
    public void setLoadMoreView(ILoadMoreView loadMoreView) {
        mLoadMoreView = loadMoreView;
        mLoadMoreView.getFooterView().setOnClickListener(new OnMoreViewClickListener());
    }

    /**
     * 设置加载更多模式
     * @param mode
     */
    public void setLoadMoreMode(LoadMoreMode mode) {
        mLoadMoreMode = mode;
    }

    /**
     * 设置没有更多数据了，是否隐藏footer view
     * @param hide
     */
    public void setNoLoadMoreHideView(boolean hide) {
        this.mNoLoadMoreHideView = hide;
    }

    /**
     * 没有很多了
     */
    public void showNoMoreUI() {
        mLoadMoreLock = true;
        mLoadMoreView.showNoMore();
    }

    /**
     * 显示失败UI
     */
    public void showFailUI() {
        mHasLoadFail = true;
        mLoadMoreLock = false;
        mLoadMoreView.showFail();
    }

    /**
     * 显示默认UI
     */
    void showNormalUI() {
        mLoadMoreLock = false;
        if(mLoadMoreMode==LoadMoreMode.CLICK){
            mLoadMoreView.showNormalClick();
        }else{
            mLoadMoreView.showNormal();
        }

    }

    /**
     * 显示加载中UI
     */
    void showLoadingUI(){
        mHasLoadFail = false;
        mLoadMoreView.showLoading();
    }

    /**
     * 是否有更多
     * @param hasLoadMore
     */
    public void setHasLoadMore(boolean hasLoadMore) {
        mHasLoadMore = hasLoadMore;
        if (!mHasLoadMore) {
            showNoMoreUI();
            if(mNoLoadMoreHideView && mHasLoadMoreViewShowState) {
                hideLoadMoreView();
            }
        } else {
            if(!mHasLoadMoreViewShowState) {
                showLoadMoreView();
            }
            showNormalUI();
        }
    }

    /**
     * 设置加载更多事件回调
     * @param loadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.mOnLoadMoreListener = loadMoreListener;
    }

    /**
     * 完成加载更多
     */
    public void onLoadMoreComplete() {
        setHasLoadMore(false);
        hideLoadMoreView();
    }

    /**
     * 点击more view加载更多
     */
    class OnMoreViewClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            if(mHasLoadMore) {
                executeLoadMore();
            }
        }
    }

    /**
     * 加载更多
     */
    void executeLoadMore() {
        loadMore();
    }

    public void loadMore(){
        if(!mLoadMoreLock && mHasLoadMore) {
            showLoadMoreView();
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.loadMore();
            }
            mLoadMoreLock = true;//上锁
            showLoadingUI();
        }
    }
    private void hideLoadMoreView() {
        mHasLoadMoreViewShowState = false;
        mLoadMoreView.hideView();
    }

    private void showLoadMoreView(){
        mHasLoadMoreViewShowState = true;
        mLoadMoreView.showView();
    }

    /**
     * 滚动到底部自动加载更多数据
     */
    private class ListViewOnScrollListener implements OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView listView, int scrollState) {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                    && listView.getLastVisiblePosition() + 1 == listView.getCount()) {// 如果滚动到最后一行
                onScrollBottom();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

    /**
     * 刷新数据时停止滑动,避免出现数组下标越界问题
     */
    private DataSetObserver mDataObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
        }

        @Override
        public void onInvalidated() {
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        hideLoadMoreView();
    }
}
