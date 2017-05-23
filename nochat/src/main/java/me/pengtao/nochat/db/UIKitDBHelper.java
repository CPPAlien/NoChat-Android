package me.pengtao.nochat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBHelper
 * Created by 90Chris on 2015/7/5.
 */
public class UIKitDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 24;

    public UIKitDBHelper(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CONTACT_TABLE = "CREATE TABLE " + UIKitDBContract.AccountEntry.TABLE_NAME + "( "
                + UIKitDBContract.AccountEntry._ID + " TEXT PRIMARY KEY, "
                + UIKitDBContract.AccountEntry.COLUMN_GENDER + " INTEGER, "
                + UIKitDBContract.AccountEntry.COLUMN_NICKNAME + " TEXT, "
                + UIKitDBContract.AccountEntry.COLUMN_AVATAR_URL + " TEXT, "
                + UIKitDBContract.AccountEntry.COLUMN_TYPE + " TEXT );";

        db.execSQL(SQL_CREATE_CONTACT_TABLE);

        final String SQL_CREATE_MESSAGE_LIST_TABLE = "CREATE TABLE " + UIKitDBContract.SessionEntry.TABLE_NAME + "("
                + UIKitDBContract.SessionEntry._ID + " TEXT PRIMARY KEY, "
                + UIKitDBContract.SessionEntry.COLUMN_TYPE + " TEXT, "
                + UIKitDBContract.SessionEntry.COLUMN_TIME + " INTEGER, "
                + UIKitDBContract.SessionEntry.COLUMN_LOGO_URL + " TEXT, "
                + UIKitDBContract.SessionEntry.COLUMN_TITLE + " TEXT, "
                + UIKitDBContract.SessionEntry.COLUMN_CONTENT + " TEXT );";
        db.execSQL(SQL_CREATE_MESSAGE_LIST_TABLE);

        final String SQL_CREATE_MESSAGE_DETAIL_TABLE = "CREATE TABLE " + UIKitDBContract.MessageEntry.TABLE_NAME + "("
                + UIKitDBContract.MessageEntry._ID + " TEXT PRIMARY KEY, "
                + UIKitDBContract.MessageEntry.COLUMN_SESSION_ID + " TEXT, "
                + UIKitDBContract.MessageEntry.COLUMN_CONTENT + " TEXT, "
                + UIKitDBContract.MessageEntry.COLUMN_STATUS + " INTEGER, "
                + UIKitDBContract.MessageEntry.COLUMN_FORMAT + " TEXT, "
                + UIKitDBContract.MessageEntry.COLUMN_ACCOUNT_ID + " TEXT, "
                + UIKitDBContract.MessageEntry.COLUMN_TIME + " INTEGER);";

        db.execSQL(SQL_CREATE_MESSAGE_DETAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UIKitDBContract.AccountEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UIKitDBContract.SessionEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UIKitDBContract.MessageEntry.TABLE_NAME);
        onCreate(db);
    }
}
