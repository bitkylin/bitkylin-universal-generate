package cc.bitky.jetbrains.plugin.universalgenerate.common.exception;

import lombok.Getter;

/**
 * @author bitkylin
 */
@Getter
public enum ExceptionMsgEnum {

    /**
     * BitkylinExceptionMsgEnum
     */
    TEST(10001, "测试描述"),
    ;

    private final Long code;
    private final String innerMsg;
    private final String showMsg;

    ExceptionMsgEnum(long code, String message) {
        this(code, message, message);
    }

    ExceptionMsgEnum(long code, String innerMsg, String showMsg) {
        this.code = code;
        this.innerMsg = innerMsg;
        this.showMsg = showMsg;
    }

}
