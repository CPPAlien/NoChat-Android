package me.pengtao.nochat_example.panel;

import me.pengtao.nochat.base.IMessage;

/**
 * IChatView
 * Created by 90Chris on 2016/4/21.
 */
public interface IChatView {
    void initMessageList();
    void initKeyboard();
    String getInputAreaContent();
    void clearTypedMessage();

    void displayMsg(IMessage message);
}
