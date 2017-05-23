package me.pengtao.nochat.viewholder;

import java.util.HashMap;
import java.util.Map;

import me.pengtao.nochat.base.IMessage;
import me.pengtao.nochat.format.MsgFormatBase;
import me.pengtao.nochat.msg.TextMsgFormat;
import me.pengtao.nochat.msg.TextMsgView;
import me.pengtao.nochat.msg.TipMsgFormat;
import me.pengtao.nochat.msg.TipMsgView;
import me.pengtao.nochat.viewholder.base.MsgViewBase;
import me.pengtao.nochat.viewholder.base.UndefView;

/**
 * MsgViewFactory
 * Created by 90Chris on 2016/4/20.
 */
public class MsgViewFactory {
    private static Map<Class<? extends MsgFormatBase>, Class<? extends MsgViewBase>>
            viewHolders = new HashMap<>();

    static {
        register(TextMsgFormat.class, TextMsgView.class);
        register(TipMsgFormat.class, TipMsgView.class);
    }

    public static void register(Class<? extends MsgFormatBase> format, Class<? extends
            MsgViewBase> view) {
        viewHolders.put(format, view);
    }

    public static Class<? extends MsgViewBase> getView(IMessage message) {
        Class<? extends MsgFormatBase> clazz = message.getFormat().getClass();
        Class<? extends MsgViewBase> view = viewHolders.get(clazz);
        return view == null ? UndefView.class : view;
    }

    public static int getViewCount() {
        return viewHolders.size();
    }
}
