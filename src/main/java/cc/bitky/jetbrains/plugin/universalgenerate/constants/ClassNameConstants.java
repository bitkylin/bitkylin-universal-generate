package cc.bitky.jetbrains.plugin.universalgenerate.constants;

import java.util.List;

/**
 * @author bitkylin
 */
public final class ClassNameConstants {

    private ClassNameConstants() {
    }

    /**
     * 入口类名后缀列表
     */
    public static final List<String> ENTRANCE_CLASS_NAME_SUFFIX_LIST = List.of(
            "Controller",
            "Request",
            "Response"
    );

}
