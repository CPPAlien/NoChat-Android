package me.pengtao.nochat.constant;

/**
 * AccountType
 * Created by 90Chris on 2016/4/21.
 */
public enum AccountType {
    UNDEF("undef"),   //type not defined
    FRIEND("friend"),
    SELF("self"),     //msg sent by yourself
    ROBOT("robot"),
    SystemMessage("SM"),
    AgentMessage("AM"),
    Advertisement("AD");

    private String value;
    AccountType(String type) {
        this.value = type;
    }

    final public String getValue() {
        return this.value;
    }

    public static AccountType OfValue(String value) {
        for (AccountType sender : values()) {
            if ( sender.getValue().equals(value) ) {
                return sender;
            }
        }
        return UNDEF;
    }
}
