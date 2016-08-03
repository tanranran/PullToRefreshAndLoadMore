package com.tan.pulltorefreshandloadmore.sample.ui;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.tan.pulltorefreshandloadmore.impl.OnLoadMoreListener;
import com.tan.pulltorefreshandloadmore.sample.R;
import com.tan.pulltorefreshandloadmore.sample.base.BaseActivity;
import com.tan.pulltorefreshandloadmore.ui.ExpandableListViewLoadMore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.customize.OnDefaultRefreshListener;

/**
 * User: Tanranran(tanjuran@gmail.com)
 * Date: 2016-08-03
 * Time: 14:58
 */
public class ExpandableListDemoActivity extends BaseActivity{

    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private SimpleExpandableListAdapter mExpandableListViewAdapter;
    private ExpandableListViewLoadMore expandableListViewLoadMore;
    private List<Map<String, String>> mGroupList;
    private List<List<Map<String, String>>> mChildList;
    private static final String KEY = "key";
    private final int MAX_SIZE = 50;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view_demo);
        initView();
        initData();
    }
    public void initView(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ptrClassicFrameLayout=getView(R.id.ptr_frame);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            public void onRefreshBegin(final PtrFrameLayout frame) {
                requestData(true);
            }
        });
        expandableListViewLoadMore=getView(R.id.lv_load_more);

        TextView textView=new TextView(context);
        textView.setText("我是HeaderView");
        textView.setPadding(100,100,100,100);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.RED);
        textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,AbsListView.LayoutParams.WRAP_CONTENT));
        expandableListViewLoadMore.addHeaderView(textView);
        expandableListViewLoadMore.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                requestData(false);
            }
        });
    }
    public void initData(){
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
        mExpandableListViewAdapter = new SimpleExpandableListAdapter(context, mGroupList, android.R.layout.simple_expandable_list_item_1,
                new String[]{KEY}, new int[]{android.R.id.text1}, mChildList,
                android.R.layout.simple_expandable_list_item_2, new String[]{KEY}, new int[]{android.R.id.text1});

        expandableListViewLoadMore.setAdapter(mExpandableListViewAdapter);
        ptrClassicFrameLayout.autoRefresh();
    }
    private void requestData(final boolean refresh) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int loadMoreSize = 2;
                if (refresh) {
                    mChildList.clear();
                    mGroupList.clear();
                    loadMoreSize = 5;
                }
                for (int i = 0; i < loadMoreSize; i++) {

                    Map<String, String> groupMap1 = new HashMap<String, String>();
                    groupMap1.put(KEY, "Group:" + mGroupList.size());
                    mGroupList.add(groupMap1);

                    List<Map<String, String>> childList = new ArrayList<>();
                    for (int j = 0; j < 6; j++) {
                        Map<String, String> childMap = new HashMap<>();
                        childList.add(childMap);
                        if (refresh) {
                            childMap.put(KEY, "Child item " + j);
                        } else {
                            childMap.put(KEY, "Child item " + j + "(LoadMore)");
                        }
                    }
                    mChildList.add(childList);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (isFinishing()) {
                    return;
                }

                if (mGroupList.size() + mChildList.size() >= MAX_SIZE) {
                    expandableListViewLoadMore.setHasLoadMore(false);
                } else {
                    expandableListViewLoadMore.setHasLoadMore(true);
                }

                if (refresh) {
                    expandableListViewLoadMore.expandGroup(0);
                    ptrClassicFrameLayout.refreshComplete();
                } else {
                    expandableListViewLoadMore.onLoadMoreComplete();
                }
                mExpandableListViewAdapter.notifyDataSetChanged();

            }
        }.execute();
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
