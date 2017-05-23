package me.pengtao.nochat.viewholder.base;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.pengtao.nochat.R;
import me.pengtao.nochat.base.IMessage;
import me.pengtao.nochat.constant.AccountType;
import me.pengtao.nochat.constant.MsgStatus;
import me.pengtao.nochat.util.TimeUtil;

/**
 * 普通消息视图，头像加气泡显示形式
 * Created by 90Chris on 2016/4/20.
 */
public abstract class MsgBubbleView extends MsgViewBase {
    private ImageView avLeft;
    private ImageView avRight;
    private ProgressBar pbLoading;
    private TextView tvTime;
    private ImageView ivError;
    private FrameLayout flContainer;
    private IMessage msg;


    @Override
    protected int getResId() {
        return R.layout.uikit_msg_bubble;
    }

    @Override
    protected void inflate() {
        avLeft = findView(R.id.msg_avatar_left);
        avRight = findView(R.id.msg_avatar_right);
        pbLoading = findView(R.id.msg_progress);
        tvTime = findView(R.id.msg_time_tag);
        ivError = findView(R.id.msg_error);
        flContainer = findView(R.id.msg_container);

        View.inflate(getContext(), getContentResId(), flContainer);
        inflateContentView();
    }

    @Override
    public void refresh(IMessage item) {
        msg = item;
        setAvatarView();
        setStatus();

        setContent();
        bindContentView();
    }

    final protected IMessage getMsg() {
        return msg;
    }

    private void setAvatarView() {
        ImageView show = isSendMessage() ? avRight : avLeft;
        ImageView hide = isSendMessage() ? avLeft : avRight;

        show.setVisibility(View.VISIBLE);
        hide.setVisibility(View.GONE);

        //DaVinci.with().getImageLoader().load(msg.getSender().getAvatarUrl()).into(show, R.drawable.default_avatar, R.drawable.default_avatar);
        if (listener != null) {
            listener.onAvatarLoad(show, msg.getSender());
        }

        show.setOnClickListener(new AvatarClick());
    }

    private void setStatus() {
        MsgStatus status = msg.getStatus();
        tvTime.setCompoundDrawables(null, null, null, null);

        switch ( status ) {
            case SENDING:
                setStatusView(View.VISIBLE, View.GONE, View.GONE);
                break;
            case SUCCESS:
                tvTime.setCompoundDrawables(null, null, getContext().getResources().getDrawable(R.drawable.msg_check), null);
            case RECEIVED:
                setStatusView(View.GONE, View.VISIBLE, View.GONE);
                tvTime.setText(TimeUtil.getHHmm(msg.getTime()));
                break;
            case FAILED:
                setStatusView(View.GONE, View.GONE, View.VISIBLE);
                break;
        }
    }

    private void setStatusView(int loading, int time, int error) {
        ivError.setVisibility(error);
        tvTime.setVisibility(time);
        pbLoading.setVisibility(loading);
    }

    protected boolean isSendMessage() {
        return msg.getSender().getType() == AccountType.SELF;
    }

    protected final void setGravity(View view, int gravity) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = gravity;
    }

    protected int leftBackground() {
        return R.drawable.bubble_receive;
    }

    protected int rightBackground() {
        return R.drawable.bubble_send;
    }

    private void setContent() {
        LinearLayout bodyContainer = (LinearLayout) view.findViewById(R.id.msg_body);
        int index = isSendMessage() ? 3 : 0;
        if (bodyContainer.getChildAt(index) != flContainer) {
            bodyContainer.removeView(flContainer);
            bodyContainer.addView(flContainer, index);
        }

        if (isSendMessage()) {
            setGravity(bodyContainer, Gravity.END);
            flContainer.setBackgroundResource(rightBackground());
        } else {
            setGravity(bodyContainer, Gravity.START);
            flContainer.setBackgroundResource(leftBackground());
        }

        flContainer.setOnClickListener(new BubbleClick());
        flContainer.setOnLongClickListener(new BubbleLongClick());
    }

    abstract protected int getContentResId();

    abstract protected void inflateContentView();

    abstract protected void bindContentView();

    protected void onBubbleClicked(){}

    protected boolean onBubbleLongClicked(){return false;}

    private class AvatarClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if ( listener != null ) listener.onAvatarClick(msg.getSender());
        }
    }

    private class BubbleClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            onBubbleClicked();
        }
    }

    private class BubbleLongClick implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            return onBubbleLongClicked();
        }
    }
}
