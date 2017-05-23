package me.pengtao.nochat.constant;

/**
 * status of message
 * Created by 90Chris on 2016/4/19.
 */
public enum  MsgStatus {
    ERROR(-1),    // message caught an error
    SENDING(0),   // message is sending
    SUCCESS(1),   // message sent successfully, but not be read
    FAILED(2),    // message sent failed
    READ(3),      // message has been read by receiver
    RECEIVED(4);  // message is received successfully

    private int value;
    MsgStatus(int value) {
        this.value = value;
    }

    public static MsgStatus OfValue(int value) {
        for (MsgStatus status : values()) {
            if ( value == status.getValue() ) {
                return status;
            }
        }
        return ERROR;
    }

    public final int getValue() {
        return this.value;
    }
}
