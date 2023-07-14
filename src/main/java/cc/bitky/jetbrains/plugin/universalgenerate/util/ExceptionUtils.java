package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.BitkylinException;
import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * @author bitkylin
 */
public final class ExceptionUtils {

    private ExceptionUtils() {
    }

    /**
     * checks都为true，不抛异常
     */
    public static void checks(ExceptionMsgEnum exceptionMsgEnum, Boolean... checks) {
        if (ArrayUtils.isEmpty(checks)) {
            throw new BitkylinException(exceptionMsgEnum);
        }
        Arrays.stream(checks).forEach(check -> {
            if (BooleanUtils.isNotTrue(check)) {
                throw new BitkylinException(exceptionMsgEnum);
            }
        });
    }
}
