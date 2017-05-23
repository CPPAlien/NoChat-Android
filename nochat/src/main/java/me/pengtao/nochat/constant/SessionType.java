package me.pengtao.nochat.constant;

/**
 * AccountType
 * Created by 90Chris on 2016/4/21.
 */
public enum SessionType {
    UNDEF("error"),   //type not defined
    SINGLE("private"),     //single chat
    GROUP("group");     //group chat

    private String value;
    SessionType(String type) {
        this.value = type;
    }

    final public String getValue() {
        return this.value;
    }

    public static SessionType OfValue(String value) {
        for (SessionType sender : values()) {
            if ( sender.getValue().equals(value) ) {
                return sender;
            }
        }
        return UNDEF;
    }
}
