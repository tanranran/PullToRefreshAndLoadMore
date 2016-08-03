package com.tan.pulltorefreshandloadmore.sample.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import com.tan.pulltorefreshandloadmore.adapter.HeaderAndFooterRecyclerViewAdapter;
import com.tan.pulltorefreshandloadmore.impl.OnLoadMoreListener;
import com.tan.pulltorefreshandloadmore.sample.R;
import com.tan.pulltorefreshandloadmore.sample.adapter.ListDataAdapter;
import com.tan.pulltorefreshandloadmore.sample.base.BaseActivity;
import com.tan.pulltorefreshandloadmore.ui.RecyclerViewLoadMore;
import com.tan.pulltorefreshandloadmore.util.RecyclerViewOnScrollListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.customize.OnDefaultRefreshListener;

/**
 * User: Tanranran(tanjuran@gmail.com)
 * Date: 2016-08-02
 * Time: 16:35
 */
public class RecyclerViewDemoActivity extends BaseActivity {

    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private ListDataAdapter listDataAdapter;
    private RecyclerViewLoadMore listViewLoadMore;
    private int headAdd=0,foodAdd=0;
    private List listData=new ArrayList();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);
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
        listViewLoadMore=getView(R.id.lv_load_more);

        TextView textView=new TextView(context);
        textView.setText("我是HeaderView");
        textView.setPadding(100,100,100,100);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.RED);
        textView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT));
        listViewLoadMore.addHeaderView(textView);
        listViewLoadMore.setOnItemClickListener(new HeaderAndFooterRecyclerViewAdapter.OnItemClickListener() {
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
            }
        });
        listViewLoadMore.setOnItemLongClickListener(new HeaderAndFooterRecyclerViewAdapter.OnItemLongClickListener() {
            public boolean onItemLongCLick(RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        listViewLoadMore.addOnScrollListener(new RecyclerViewOnScrollListener(listViewLoadMore.getLayoutManager()) {
            public void onScrolledToTop() {
            }
            public void onScrolledToBottom() {
            }
        });
        listViewLoadMore.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#ADAAAA"))
                        .size(1)
                        .build());
    }
    public void initData(){
        listDataAdapter=new ListDataAdapter(this,listData,R.layout.list_item_demo);
        listDataAdapter.openLoadAnimation();
        listDataAdapter.setOnlyOnce(false);
        listViewLoadMore.setAdapter(listDataAdapter);
        for (int i=0;i<20;i++){
            listData.add("测试数据"+i);
        }
        listViewLoadMore.setHasLoadMore(true);
        listDataAdapter.addAll(listData);
        listViewLoadMore.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                listViewLoadMore.postDelayed(new Runnable() {
                    public void run() {
                        if(foodAdd<3){
                            foodAdd++;
                            listDataAdapter.add("上拉加载的数据"+foodAdd);
                            listViewLoadMore.setHasLoadMore(true);
                        }else{
                            listViewLoadMore.setHasLoadMore(false);
                        }
                        listViewLoadMore.onLoadMoreComplete();
                    }
                }, 1000);
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