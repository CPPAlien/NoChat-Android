package me.pengtao.nochat.msg;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import me.pengtao.nochat.format.MsgFormat;

/**
 * TipMsgFormat
 * Created by 90Chris on 2016/4/29.
 */
public class TipMsgFormat extends MsgFormat<TipMsgFormat> {
    private static final String TAG = "NoChat.TipMsgFormat";
    private String content;
    /**
     * Shouldn't be deleted
     * used by reflection class.newInstance();
     */
    @SuppressWarnings("unused")
    public TipMsgFormat() {
    }

    public TipMsgFormat(String ctn) {
        content = ctn;
    }

    public String getContent() {
        return content;
    }

    @Override
    protected TipMsgFormat retThis() {
        return this;
    }

    @Override
    protected TipMsgFormat construct(JSONObject jsonObject) {
        try {
            return new TipMsgFormat(jsonObject.getString("content"));
        } catch (JSONException e) {
            Log.e(TAG, "get json failed", e);
        }
        return null;
    }
}
