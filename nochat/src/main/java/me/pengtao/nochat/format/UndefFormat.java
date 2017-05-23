package me.pengtao.nochat.format;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * UndefFormat
 * Created by 90Chris on 2016/4/20.
 */
public class UndefFormat extends MsgFormat<UndefFormat> {
    private static final String TAG = "NoChat.UndefFormat";
    private String content;
    /**
     * Shouldn't be deleted
     * used by reflection class.newInstance();
     */
    @SuppressWarnings("unused")
    public UndefFormat() {
    }

    public UndefFormat(String ctn) {
        content = ctn;
    }

    public String getContent() {
        return content;
    }

    @Override
    protected UndefFormat retThis() {
        return this;
    }

    @Override
    protected UndefFormat construct(JSONObject jsonObject) {
        try {
            return new UndefFormat(jsonObject.getString("content"));
        } catch (JSONException e) {
            Log.e(TAG, "get json failed", e);
        }
        return null;
    }
}