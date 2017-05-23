package me.pengtao.nochat.viewholder.base;

import me.pengtao.nochat.base.IMessage;

/**
 * undefined view type
 * Created by 90Chris on 2016/4/21.
 */
public class UndefView extends MsgViewBase {
    @Override
    protected int getResId() {
        return 0;
    }

    @Override
    protected void inflate() {

    }

    @Override
    public void refresh(IMessage item) {

    }
}
