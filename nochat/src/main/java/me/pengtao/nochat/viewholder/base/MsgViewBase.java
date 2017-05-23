package me.pengtao.nochat.viewholder.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import me.pengtao.nochat.OnUIKitListener;
import me.pengtao.nochat.base.IMessage;

/**
 * base view of msg item
 * Created by 90Chris on 2016/4/20.
 */
public abstract class MsgViewBase {
    protected View view;
    protected OnUIKitListener listener;

    protected abstract int getResId();
    protected abstract void inflate();
    public abstract void refresh(IMessage item);

    public View getView(LayoutInflater inflater) {
        int resId = getResId();
        view = inflater.inflate(resId, null);
        inflate();
        return view;
    }

    public void setListener(OnUIKitListener listener) {
        this.listener = listener;
    }

    final protected Context getContext() {
        return view.getContext();
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(int resId) {
        return (T) (view.findViewById(resId));
    }
}
