package cc.bitky.jetbrains.plugin.universalgenerate.config;

import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.LocalizationConfigFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionGroupEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.LocalizationEnum;
import com.google.common.collect.Maps;

import java.util.Map;

import static cc.bitky.jetbrains.plugin.universalgenerate.constants.LocalizationEnum.*;

/**
 * @author bitkylin
 */
public class ActionConfig {

    public static final Map<ActionEnum, LocalizationEnum> ACTION_MAP = Maps.newHashMap();
    public static final Map<ActionGroupEnum, LocalizationEnum> ACTION_GROUP_MAP = Maps.newHashMap();

    static {
        ACTION_MAP.put(ActionEnum.RE_GENERATE_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT, RE_GENERATE_TO_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.RE_GENERATE_SWAGGER_TO_JAVA_DOC_FOR_FILE, RE_GENERATE_TO_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.POPULATE_MISSING_ANNOTATION_FOR_ELEMENT, POPULATE_MISSING_ANNOTATION);
        ACTION_MAP.put(ActionEnum.POPULATE_MISSING_ANNOTATION_FOR_FILE, POPULATE_MISSING_ANNOTATION);
        ACTION_MAP.put(ActionEnum.POPULATE_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT, POPULATE_MISSING_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.POPULATE_SWAGGER_TO_JAVA_DOC_FOR_FILE, POPULATE_MISSING_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.RE_GENERATE_ANNOTATION_FOR_ELEMENT, RE_GENERATE_ANNOTATION);
        ACTION_MAP.put(ActionEnum.RE_GENERATE_ANNOTATION_FOR_FILE, RE_GENERATE_ANNOTATION);

        ACTION_GROUP_MAP.put(ActionGroupEnum.SWAGGER_TO_JAVA_DOC, ANNOTATION_TO_JAVA_DOC);
        ACTION_GROUP_MAP.put(ActionGroupEnum.GENERATE_ANNOTATION, GENERATE_ENTRY_ANNOTATION);
    }

    public String fetchActionTitleByActionEnum(ActionEnum actionEnum) {
        LocalizationEnum localizationEnum = ACTION_MAP.get(actionEnum);
        if (localizationEnum == null) {
            return localizedText(LocalizationEnum.EMPTY);
        }
        return localizedText(localizationEnum);
    }

    public String fetchActionGroupTitleByActionGroupEnum(ActionGroupEnum actionGroupEnum) {
        LocalizationEnum localizationEnum = ACTION_GROUP_MAP.get(actionGroupEnum);
        if (localizationEnum == null) {
            return localizedText(LocalizationEnum.EMPTY);
        }
        return localizedText(localizationEnum);
    }

    public String fetchTextForFile() {
        return localizedText(LocalizationEnum.FULL_DOCUMENT);
    }

    public String fetchTextForElement() {
        return localizedText(LocalizationEnum.CURRENT_ELEMENT);
    }

    public String fetchTextForDumbMode() {
        return localizedText(LocalizationEnum.DUMB_MODE);
    }

    private String localizedText(LocalizationEnum localizationEnum) {
        return LocalizationConfigFactory.name(localizationEnum);
    }
}
