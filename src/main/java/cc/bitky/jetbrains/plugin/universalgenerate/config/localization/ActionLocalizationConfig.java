package cc.bitky.jetbrains.plugin.universalgenerate.config.localization;

import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionGroupEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.IntentionFamilyEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.LocalizationEnum;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

import static cc.bitky.jetbrains.plugin.universalgenerate.constants.LocalizationEnum.*;

/**
 * @author bitkylin
 */
public class ActionLocalizationConfig {

    protected static final Map<ActionEnum, LocalizationEnum> ACTION_MAP = Maps.newHashMap();
    protected static final Map<ActionEnum, Pair<LocalizationEnum, LocalizationEnum>> INTENTION_ACTION_MAP = Maps.newHashMap();
    protected static final Map<ActionGroupEnum, LocalizationEnum> ACTION_GROUP_MAP = Maps.newHashMap();
    protected static final Map<IntentionFamilyEnum, LocalizationEnum> INTENTION_FAMILY_MAP = Maps.newHashMap();

    static {
        ACTION_MAP.put(ActionEnum.RE_GENERATE_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT, RE_GENERATE_TO_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.RE_GENERATE_SWAGGER_TO_JAVA_DOC_FOR_FILE, RE_GENERATE_TO_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.POPULATE_MISSING_ANNOTATION_FOR_ELEMENT, POPULATE_MISSING_ANNOTATION);
        ACTION_MAP.put(ActionEnum.POPULATE_MISSING_ANNOTATION_FOR_FILE, POPULATE_MISSING_ANNOTATION);
        ACTION_MAP.put(ActionEnum.POPULATE_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT, POPULATE_MISSING_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.POPULATE_SWAGGER_TO_JAVA_DOC_FOR_FILE, POPULATE_MISSING_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.RE_GENERATE_ANNOTATION_FOR_ELEMENT, RE_GENERATE_ANNOTATION);
        ACTION_MAP.put(ActionEnum.RE_GENERATE_ANNOTATION_FOR_FILE, RE_GENERATE_ANNOTATION);
        ACTION_MAP.put(ActionEnum.RE_GENERATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_ELEMENT, RE_GENERATE_ELEMENT_NAME_TO_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.RE_GENERATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_FILE, RE_GENERATE_ELEMENT_NAME_TO_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.POPULATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_ELEMENT, POPULATE_ELEMENT_NAME_TO_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.POPULATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_FILE, POPULATE_ELEMENT_NAME_TO_JAVA_DOC);

        ACTION_MAP.put(ActionEnum.DELETE_JAVA_DOC_FOR_ELEMENT, DELETE_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.DELETE_JAVA_DOC_FOR_FILE, DELETE_JAVA_DOC);
        ACTION_MAP.put(ActionEnum.DELETE_ANNOTATION_SWAGGER_FOR_ELEMENT, DELETE_ANNOTATION_SWAGGER);
        ACTION_MAP.put(ActionEnum.DELETE_ANNOTATION_SWAGGER_FOR_FILE, DELETE_ANNOTATION_SWAGGER);
        ACTION_MAP.put(ActionEnum.DELETE_ANNOTATION_TAG_FOR_ELEMENT, DELETE_ANNOTATION_TAG);
        ACTION_MAP.put(ActionEnum.DELETE_ANNOTATION_TAG_FOR_FILE, DELETE_ANNOTATION_TAG);
        ACTION_MAP.put(ActionEnum.DELETE_ELEMENT_ALL_FOR_ELEMENT, DELETE_ELEMENT_ALL);
        ACTION_MAP.put(ActionEnum.DELETE_ELEMENT_ALL_FOR_FILE, DELETE_ELEMENT_ALL);

        ACTION_GROUP_MAP.put(ActionGroupEnum.GLOBAL_UNIVERSAL_GENERATE, GLOBAL_UNIVERSAL_GENERATE);
        ACTION_GROUP_MAP.put(ActionGroupEnum.SEARCH_ELEMENT_NAME_TO_JAVA_DOC, SEARCH_ELEMENT_NAME_TO_JAVA_DOC);
        ACTION_GROUP_MAP.put(ActionGroupEnum.SWAGGER_TO_JAVA_DOC, ANNOTATION_TO_JAVA_DOC);
        ACTION_GROUP_MAP.put(ActionGroupEnum.GENERATE_ANNOTATION, GENERATE_ENTRY_ANNOTATION);
        ACTION_GROUP_MAP.put(ActionGroupEnum.DELETE_ELEMENT, DELETE_ELEMENT);

        INTENTION_ACTION_MAP.put(ActionEnum.RE_GENERATE_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT, Pair.of(ANNOTATION_TO_JAVA_DOC, CURRENT_ELEMENT));
        INTENTION_ACTION_MAP.put(ActionEnum.RE_GENERATE_SWAGGER_TO_JAVA_DOC_FOR_FILE, Pair.of(ANNOTATION_TO_JAVA_DOC, FULL_DOCUMENT));
        INTENTION_ACTION_MAP.put(ActionEnum.POPULATE_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT, Pair.of(ANNOTATION_TO_JAVA_DOC, CURRENT_ELEMENT));
        INTENTION_ACTION_MAP.put(ActionEnum.POPULATE_SWAGGER_TO_JAVA_DOC_FOR_FILE, Pair.of(ANNOTATION_TO_JAVA_DOC, FULL_DOCUMENT));
        INTENTION_ACTION_MAP.put(ActionEnum.RE_GENERATE_ANNOTATION_FOR_ELEMENT, Pair.of(GENERATE_ENTRY_ANNOTATION, CURRENT_ELEMENT));
        INTENTION_ACTION_MAP.put(ActionEnum.RE_GENERATE_ANNOTATION_FOR_FILE, Pair.of(GENERATE_ENTRY_ANNOTATION, FULL_DOCUMENT));
        INTENTION_ACTION_MAP.put(ActionEnum.POPULATE_MISSING_ANNOTATION_FOR_ELEMENT, Pair.of(GENERATE_ENTRY_ANNOTATION, CURRENT_ELEMENT));
        INTENTION_ACTION_MAP.put(ActionEnum.POPULATE_MISSING_ANNOTATION_FOR_FILE, Pair.of(GENERATE_ENTRY_ANNOTATION, FULL_DOCUMENT));
        INTENTION_ACTION_MAP.put(ActionEnum.RE_GENERATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_ELEMENT, Pair.of(SEARCH_ELEMENT_NAME_TO_JAVA_DOC, CURRENT_ELEMENT));
        INTENTION_ACTION_MAP.put(ActionEnum.RE_GENERATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_FILE, Pair.of(SEARCH_ELEMENT_NAME_TO_JAVA_DOC, FULL_DOCUMENT));
        INTENTION_ACTION_MAP.put(ActionEnum.POPULATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_ELEMENT, Pair.of(SEARCH_ELEMENT_NAME_TO_JAVA_DOC, CURRENT_ELEMENT));
        INTENTION_ACTION_MAP.put(ActionEnum.POPULATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_FILE, Pair.of(SEARCH_ELEMENT_NAME_TO_JAVA_DOC, FULL_DOCUMENT));

        INTENTION_FAMILY_MAP.put(IntentionFamilyEnum.SEARCH_ELEMENT_NAME_TO_JAVA_DOC, SEARCH_ELEMENT_NAME_TO_JAVA_DOC_INTENTION_FAMILY);
        INTENTION_FAMILY_MAP.put(IntentionFamilyEnum.SWAGGER_TO_JAVA_DOC, ANNOTATION_TO_JAVA_DOC_INTENTION_FAMILY);
        INTENTION_FAMILY_MAP.put(IntentionFamilyEnum.GENERATE_ANNOTATION, GENERATE_ENTRY_ANNOTATION_INTENTION_FAMILY);
    }

    public String fetchActionTitle(ActionEnum actionEnum) {
        LocalizationEnum localizationEnum = ACTION_MAP.get(actionEnum);
        if (localizationEnum == null) {
            return localizedText(LocalizationEnum.EMPTY);
        }
        return localizedText(localizationEnum);
    }

    public String fetchIntentionActionTitle(ActionEnum actionEnum) {
        Pair<LocalizationEnum, LocalizationEnum> localizationPair = INTENTION_ACTION_MAP.get(actionEnum);
        if (localizationPair == null) {
            return localizedText(LocalizationEnum.EMPTY);
        }
        return localizedText(localizationPair.getKey()) + " - " + localizedText(localizationPair.getValue());
    }

    public String fetchActionGroupTitle(ActionGroupEnum actionGroupEnum) {
        LocalizationEnum localizationEnum = ACTION_GROUP_MAP.get(actionGroupEnum);
        if (localizationEnum == null) {
            return localizedText(LocalizationEnum.EMPTY);
        }
        return localizedText(localizationEnum);
    }

    public String fetchIntentionFamilyTitle(IntentionFamilyEnum intentionFamilyEnum) {
        LocalizationEnum localizationEnum = INTENTION_FAMILY_MAP.get(intentionFamilyEnum);
        if (localizationEnum == null) {
            return localizedText(LocalizationEnum.EMPTY);
        }
        return localizedText(localizationEnum);
    }

    public String fetchTextForFile() {
        return localizedText(LocalizationEnum.FULL_DOCUMENT);
    }

    public String fetchTextForElement() {
        return localizedText(CURRENT_ELEMENT);
    }

    public String fetchTextForCurrentClassName() {
        return localizedText(CURRENT_CLASS_NAME);
    }

    public String fetchTextForCurrentField() {
        return localizedText(CURRENT_FIELD);
    }

    public String fetchTextForCurrentMethod() {
        return localizedText(CURRENT_METHOD);
    }

    public String fetchTextForNotSupport() {
        return localizedText(LocalizationEnum.NOT_SUPPORT);
    }

    public String fetchTextForDumbMode() {
        return localizedText(LocalizationEnum.DUMB_MODE);
    }

    private String localizedText(LocalizationEnum localizationEnum) {
        return LocalizationConfigFactory.name(localizationEnum);
    }
}
