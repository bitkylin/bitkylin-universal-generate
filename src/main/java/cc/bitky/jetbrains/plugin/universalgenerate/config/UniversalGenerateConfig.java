package cc.bitky.jetbrains.plugin.universalgenerate.config;

import java.util.List;

/**
 * @author bitkylin
 */
public class UniversalGenerateConfig {


    /**
     * 入口类名后缀列表
     */
    public static final List<String> ENTRANCE_CLASS_NAME_SUFFIX_LIST = List.of(
            "Controller",
            "Request",
            "Req",
            "Response",
            "Resp"
    );

    public static List<String> entranceClassNameSuffixList() {
        return ENTRANCE_CLASS_NAME_SUFFIX_LIST;
    }

}
