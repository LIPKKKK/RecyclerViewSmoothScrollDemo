package com.weds.recyclerviewscrolldemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.weds.recyclerviewscrolldemo.R;
import com.weds.recyclerviewscrolldemo.adapter.BaseRecyclerAdapter;
import com.weds.recyclerviewscrolldemo.adapter.RecyclerViewHolder;
import com.weds.recyclerviewscrolldemo.adapter.SpaceItemDecoration;
import com.weds.recyclerviewscrolldemo.weight.ControlRvSpeedLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.rv_text_list)
    RecyclerView rvTextList;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 20:
                    rvTextList.smoothScrollToPosition(20);
                    break;
                case 0:
                    rvTextList.scrollToPosition(0);
                    handler.sendEmptyMessageDelayed(20,5000);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private List<String> strings = new ArrayList<>();

    private void initData() {
        for (int i = 0; i < 20; i++) {
            strings.add("第" + i + "条测试数据====~a");
        }
    }

    private void initView() {
        BaseRecyclerAdapter<String> recyclerAdapter = new BaseRecyclerAdapter<String>(MainActivity.this, strings) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.text_rv_item_layout;
            }

            @Override
            public void bindData(RecyclerView.ViewHolder holder, int position, String item) {
                RecyclerViewHolder rv = (RecyclerViewHolder) holder;
                rv.setText(R.id.tv_text, item);
            }
        };
        ControlRvSpeedLinearLayoutManager controlRvSpeedLinearLayoutManager = new ControlRvSpeedLinearLayoutManager(MainActivity.this, new ControlRvSpeedLinearLayoutManager.StopScrollCallBack() {
            @Override
            public void scrollStop(final int position) {
                handler.sendEmptyMessageDelayed(0,1000);
            }
        },ControlRvSpeedLinearLayoutManager.EXTREMELY_SLOW);
        rvTextList.setLayoutManager(controlRvSpeedLinearLayoutManager);
        rvTextList.addItemDecoration(new SpaceItemDecoration(20));
        rvTextList.setAdapter(recyclerAdapter);
        handler.sendEmptyMessageDelayed(20,2000);
    }
}
