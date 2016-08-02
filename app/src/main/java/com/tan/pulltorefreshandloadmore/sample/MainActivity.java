package com.tan.pulltorefreshandloadmore.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tan.pulltorefreshandloadmore.sample.adapter.ListDataAdapter;
import com.tan.pulltorefreshandloadmore.sample.base.BaseActivity;
import com.tan.pulltorefreshandloadmore.sample.ui.ListViewDemoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Context  context;
    private ListView lv_load_more;
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
        lv_load_more= (ListView) findViewById(R.id.lv_load_more);
        listDataAdapter=new ListDataAdapter(this,listData,android.R.layout.simple_list_item_1);
        listDataAdapter.openLoadAnimation();
        lv_load_more.setAdapter(listDataAdapter);
        lv_load_more.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(context, ListViewDemoActivity.class));
                        break;
                }
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
