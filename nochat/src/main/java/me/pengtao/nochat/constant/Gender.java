package me.pengtao.nochat.constant;

/**
 *
 * Created by 90Chris on 2016/6/20.
 */
public enum Gender {
    UNDEF(0),
    MALE(1),
    FEMALE(-1);

    private int mValue;
    Gender(int value) {
        mValue = value;
    }

    public final int getValue() {
        return mValue;
    }

    public static Gender ofValue(int value) {
        for ( Gender item : values() ) {
            if ( item.getValue() == value ) return item;
        }
        return UNDEF;
    }
}
