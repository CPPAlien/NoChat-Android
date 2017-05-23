package me.pengtao.nochat;

import android.widget.ImageView;

import me.pengtao.nochat.base.IAccount;

/**
 * OnUIKitListener
 * Created by 90Chris on 2016/6/3.
 */
public interface OnUIKitListener {
    void onAvatarClick(IAccount account);
    void onAvatarLoad(ImageView iv, IAccount account);
    void onFormatUpdate(String msgId, String formatName, String formatContent);
}
