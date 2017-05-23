package me.pengtao.nochat.msg;

import android.widget.TextView;

import me.pengtao.nochat.R;
import me.pengtao.nochat.base.IMessage;
import me.pengtao.nochat.viewholder.base.MsgViewBase;

/**
 * 提醒类消息类型，如显示顶部的时间小方块等
 * Created by 90Chris on 2016/4/20.
 */
public class TipMsgView extends MsgViewBase {
    private TextView tvTip;

    @Override
    protected int getResId() {
        return R.layout.msgkit_tip;
    }

    @Override
    protected void inflate() {
        tvTip = findView(R.id.msgkit_tip_content);
    }

    @Override
    public void refresh(IMessage item) {
        TipMsgFormat tipFormat = (TipMsgFormat)item.getFormat();
        tvTip.setText(tipFormat.getContent());
    }
}
