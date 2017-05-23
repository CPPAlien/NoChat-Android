package me.pengtao.nochat.msg;

import android.text.Html;
import android.widget.TextView;

import me.pengtao.nochat.R;
import me.pengtao.nochat.viewholder.base.MsgBubbleView;

/**
 * text msg view
 * Created by 90Chris on 2016/4/20.
 */
public class TextMsgView extends MsgBubbleView {
    private TextView tvContent;

    @Override
    protected int getContentResId() {
        return R.layout.msgkit_text;
    }

    @Override
    protected void inflateContentView() {
        tvContent = findView(R.id.msgkit_text_content);
    }

    @Override
    protected void bindContentView() {
        TextMsgFormat formatText = (TextMsgFormat)getMsg().getFormat();
        tvContent.setText(Html.fromHtml(formatText.getContent()));
    }


    @Override
    protected boolean onBubbleLongClicked() {
        TextMsgFormat formatText = (TextMsgFormat)getMsg().getFormat();
        afterTextLongClick(formatText.getContent());
        return false;
    }

    /**
     * after plain text being long clicked
     * @param content plain text content
     */
    private void afterTextLongClick(final String content) {
        /*ListDialog listDialog = new ListDialog(getContext());
        listDialog.addItem(getContext().getString(R.string.copy), new ListDialog.ListAction() {
            @Override
            public boolean onClick() {
                android.text.ClipboardManager cmb = (android.text.ClipboardManager) getContext().getSystemService(Activity.CLIPBOARD_SERVICE);
                cmb.setText(content.trim());
                Toast.makeText(getContext(), getContext().getString(R.string.copied), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        listDialog.show();*/
    }
}
