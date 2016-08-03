package com.tan.pulltorefreshandloadmore.sample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.linearlistview.LinearListView;
import com.tan.pulltorefreshandloadmore.impl.OnLoadMoreListener;
import com.tan.pulltorefreshandloadmore.sample.R;
import com.tan.pulltorefreshandloadmore.sample.adapter.ListDataAdapter;
import com.tan.pulltorefreshandloadmore.sample.base.BaseActivity;
import com.tan.pulltorefreshandloadmore.ui.ScrollViewLoadMore;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.customize.OnDefaultRefreshListener;

/**
 * User: Tanranran(tanjuran@gmail.com)
 * Date: 2016-08-03
 * Time: 15:41
 */
public class ScrollViewDemoActivity extends BaseActivity {

    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private ListDataAdapter listDataAdapter;
    private LinearListView mLinearListView;
    private ScrollViewLoadMore scrollViewLoadMore;
    private int headAdd=0,foodAdd=0;
    private List listData=new ArrayList();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view_demo);
        initView();
        initData();
    }
    public void initView(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ptrClassicFrameLayout=getView(R.id.ptr_frame);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    public void run() {
                        headAdd++;
                        listDataAdapter.add(0,"下拉刷新加载的数据"+headAdd);
                        frame.refreshComplete();
                    }
                }, 500);
            }
        });
        scrollViewLoadMore=getView(R.id.lv_load_more);
        mLinearListView=getView(R.id.linear_list_view);
    }
    public void initData(){
        listDataAdapter=new ListDataAdapter(this,listData,R.layout.list_item_demo);
        mLinearListView.setAdapter(listDataAdapter);
        for (int i=0;i<20;i++){
            listData.add("测试数据"+i);
        }
        scrollViewLoadMore.setHasLoadMore(true);
        listDataAdapter.addAll(listData);
        scrollViewLoadMore.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                scrollViewLoadMore.postDelayed(new Runnable() {
                    public void run() {
                        if(foodAdd<10){
                            listDataAdapter.add("上拉加载的数据"+foodAdd++);
                            listDataAdapter.add("上拉加载的数据"+foodAdd++);
                            listDataAdapter.add("上拉加载的数据"+foodAdd++);
                            scrollViewLoadMore.setHasLoadMore(true);
                        }else{
                            scrollViewLoadMore.setHasLoadMore(false);
                        }
                        scrollViewLoadMore.onLoadMoreComplete();
                    }
                }, 100);
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
