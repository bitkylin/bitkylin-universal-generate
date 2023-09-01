package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.BitkylinException;
import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.LocalizationConfigFactory;
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
            String localizationDesc = LocalizationConfigFactory.name(exceptionMsgEnum.getLocalizationEnum());
            throw new BitkylinException(exceptionMsgEnum, localizationDesc);
        }
        Arrays.stream(checks).forEach(check -> {
            if (BooleanUtils.isNotTrue(check)) {
                String localizationDesc = LocalizationConfigFactory.name(exceptionMsgEnum.getLocalizationEnum());
                throw new BitkylinException(exceptionMsgEnum, localizationDesc);
            }
        });
    }

    public static BitkylinException newException(ExceptionMsgEnum exceptionMsgEnum) {
        String localizationDesc = LocalizationConfigFactory.name(exceptionMsgEnum.getLocalizationEnum());
        return new BitkylinException(exceptionMsgEnum, localizationDesc);
    }

    public static BitkylinException newException(ExceptionMsgEnum exceptionMsgEnum, String exceptionParam) {
        String localizationDesc = LocalizationConfigFactory.name(exceptionMsgEnum.getLocalizationEnum());
        localizationDesc = String.format(localizationDesc, exceptionParam);
        return new BitkylinException(exceptionMsgEnum, localizationDesc);
    }

}
