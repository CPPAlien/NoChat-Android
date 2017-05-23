package me.pengtao.nochat_example.panel;

import me.pengtao.nochat.NoChatAssembler;
import me.pengtao.nochat.base.AccountEntity;
import me.pengtao.nochat.base.IMessage;
import me.pengtao.nochat.constant.AccountType;
import me.pengtao.nochat.constant.MsgStatus;

/**
 * ChatPresenter
 * Created by 90Chris on 2016/4/21.
 */
public class ChatPresenter {
    private final IChatView mIView;

    public ChatPresenter( IChatView iView ) {
        mIView = iView;
    }

    public void init() {
        mIView.initMessageList();
        mIView.initKeyboard();
    }

    public void sendButtonClicked() {
        String content = mIView.getInputAreaContent();
        if ( content.trim().isEmpty() ) { // filter empty msg
            return;
        }
        AccountEntity.Builder builder = new AccountEntity.Builder();
        builder.type(AccountType.SELF);
        IMessage msg = NoChatAssembler.assembleTextMsg("1", "1", builder.build(), content, MsgStatus.SENDING);
        showMessage(msg);
        mIView.clearTypedMessage();
    }

    private void showMessage(IMessage msg) {
        mIView.displayMsg(msg);
        //mAgentService.sendTextMessage(messageId, content);
    }
}
