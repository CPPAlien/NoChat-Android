package me.pengtao.nochat_example.panel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import cn.hadcn.keyboard.ChatKeyboardLayout;
import cn.hadcn.keyboard.media.MediaBean;
import me.pengtao.nochat.NoChatAssembler;
import me.pengtao.nochat.base.IMessage;
import me.pengtao.nochat.panel.IMsgAdapterDelegate;
import me.pengtao.nochat.panel.MsgAdapter;
import me.pengtao.nochat.viewholder.MsgViewFactory;
import me.pengtao.nochat.viewholder.base.MsgViewBase;
import me.pengtao.nochat_example.R;

/**
 * chat place
 * Created by 90Chris on 2016/4/21.
 */
public class ChatFragment extends Fragment implements IMsgAdapterDelegate, IChatView, MediaBean.MediaListener {
    private View mView;
    private MsgAdapter mAdapter;
    private final List<IMessage> messages = new LinkedList<>();
    private ChatPresenter mPresenter;
    private ChatKeyboardLayout cklKeyboard = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ChatPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_chat, container, false);
        cklKeyboard = (ChatKeyboardLayout)mView.findViewById(R.id.chat_area);
        mPresenter.init();
        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return MsgViewFactory.getViewCount();
    }

    @Override
    public Class<? extends MsgViewBase> viewAtPosition(int pos) {
        return MsgViewFactory.getView(messages.get(pos));
    }

    @Override
    public void initMessageList() {
        mAdapter = new MsgAdapter(getContext(), messages, this);
        ListView listView = (ListView)mView.findViewById(R.id.cim_chat_message_list);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void initKeyboard() {
        List<MediaBean> list = new ArrayList<>();
        list.add(new MediaBean(R.drawable.photo_shot, R.drawable.photo_shot, getString(R.string.take_photo), this));
        list.add(new MediaBean(R.drawable.pic_select, R.drawable.pic_select, getString(R.string.pic_select), this));
        list.add(new MediaBean(R.drawable.image_text, R.drawable.image_text, getString(R.string.image_text), this));
        list.add(new MediaBean(R.drawable.file_select, R.drawable.file_select, getString(R.string.file_select), this));
        list.add(new MediaBean(R.drawable.call_agent, R.drawable.call_agent, getString(R.string.call_agent), this));
        cklKeyboard.showMedias(list);
        cklKeyboard.showEmoticons();
        cklKeyboard.setOnKeyBoardBarListener(new KeyBoardBarListener());
    }

    @Override
    public String getInputAreaContent() {
        return cklKeyboard.getInputArea().getText().toString();
    }

    @Override
    public void clearTypedMessage() {
        cklKeyboard.clearInputArea();
    }

    String currentDate;
    @Override
    public void displayMsg(IMessage msg) {
        String date = new SimpleDateFormat(getContext().getResources().getString(R.string.date_format), Locale.TAIWAN).format(Calendar.getInstance().getTime());
        if ( !date.equals(currentDate) ) {
            currentDate = date;
            IMessage timeMessage = NoChatAssembler.assembleTimeMsg("1", currentDate);
            messages.add(timeMessage);
        }

        messages.add(msg);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMediaClick(int i) {

    }

    private class KeyBoardBarListener implements ChatKeyboardLayout.OnChatKeyBoardListener {

        @Override
        public void onSendBtnClick(String s) {
            mPresenter.sendButtonClicked();
        }

        @Override
        public void onRecordingAction(ChatKeyboardLayout.RecordingAction recordingAction) {
        }

        @Override
        public void onUserDefEmoticonClicked(String tag, String uri) {

        }
    }
}
