package me.pengtao.nochat;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import java.util.List;

import me.pengtao.nochat.base.IMessage;
import me.pengtao.nochat.panel.IMsgAdapterDelegate;
import me.pengtao.nochat.panel.MsgAdapter;
import me.pengtao.nochat.viewholder.MsgViewFactory;
import me.pengtao.nochat.viewholder.base.MsgViewBase;

/**
 * MsgPanel
 * Created by 90Chris on 2016/6/3.
 */
public class MsgPanel extends ListView implements IMsgAdapterDelegate {
    private List<IMessage> msgContainer;
    private MsgAdapter mAdapter;

    public MsgPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewInit();
    }

    public MsgPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewInit();
    }

    public MsgPanel(Context context) {
        super(context);
        viewInit();
    }

    private void viewInit() {
        setTranscriptMode(TRANSCRIPT_MODE_ALWAYS_SCROLL);
        setDivider(null);
    }

    public void setContainer(List<IMessage> container) {
        this.msgContainer = container;
        mAdapter = new MsgAdapter(getContext(), msgContainer, this);
        setAdapter(mAdapter);
    }

    public void refresh() {
        mAdapter.notifyDataSetChanged();
        setSelection(mAdapter.getCount() - 1);
    }

    public void setListener(OnUIKitListener listener) {
        mAdapter.setListener(listener);
    }

    @Override
    public int getViewTypeCount() {
        return MsgViewFactory.getViewCount();
    }

    @Override
    public Class<? extends MsgViewBase> viewAtPosition(int pos) {
        return MsgViewFactory.getView(msgContainer.get(pos));
    }
}
