package me.pengtao.nochat.util;

import android.database.Cursor;

/**
 * Tools for system
 * Created by 90Chris on 2014/11/12.
 */
public class Tools {
    public static String generateMessageId() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String getDbString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getDbInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getDbLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
