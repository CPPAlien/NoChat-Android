package me.pengtao.nochat.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * DBContract
 * Created by 90Chris on 2015/7/5.
 */
public class UIKitDBContract {

    public static final String CONTENT_AUTHORITY = "tw.com.chainsea.im";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ACCOUNT = "account";
    public static final class AccountEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACCOUNT).build();
        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME = "account";

        public static final String COLUMN_NICKNAME = "nickname";
        public static final String COLUMN_AVATAR_URL = "avatar_url";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_GENDER = "gender";
    }

    public static final String PATH_SESSION = "session";
    public static final class SessionEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SESSION).build();
        public static Uri buildUri (long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME = "session";

        public static final String COLUMN_TYPE = "session_type";
        public static final String COLUMN_LOGO_URL = "logo_url";
        public static final String COLUMN_TITLE = "name";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_TIME = "time";
    }

    public static final String PATH_MESSAGE = "message";
    public static final class MessageEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MESSAGE).build();
        public static Uri buildUri (long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME = "message";
        public static final String COLUMN_SESSION_ID = "session_id";
        public static final String COLUMN_ACCOUNT_ID = "account_id";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_FORMAT = "format";    //save class.getName() of a POJO class
    }
}
