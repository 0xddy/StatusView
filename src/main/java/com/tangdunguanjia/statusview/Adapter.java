package com.tangdunguanjia.statusview;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashMap;

/**
 * Created by dingyong on 2018/4/20.
 */
public class Adapter {

    /**
     * 存储各种状态的View集合
     **/
    private LinkedHashMap<Integer, View> viewsMap = new LinkedHashMap<>();

    /**
     * 当前显示的View id
     */
    private int currView;

    private boolean REMOVE_STATUS = false;

    public void add(int id, @NonNull View view) {

        if (viewsMap.containsKey(id)) {
            if (viewsMap.get(id) != view) {
                viewsMap.remove(id);
            } else {
                //有重复值不添加
                return;
            }
        }
        viewsMap.put(id, view);
    }

    public View get(int id) {
        return viewsMap.get(id);
    }

    public int getCurrViewId() {
        return currView;
    }


    protected void setCurrView(int id) {
        currView = id;
    }

    /**
     * 检测是否是statusView添加的View
     *
     * @return
     */
    protected boolean checkView(int id) {
        return viewsMap.containsKey(id);
    }

    protected boolean checkView(View view) {
        return viewsMap.containsValue(view);
    }

    protected void removeCurrStatusView() {
        REMOVE_STATUS = true;
    }

    /**
     * 刷新View操作，所有操作需要调用该方法生效
     */
    protected void notifyDataSetChanged(ViewGroup view) {

        int childNum = view.getChildCount();
        if (childNum > 0) {
            //获取最上面的View
            View firstView = view.getChildAt(0);
            boolean isStatusView = checkView(firstView);
            if (isStatusView) {
                //最上层是一个状态视图
                //如果现在设置的视图就是目前显示的视图，不操作。
                View newView;
                if ((newView = get(getCurrViewId())) != firstView) {
                    //删除目前显示的
                    view.removeView(firstView);
                    addView(newView, view);
                }
            } else {
                // add statusView
                addView(get(getCurrViewId()), view);

            }
        } else {
            // add statusView
            addView(get(getCurrViewId()), view);
        }
        //标记刷新UI模式,判断是恢复显示正常内容 or 显示statusView
        boolean mode = false;
        if (REMOVE_STATUS) {
            mode = true;
            //需要移除statusView恢复正常状态
            if (view.getChildCount() > 0) {
                if (checkView(getCurrViewId())) {
                    REMOVE_STATUS = false;
                    try {
                        //viewsMap.remove(getCurrViewId());
                        view.removeViewAt(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //防止意外，回滚标记
                        REMOVE_STATUS = true;
                        mode = false;
                    }
                }
            }
        }

        View tempView;
        for (int i = 0; i < view.getChildCount(); i++) {

            boolean isStatusView = checkView(tempView = view.getChildAt(i));
            if (isStatusView) {
                tempView.setVisibility(mode ? View.GONE : View.VISIBLE);
            } else {
                tempView.setVisibility(mode ? View.VISIBLE : View.GONE);
            }

        }

    }

    private void addView(View newView, ViewGroup contentView) {

        if (newView == null || getCurrViewId() == 0) {
            //移除statusView恢复正常状态
            return;
        }

        ViewGroup.LayoutParams ralp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.addView(newView, 0, ralp);
    }

}
