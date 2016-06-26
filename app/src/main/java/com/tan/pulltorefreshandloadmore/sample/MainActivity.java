package com.tan.pulltorefreshandloadmore.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tan.pulltorefreshandloadmore.impl.OnLoadMoreListener;
import com.tan.pulltorefreshandloadmore.ui.ListViewLoadMore;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.customize.OnDefaultRefreshListener;

public class MainActivity extends AppCompatActivity {

    private ListViewLoadMore lv_load_more;
    private PtrClassicFrameLayout ptr_frame;
    private List<String> listData=new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_load_more= (ListViewLoadMore) findViewById(R.id.lv_load_more);
        ptr_frame= (PtrClassicFrameLayout) findViewById(R.id.store_house_ptr_frame);

        for (int i=0;i<3;i++){
            listData.add("测试啊啊");
        }
        final SingleAdapter singleAdapter=new SingleAdapter(this,listData,android.R.layout.simple_list_item_1);
        ptr_frame.setOnRefreshListener(new OnDefaultRefreshListener() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptr_frame.refreshComplete();
            }
        });
        lv_load_more.setHasLoadMore(true);
        lv_load_more.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                lv_load_more.postDelayed(new Runnable() {
                    public void run() {
                        for (int i=0;i<5;i++){
                            listData.add("测试啊啊");
                        }
                        singleAdapter.addAll(listData);
                        lv_load_more.setHasLoadMore(true);
                        if(listData.size()>=15){
                            lv_load_more.onLoadMoreComplete();
                        }
                    }
                },1000);

            }
        });
        lv_load_more.setAdapter(singleAdapter);
    }

    public class SingleAdapter extends SuperAdapter<String> {
        public SingleAdapter(Context context, List<String> list, int layoutResId) {
            super(context, list, layoutResId);
        }
        public void onBind(SuperViewHolder holder, int viewType, int position, String item) {
            holder.setText(android.R.id.text1, item+position);
        }
    }
    public void onBackPressed() {
        System.exit(0);
    }
}
