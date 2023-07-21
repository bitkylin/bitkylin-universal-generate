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
    CLASS_NOT_FOUND(10002, "找不到指定的类"),
    ANNOTATION_TAG_ERROR(10003, "@Tag注解信息有误"),
    CLASS_ROLE_UNSUPPORTED(10004, "类的角色不被支持"),
    COMMAND_SCOPE_UNSUPPORTED(10005, "当前的指令作用范围不被支持"),
    ELEMENT_NOT_SELECT(10006, "未选择任何元素，指令未执行"),
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
