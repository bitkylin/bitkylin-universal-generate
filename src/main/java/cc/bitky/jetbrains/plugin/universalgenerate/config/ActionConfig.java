package cc.bitky.jetbrains.plugin.universalgenerate.config;

import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionGroupEnum;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author bitkylin
 */
public class ActionConfig {

    public static final Map<ActionEnum, String> ACTION_MAP = Maps.newHashMap();
    public static final Map<ActionGroupEnum, String> ACTION_GROUP_MAP = Maps.newHashMap();

    static {
        ACTION_MAP.put(ActionEnum.MERGE_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT, "合并到JavaDoc");
        ACTION_MAP.put(ActionEnum.MERGE_SWAGGER_TO_JAVA_DOC_FOR_FILE, "合并到JavaDoc");
        ACTION_MAP.put(ActionEnum.PADDING_GENERATE_FOR_ELEMENT, "填充不存在的注解");
        ACTION_MAP.put(ActionEnum.PADDING_GENERATE_FOR_FILE, "填充不存在的注解");
        ACTION_MAP.put(ActionEnum.PADDING_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT, "填充不存在的JavaDoc");
        ACTION_MAP.put(ActionEnum.PADDING_SWAGGER_TO_JAVA_DOC_FOR_FILE, "填充不存在的JavaDoc");
        ACTION_MAP.put(ActionEnum.RENEW_GENERATE_FOR_ELEMENT, "重新生成注解");
        ACTION_MAP.put(ActionEnum.RENEW_GENERATE_FOR_FILE, "重新生成注解");

        ACTION_GROUP_MAP.put(ActionGroupEnum.SWAGGER_TO_JAVA_DOC, "注解转JavaDoc");
        ACTION_GROUP_MAP.put(ActionGroupEnum.GENERATE_ANNOTATION, "生成入口注解");
    }

    public String fetchActionTitleByActionEnum(ActionEnum actionEnum) {
        String title = ACTION_MAP.get(actionEnum);
        if (title == null) {
            return "空";
        }
        return title;
    }

    public String fetchActionGroupTitleByActionGroupEnum(ActionGroupEnum actionGroupEnum) {
        String title = ACTION_GROUP_MAP.get(actionGroupEnum);
        if (title == null) {
            return "空";
        }
        return title;
    }

    public String fetchTextForFile() {
        return "整个文件";
    }
    public String fetchTextForElement() {
        return "当前元素";
    }
}
