package com.tan.pulltorefreshandloadmore.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.tan.pulltorefreshandloadmore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Tanranran(tanjuran@gmail.com)
 * Date: 2016-08-02
 * Time: 16:10
 */
public class SwipeRefreshLayoutReFresh extends SwipeRefreshLayout {
    private OnRefreshListener mOnRefreshListener;

    private Handler mHandler = new Handler();
    private List<View> mSwipeAbleScrollChildren = new ArrayList<>();
    private int mTouchSlop;
    private float mPrevX;
    private boolean mDeclined;

    public SwipeRefreshLayoutReFresh(Context context) {
        super(context);

        init(context, null);
    }

    public SwipeRefreshLayoutReFresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeRefreshLayoutRefresh);
        if(a.hasValue(R.styleable.SwipeRefreshLayoutRefresh_refreshLoadingColor)) {
            int color = a.getColor(R.styleable.SwipeRefreshLayoutRefresh_refreshLoadingColor, Color.BLACK);
            setColorSchemeColors(color);
        }
        a.recycle();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        setSize(SwipeRefreshLayout.DEFAULT);
        //设置进度条的颜色主题，最多能设置四种 加载颜色是循环播放的，只要没有完成刷新就会一直循环，
        setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        super.setOnRefreshListener(listener);
        this.mOnRefreshListener = listener;
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshing(true);
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onRefresh();
                }
            }
        }, 200);
    }

    public void onRefreshComplete() {
        setRefreshing(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        getViewGroup(this);
    }

    public void getViewGroup(ViewGroup viewGroup ) {
        for(int i = 0 ; i < viewGroup.getChildCount(); i++){
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                if (view instanceof ScrollView || view instanceof ListView
                        || view instanceof GridView || view instanceof RecyclerView) {
                    mSwipeAbleScrollChildren.add(view);
                } else {
                    getViewGroup((ViewGroup) view);
                }
            }
        }
    }

    @Override
    public boolean canChildScrollUp() {
        // 检查是否可以下拉刷新
        for (View view : mSwipeAbleScrollChildren) {
            if (view.isShown() && ViewCompat.canScrollVertically(view, -1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event).getX();
                mDeclined = false; // New action
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);

                if (mDeclined || xDiff > mTouchSlop) {
                    mDeclined = true; // Memorize
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }
}
