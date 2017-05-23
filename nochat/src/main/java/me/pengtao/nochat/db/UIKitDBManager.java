package me.pengtao.nochat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.pengtao.nochat.base.AccountEntity;
import me.pengtao.nochat.base.IAccount;
import me.pengtao.nochat.base.IMessage;
import me.pengtao.nochat.base.ISession;
import me.pengtao.nochat.base.MessageEntity;
import me.pengtao.nochat.base.SessionEntity;
import me.pengtao.nochat.constant.AccountType;
import me.pengtao.nochat.constant.Gender;
import me.pengtao.nochat.constant.MsgStatus;
import me.pengtao.nochat.constant.SessionType;
import me.pengtao.nochat.format.MsgFormatBase;
import me.pengtao.nochat.util.Tools;

/**
 * DBManager
 * Created by 90Chris on 2015/7/5.
 */
public class UIKitDBManager implements UIKitDBLocalRule {
    private static final String TAG = "NoChat.UIKitDBManager";

    private UIKitDBHelper mOpenHelper;
    private static UIKitDBManager sDbManager;
    private Context mContext;
    private String mDBName;

    public static void init(Context context, String name) {
        if ( sDbManager == null ) {
            sDbManager = new UIKitDBManager(context.getApplicationContext(), name);
        }
    }

    public static UIKitDBManager getInstance() {
        if ( sDbManager == null ) {
            Log.e(TAG, "DbManager was not be initialized");
        }
        return sDbManager;
    }

    private UIKitDBManager(Context context, String name) {
        mDBName = name;
        mContext = context;
        mOpenHelper = new UIKitDBHelper(mContext, name);
    }

    @Override
    public synchronized boolean insertAccount(IAccount account) {
        String userId = account.getId();
        Gender gender = account.getGender();
        String avatarUrl = account.getAvatarUrl();
        String name = account.getNickname();
        AccountType type = account.getType();

        ContentValues values = new ContentValues();
        if ( avatarUrl != null) values.put(UIKitDBContract.AccountEntry.COLUMN_AVATAR_URL, avatarUrl);
        if ( name != null) values.put(UIKitDBContract.AccountEntry.COLUMN_NICKNAME, name);
        if ( type != null) values.put(UIKitDBContract.AccountEntry.COLUMN_TYPE, type.getValue());
        if ( gender != null )  values.put(UIKitDBContract.AccountEntry.COLUMN_GENDER, gender.getValue());

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final String selection = UIKitDBContract.AccountEntry._ID + " = ?";
        final String[] selectionArgs = new String[] {userId};

        long _id;
        Cursor retCursor = db.query(UIKitDBContract.AccountEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if ( retCursor.getCount() <= 0 ) {
            //if not exist, insert
            values.put(UIKitDBContract.AccountEntry._ID, userId);
            _id = db.insert(UIKitDBContract.AccountEntry.TABLE_NAME, null, values);
        } else {
            _id = db.update(UIKitDBContract.AccountEntry.TABLE_NAME, values, selection, selectionArgs);
        }
        retCursor.close();

        return _id > 0;
    }

    @Override
    public IAccount queryAccount(String id) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final String selection = UIKitDBContract.AccountEntry._ID + " = ?";
        final String[] selectionArgs = new String[] {id};
        Cursor retCursor = db.query(UIKitDBContract.AccountEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if ( retCursor.getCount() <= 0 ) {
            retCursor.close();
            return null;
        }
        retCursor.moveToFirst();
        AccountEntity.Builder builder = new AccountEntity.Builder();
        builder.avatarUrl(Tools.getDbString(retCursor, UIKitDBContract.AccountEntry.COLUMN_AVATAR_URL));
        builder.id(Tools.getDbString(retCursor, UIKitDBContract.AccountEntry._ID));
        builder.nickName(Tools.getDbString(retCursor, UIKitDBContract.AccountEntry.COLUMN_NICKNAME));
        builder.type(AccountType.OfValue(Tools.getDbString(retCursor, UIKitDBContract.AccountEntry.COLUMN_TYPE)));
        builder.gender(Gender.ofValue(Tools.getDbInt(retCursor, UIKitDBContract.AccountEntry.COLUMN_GENDER)));
        retCursor.close();
        return builder.build();
    }

    @Override
    public List<IAccount> queryAllAccounts() {
        List<IAccount> accounts = new ArrayList<>();
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(UIKitDBContract.AccountEntry.TABLE_NAME, null, null, null, null, null, null);
        if ( cursor.getCount() <= 0 ) {
            cursor.close();
            return accounts;
        }
        while (cursor.moveToNext()) {
            AccountEntity.Builder builder = new AccountEntity.Builder();
            builder.id(Tools.getDbString(cursor, UIKitDBContract.AccountEntry._ID))
                    .avatarUrl(Tools.getDbString(cursor, UIKitDBContract.AccountEntry.COLUMN_AVATAR_URL))
                    .nickName(Tools.getDbString(cursor, UIKitDBContract.AccountEntry.COLUMN_NICKNAME))
                    .gender(Gender.ofValue(Tools.getDbInt(cursor, UIKitDBContract.AccountEntry.COLUMN_GENDER)))
                    .type(AccountType.OfValue(Tools.getDbString(cursor, UIKitDBContract.AccountEntry.COLUMN_TYPE)));
            accounts.add(builder.build());
        }
        cursor.close();
        return accounts;
    }

    @Override
    public boolean deleteAccount(String id) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final String whereClause = UIKitDBContract.AccountEntry._ID + " = ?";
        final String[] whereArgs = new String[] {id};
        return db.delete(UIKitDBContract.AccountEntry.TABLE_NAME, whereClause, whereArgs) > 0;
    }

    @Override
    public synchronized boolean insertMessage(String sessionId, IMessage message) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UIKitDBContract.MessageEntry._ID, message.getId());
        values.put(UIKitDBContract.MessageEntry.COLUMN_SESSION_ID, sessionId);
        values.put(UIKitDBContract.MessageEntry.COLUMN_FORMAT, message.getFormat().getClass().getName());
        values.put(UIKitDBContract.MessageEntry.COLUMN_CONTENT, message.getFormat().toJsonString());
        MsgStatus status = message.getStatus();
        if ( status != null ) {
            values.put(UIKitDBContract.MessageEntry.COLUMN_STATUS, status.getValue());
        }
        IAccount sender = message.getSender();
        if ( sender != null ) {
            values.put(UIKitDBContract.MessageEntry.COLUMN_ACCOUNT_ID, sender.getId());
        }
        values.put(UIKitDBContract.MessageEntry.COLUMN_TIME, message.getTime());

        long _id = db.insert(UIKitDBContract.MessageEntry.TABLE_NAME, null, values);
        return _id > 0;
    }

    @Override
    public boolean deleteMessage(String messageId) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final String messageSelection = UIKitDBContract.MessageEntry._ID + " = ?";
        final String[] selectionArgs = new String[] {messageId};

        return db.delete(UIKitDBContract.MessageEntry.TABLE_NAME, messageSelection, selectionArgs) > 0;
    }

    @Override
    public IMessage queryMessage(String messageId) {
        return null;
    }

    @Override
    public List<IMessage> queryMessages(String sessionId) {
        List<IMessage> msgs = new LinkedList<>();
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final String selection = UIKitDBContract.MessageEntry.COLUMN_SESSION_ID + " = ?";
        final String[] selectionArgs = new String[]{ sessionId };
        final String order = UIKitDBContract.MessageEntry.COLUMN_TIME + " ASC";

        Cursor cursor = db.query(UIKitDBContract.MessageEntry.TABLE_NAME, null, selection, selectionArgs, null, null, order);
        if ( cursor.getCount() <= 0 ) {
            cursor.close();
            return msgs;
        }
        while (cursor.moveToNext()) {
            MessageEntity.Builder builder = new MessageEntity.Builder();
            builder.id(Tools.getDbString(cursor, UIKitDBContract.MessageEntry._ID));
            builder.time(Tools.getDbLong(cursor, UIKitDBContract.MessageEntry.COLUMN_TIME));
            builder.status(MsgStatus.OfValue(Tools.getDbInt(cursor, UIKitDBContract.MessageEntry.COLUMN_STATUS)));
            String account_id = Tools.getDbString(cursor, UIKitDBContract.MessageEntry.COLUMN_ACCOUNT_ID);
            if ( account_id != null ) {
                builder.sender(queryAccount(account_id));
            }
            String className = Tools.getDbString(cursor, UIKitDBContract.MessageEntry.COLUMN_FORMAT);
            String content = Tools.getDbString(cursor, UIKitDBContract.MessageEntry.COLUMN_CONTENT);
            try {
                Class formatClass = Class.forName(className);
                MsgFormatBase baseFormat = (MsgFormatBase)formatClass.newInstance();
                builder.format((MsgFormatBase)baseFormat.restore(content));
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "class not found, className = " + className, e);
            } catch (InstantiationException e) {
                Log.e(TAG, "The class construction failed, make sure there exist a default constructor, className = " + className, e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "The class default constructor is not public, className = " + className, e);
            }

            msgs.add(builder.build());
        }
        cursor.close();
        return msgs;
    }

    @Override
    public boolean clearMessagePanel(String sessionId) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final String messageSelection = UIKitDBContract.MessageEntry.COLUMN_SESSION_ID + " = ?";
        final String[] selectionArgs = new String[] {sessionId};
        db.beginTransaction();
        int b = db.delete(UIKitDBContract.MessageEntry.TABLE_NAME, messageSelection, selectionArgs);
        if ( b > 0 ) {
            db.setTransactionSuccessful();
        }
        db.endTransaction();

        return b > 0;
    }

    @Override
    public boolean insertSession(ISession session) {
        String sessionId = session.getId();
        String title = session.getTitle();
        String content = session.getContent();
        String logo = session.getLogo();
        SessionType type = session.getType();

        ContentValues values = new ContentValues();
        if ( logo != null ) values.put(UIKitDBContract.SessionEntry.COLUMN_LOGO_URL, session.getLogo());
        if ( title != null ) values.put(UIKitDBContract.SessionEntry.COLUMN_TITLE, session.getTitle());
        if ( content != null ) values.put(UIKitDBContract.SessionEntry.COLUMN_CONTENT, session.getContent());
        if ( type != null ) values.put(UIKitDBContract.SessionEntry.COLUMN_TYPE, session.getType().getValue());

        values.put(UIKitDBContract.SessionEntry.COLUMN_TIME, System.currentTimeMillis());

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final String selection = UIKitDBContract.SessionEntry._ID + " = ?";
        final String[] selectionArgs = new String[] {sessionId};

        long _id;
        Cursor retCursor = db.query(UIKitDBContract.SessionEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if ( retCursor.getCount() <= 0 ) {
            values.put(UIKitDBContract.SessionEntry._ID, session.getId());
            _id = db.insert(UIKitDBContract.SessionEntry.TABLE_NAME, null, values);
        } else {
            _id = db.update(UIKitDBContract.SessionEntry.TABLE_NAME, values, selection, selectionArgs);
        }
        retCursor.close();

        return _id > 0;
    }

    @Override
    public ISession querySession(String sessionId) {
        return null;
    }

    @Override
    public List<ISession> queryAllSessions() {
        List<ISession> sessions = new LinkedList<>();
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final String order = UIKitDBContract.SessionEntry.COLUMN_TIME + " DESC";
        Cursor cursor = db.query(UIKitDBContract.SessionEntry.TABLE_NAME, null, null, null, null, null, order);
        if ( cursor.getCount() <= 0 ) {
            cursor.close();
            return sessions;
        }
        while (cursor.moveToNext()) {
            SessionEntity.Builder builder = new SessionEntity.Builder();
            builder.id(Tools.getDbString(cursor, UIKitDBContract.SessionEntry._ID))
                    .logo(Tools.getDbString(cursor, UIKitDBContract.SessionEntry.COLUMN_LOGO_URL))
                    .content(Tools.getDbString(cursor, UIKitDBContract.SessionEntry.COLUMN_CONTENT))
                    .title(Tools.getDbString(cursor, UIKitDBContract.SessionEntry.COLUMN_TITLE))
                    .time(Tools.getDbLong(cursor, UIKitDBContract.SessionEntry.COLUMN_TIME))
                    .type(SessionType.OfValue(Tools.getDbString(cursor, UIKitDBContract.SessionEntry.COLUMN_TYPE)));
            sessions.add(builder.build());
        }
        cursor.close();
        return sessions;
    }

    @Override
    public boolean deleteSession(String sessionId) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final String sessionSelection = UIKitDBContract.SessionEntry._ID + " = ?";
        final String messageSelection = UIKitDBContract.MessageEntry.COLUMN_SESSION_ID + " = ?";
        final String[] selectionArgs = new String[] {sessionId};
        db.beginTransaction();
        int a = db.delete(UIKitDBContract.SessionEntry.TABLE_NAME, sessionSelection, selectionArgs);
        int b = db.delete(UIKitDBContract.MessageEntry.TABLE_NAME, messageSelection, selectionArgs);
        if ( a > 0 && b > 0 ) {
            db.setTransactionSuccessful();
        }
        db.endTransaction();

        return a > 0 && b > 0;
    }

    @Override
    public void deleteDB() {
        mContext.deleteDatabase(mDBName);
        sDbManager = null;
    }

    @Override
    public void release() {
        sDbManager = null;
    }
}
