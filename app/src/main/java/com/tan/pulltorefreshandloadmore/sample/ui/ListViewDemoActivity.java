package com.tan.pulltorefreshandloadmore.sample.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tan.pulltorefreshandloadmore.impl.OnLoadMoreListener;
import com.tan.pulltorefreshandloadmore.mode.LoadMoreMode;
import com.tan.pulltorefreshandloadmore.sample.R;
import com.tan.pulltorefreshandloadmore.sample.adapter.ListDataAdapter;
import com.tan.pulltorefreshandloadmore.sample.base.BaseActivity;
import com.tan.pulltorefreshandloadmore.ui.ListViewLoadMore;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.customize.OnDefaultRefreshListener;

/**
 * User: Tanranran(tanjuran@gmail.com)
 * Date: 2016-08-01
 * Time: 15:46
 */
public class ListViewDemoActivity extends BaseActivity {

    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private ListDataAdapter listDataAdapter;
    private ListViewLoadMore listViewLoadMore;
    private int headAdd=0,foodAdd=0;
    private List listData=new ArrayList();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_demo);
        initView();
        initData();
    }
    public void initView(){
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
    }
    public void initData(){
        listDataAdapter=new ListDataAdapter(this,listData,android.R.layout.simple_list_item_1);
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
                }, 100);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_show_no_more:
                if (item.isChecked()){
                    item.setChecked(false);
                    listViewLoadMore.setNoLoadMoreHideView(true);
                }else{
                    item.setChecked(true);
                    listViewLoadMore.setNoLoadMoreHideView(false);
                }
                break;
            case R.id.menu_click_scroll:
                if (item.isChecked()){
                    item.setChecked(false);
                    listViewLoadMore.setLoadMoreMode(LoadMoreMode.SCROLL);
                }else{
                    item.setChecked(true);
                    listViewLoadMore.setLoadMoreMode(LoadMoreMode.CLICK);
                }
                break;
            default:
                break;

        }
        foodAdd=0;
        initData();
        return super.onOptionsItemSelected(item);
    }
}