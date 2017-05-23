package me.pengtao.nochat.db;

import java.util.List;

import me.pengtao.nochat.base.IAccount;
import me.pengtao.nochat.base.IMessage;
import me.pengtao.nochat.base.ISession;

/**
 * chainsea im local db rule
 * Created by 90Chris on 2016/5/1.
 */
public interface UIKitDBLocalRule {
    boolean insertAccount(IAccount account);
    IAccount queryAccount(String id);
    List<IAccount> queryAllAccounts();
    boolean deleteAccount(String id);

    boolean insertMessage(String sessionId, IMessage messageShow);
    boolean deleteMessage(String messageId);
    IMessage queryMessage(String messageId);
    List<IMessage> queryMessages(String sessionId);
    boolean clearMessagePanel(String sessionId);

    boolean insertSession(ISession session);
    ISession querySession(String sessionId);
    List<ISession> queryAllSessions();
    boolean deleteSession(String sessionId);

    void deleteDB();
    void release();
}
