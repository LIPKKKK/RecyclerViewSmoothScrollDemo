package com.weds.recyclerviewscrolldemo.weight;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by lip on 2017/2/24.
 * 控制recycler滚动速度manager
 */

public class ControlRvSpeedLinearLayoutManager extends LinearLayoutManager {

    public static final String NORMAL = "normal";

    public static final String SLOW = "slow";

    public static final String EXTREMELY_SLOW = "extremelySlow";

    /**
     * 滚动完成回调
     */
    private StopScrollCallBack outStopScrollCallBack;

    /**
     * 滚动速度
     */
    private String speed;

    /**
     *
     * @param context 上下文
     * @param stopScrollCallBack 滚动完成回调
     * @param speed 滚动速度
     */
    public ControlRvSpeedLinearLayoutManager(Context context, StopScrollCallBack stopScrollCallBack,String speed) {
        super(context);
        this.outStopScrollCallBack = stopScrollCallBack;
        this.speed = speed;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, final int position) {

        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                Log.i("滚动demo","======="+displayMetrics.densityDpi);
                float ms = 1f;
                switch (speed){
                    case NORMAL:
                        ms = 1f;
                        break;
                    case SLOW:
                        ms = 5f;
                        break;
                    case EXTREMELY_SLOW:
                        ms = 10f;
                        break;
                }
                return ms;//返回滚过1px需要多少ms
            }

            @Override
            protected void onStop() {
                super.onStop();
                outStopScrollCallBack.scrollStop(position);
            }

        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

    public interface StopScrollCallBack {
        void scrollStop(int position);
    }

}
