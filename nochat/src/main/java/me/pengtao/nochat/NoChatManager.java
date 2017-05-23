package me.pengtao.nochat;

import android.content.Context;

import me.pengtao.nochat.db.UIKitDBManager;
import me.pengtao.nochat.format.MsgFormatBase;
import me.pengtao.nochat.viewholder.MsgViewFactory;
import me.pengtao.nochat.viewholder.base.MsgViewBase;

/**
 * CimUIKit
 * Created by 90Chris on 2016/4/21.
 */
public class NoChatManager {

    public static void registerMsg(Class<? extends MsgFormatBase> format, Class<? extends MsgViewBase> view) {
        MsgViewFactory.register(format, view);
    }

    private static boolean isDefaultDBEnabled = false;
    public static void enableDefaultDB(Context context, String dbName) {
        UIKitDBManager.init(context, dbName);
        isDefaultDBEnabled = true;
    }

    public static UIKitDBManager getDb() {
        if ( isDefaultDBEnabled ) {
            return UIKitDBManager.getInstance();
        } else {
            throw new RuntimeException("Default db is not enabled");
        }
    }
}
