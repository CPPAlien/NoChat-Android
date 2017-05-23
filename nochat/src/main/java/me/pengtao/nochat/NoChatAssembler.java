package me.pengtao.nochat;

import me.pengtao.nochat.base.IAccount;
import me.pengtao.nochat.base.IMessage;
import me.pengtao.nochat.base.MessageEntity;
import me.pengtao.nochat.constant.MsgStatus;
import me.pengtao.nochat.msg.TextMsgFormat;
import me.pengtao.nochat.msg.TipMsgFormat;
import me.pengtao.nochat.util.Tools;

/**
 * @author dq
 */
public class NoChatAssembler {

    public static IMessage assembleTimeMsg(String sessionId, String time) {
        TipMsgFormat formatTip = new TipMsgFormat(time);
        MessageEntity.Builder builder = new MessageEntity.Builder();
        return builder.id(Tools.generateMessageId()).status(MsgStatus.SUCCESS).time(System.currentTimeMillis()).sessionId(sessionId)
                .format(formatTip).build();
    }

    public static IMessage assembleTextMsg(String sessionId, String messageId, IAccount sender, String content, MsgStatus status) {
        TextMsgFormat formatText = new TextMsgFormat(content);
        MessageEntity.Builder builder = new MessageEntity.Builder();
        builder.id(messageId).sender(sender).sessionId(sessionId)
                .status(status).time(System.currentTimeMillis()).format(formatText);
        return builder.build();
    }
}
