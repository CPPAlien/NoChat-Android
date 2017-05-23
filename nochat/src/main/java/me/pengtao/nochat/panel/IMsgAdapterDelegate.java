package me.pengtao.nochat.panel;

import me.pengtao.nochat.viewholder.base.MsgViewBase;

/**
 * delegate of msg adapter
 * Created by 90Chris on 2016/4/21.
 */
public interface IMsgAdapterDelegate {
    int getViewTypeCount();
    Class<? extends MsgViewBase> viewAtPosition(int pos);
}
