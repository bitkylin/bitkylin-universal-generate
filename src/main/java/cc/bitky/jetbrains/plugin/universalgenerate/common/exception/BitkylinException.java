package cc.bitky.jetbrains.plugin.universalgenerate.common.exception;

import lombok.Getter;

/**
 * @author bitkylin
 */
public class BitkylinException extends RuntimeException {

    /**
     * 异常编码
     */
    @Getter
    private final String code;

    public BitkylinException(ExceptionMsgEnum exceptionMsgEnum, String localizationDesc) {
        super(localizationDesc);
        this.code = exceptionMsgEnum.name();
    }

}

