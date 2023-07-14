package cc.bitky.jetbrains.plugin.universalgenerate.common.exception;

import lombok.Getter;

/**
 * @author bitkylin
 */
public class BitkylinException extends RuntimeException {

    /**
     * 业务异常码 ( 详情参加文档说明 )
     */
    @Getter
    private final Long code;

    /**
     * 业务异常信息
     */
    @Getter
    private final String message;

    public BitkylinException(ExceptionMsgEnum exceptionMsgEnum) {
        super(exceptionMsgEnum.getShowMsg());
        this.code = exceptionMsgEnum.getCode();
        this.message = exceptionMsgEnum.getShowMsg();
    }

    public BitkylinException(Long code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BitkylinException(Throwable cause, Long code, String message) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

}

