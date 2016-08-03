package com.tan.pulltorefreshandloadmore.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tan.pulltorefreshandloadmore.sample.adapter.ListDataAdapter;
import com.tan.pulltorefreshandloadmore.sample.base.BaseActivity;
import com.tan.pulltorefreshandloadmore.sample.ui.ExpandableListDemoActivity;
import com.tan.pulltorefreshandloadmore.sample.ui.GridViewDemoActivity;
import com.tan.pulltorefreshandloadmore.sample.ui.ListViewDemoActivity;
import com.tan.pulltorefreshandloadmore.sample.ui.RecyclerViewDemoActivity;
import com.tan.pulltorefreshandloadmore.sample.ui.ScrollViewDemoActivity;
import com.tan.pulltorefreshandloadmore.ui.SwipeRefreshLayoutReFresh;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Context  context;
    private ListView lv_load_more;
    private SwipeRefreshLayoutReFresh layoutReFresh;
    private List<String> listData=new ArrayList<>();
    private ListDataAdapter listDataAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        initView();
        initData();

    }

    public void initView(){
        lv_load_more= getView(R.id.lv_load_more);
        listDataAdapter=new ListDataAdapter(this,listData,R.layout.list_item_demo);
        listDataAdapter.openLoadAnimation();
        lv_load_more.setAdapter(listDataAdapter);
        lv_load_more.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(context, ListViewDemoActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(context, RecyclerViewDemoActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(context, GridViewDemoActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(context, ExpandableListDemoActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(context, ScrollViewDemoActivity.class));

                        break;
                }
            }
        });
        layoutReFresh=getView(R.id.refresh_layout);
        layoutReFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                layoutReFresh.postDelayed(new Runnable() {
                    public void run() {
                        layoutReFresh.onRefreshComplete();
                        Toast.makeText(context,"下拉刷新完毕",Toast.LENGTH_LONG);//下拉刷新完毕
                    }
                },500);
            }
        });
    }

    public void initData(){
        listData.add("ListView");
        listData.add("RecyclerView");
        listData.add("GridView");
        listData.add("ExpandableListView");
        listData.add("ScrollView");
        listDataAdapter.addAll(listData);
    }
    public void onBackPressed() {
        System.exit(0);
    }
}
