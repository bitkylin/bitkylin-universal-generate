package cc.bitky.jetbrains.plugin.universalgenerate.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author bitkylin
 */
public final class ClassNameUtils {

    private ClassNameUtils() {
    }

    public static boolean isControllerSuffix(String className) {
        return StringUtils.endsWith(className, "Controller");
    }
}
