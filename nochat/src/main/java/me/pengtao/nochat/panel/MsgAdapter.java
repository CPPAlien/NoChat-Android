package me.pengtao.nochat.panel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.pengtao.nochat.OnUIKitListener;
import me.pengtao.nochat.base.IMessage;
import me.pengtao.nochat.viewholder.base.MsgViewBase;

/**
 * MsgAdapter
 * Created by 90Chris on 2016/4/21.
 */
public class MsgAdapter extends BaseAdapter {
    private static final String TAG = "NoChat.MsgAdapter";
    private final List<IMessage> mItems;
    private final IMsgAdapterDelegate mDelegate;
    private final Map<Class<?>, Integer> mViewTypes;
    private final LayoutInflater mInflater;
    private OnUIKitListener mListener;

    public MsgAdapter(Context context, List<IMessage> items, IMsgAdapterDelegate delegate) {
        mDelegate = delegate;
        mItems = items;
        mViewTypes = new HashMap<>();
        mInflater = LayoutInflater.from(context);
    }

    public void setListener(OnUIKitListener listener) {
        mListener = listener;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public IMessage getItem(int position) {
        return position < getCount() ? mItems.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            try {
                convertView = viewAtPosition(position);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "viewAtPositionError", e);
                return null;
            }
        }

        MsgViewBase holder = (MsgViewBase) convertView.getTag();
        holder.refresh(getItem(position));

        return convertView;
    }

    private View viewAtPosition(int position) throws IllegalAccessException,
            InstantiationException {
        Class<?> viewHolder = mDelegate.viewAtPosition(position);
        MsgViewBase holder = (MsgViewBase) viewHolder.newInstance();
        holder.setListener(mListener);
        View view = holder.getView(mInflater);
        view.setTag(holder);
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        if (getViewTypeCount() <= 1) {
            return 0;
        }

        Class<?> clazz = mDelegate.viewAtPosition(position);
        if (mViewTypes.containsKey(clazz)) {
            return mViewTypes.get(clazz);
        } else {
            int type = mViewTypes.size();
            if (type < getViewTypeCount()) {
                mViewTypes.put(clazz, type);
                return type;
            }
        }
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        int count = mDelegate.getViewTypeCount();
        return count <= 1 ? 1 : count;
    }
}
