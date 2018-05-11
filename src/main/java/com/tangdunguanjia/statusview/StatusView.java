package com.tangdunguanjia.statusview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntRange;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by dingyong on 2017/5/26.
 */
public class StatusView extends RelativeLayout {

    private Adapter adapter;
    private Context context;

    public StatusView(Context context) {
        this(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.statusView, defStyleAttr, 0);
        int emptyViewId = typedArray.getResourceId(R.styleable.statusView_empty_view, 0);
        int errViewId = typedArray.getResourceId(R.styleable.statusView_err_view, 0);
        int loadViewId = typedArray.getResourceId(R.styleable.statusView_loading_view, 0);

        //应用xml属性设置的布局
        apply(context, emptyViewId);
        apply(context, errViewId);
        apply(context, loadViewId);

        typedArray.recycle();
    }

    private void init() {
        //初始化 数据源
        adapter = new Adapter();

    }

    /**
     * @param id 获取xml属性设置的View，传ResId
     */
    public View getView(int id) {

        return adapter.get(id);
    }

    private void apply(Context context, int resId) {
        if (resId != 0) {
            View view = inflate(context, resId, null);
            adapter.add(resId, view);
        }
    }

    public void registerView(@IntRange(from = 1) int id, @NonNull View view) {
        adapter.add(id, view);
    }

    public void registerView(@LayoutRes int resId) {
        apply(context, resId);
    }

    public void registerView(@IntRange(from = 1) int id, @LayoutRes int resId) {
        View view = inflate(context, resId, null);
        adapter.add(id, view);
    }

    /**
     * 设置 显示的视图状态，最重要
     */
    public void setStatus(int id) {
        adapter.setCurrView(id);
    }

    public void removeStatus() {
        adapter.removeCurrStatusView();
    }

    public void notifyDataSetChanged() {

        adapter.notifyDataSetChanged(this);

    }
}
