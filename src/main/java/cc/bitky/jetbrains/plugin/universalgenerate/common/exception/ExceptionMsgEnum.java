package cc.bitky.jetbrains.plugin.universalgenerate.common.exception;

import cc.bitky.jetbrains.plugin.universalgenerate.constants.LocalizationEnum;
import lombok.Getter;

/**
 * @author bitkylin
 */
public enum ExceptionMsgEnum {

    /**
     * 测试描述
     */
    TEST(LocalizationEnum.TEST),

    /**
     * 找不到指定的类
     */
    CLASS_NOT_FOUND(LocalizationEnum.CLASS_NOT_FOUND),

    /**
     * \@Tag注解信息有误
     */
    ANNOTATION_TAG_ERROR(LocalizationEnum.ANNOTATION_TAG_ERROR),

    /**
     * 类的角色不被支持
     */
    CLASS_ROLE_UNSUPPORTED(LocalizationEnum.CLASS_ROLE_UNSUPPORTED),

    /**
     * 当前的指令作用范围不被支持
     */
    COMMAND_SCOPE_UNSUPPORTED(LocalizationEnum.COMMAND_SCOPE_UNSUPPORTED),

    /**
     * 未选择任何元素，指令未执行
     */
    ELEMENT_NOT_SELECT(LocalizationEnum.ELEMENT_NOT_SELECT),

    ;

    @Getter
    private final LocalizationEnum localizationEnum;

    ExceptionMsgEnum(LocalizationEnum localizationEnum) {
        this.localizationEnum = localizationEnum;
    }

}
