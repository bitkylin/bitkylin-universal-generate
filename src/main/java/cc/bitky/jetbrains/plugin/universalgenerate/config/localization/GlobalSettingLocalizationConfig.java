package cc.bitky.jetbrains.plugin.universalgenerate.config.localization;

import cc.bitky.jetbrains.plugin.universalgenerate.constants.LocalizationEnum;

/**
 * @author bitkylin
 */
public class GlobalSettingLocalizationConfig {

    /**
     * 设置页面展示名称
     */
    public String settingDisplayName() {
        return localizedText(LocalizationEnum.SETTING_DISPLAY_NAME);
    }

    public String labelLanguage() {
        return localizedText(LocalizationEnum.LABEL_LANGUAGE);
    }

    public String radioButtonLanguageEnglish() {
        return localizedText(LocalizationEnum.RADIO_BUTTON_LANGUAGE_ENGLISH);
    }

    public String radioButtonLanguageChinese() {
        return localizedText(LocalizationEnum.RADIO_BUTTON_LANGUAGE_CHINESE);
    }

    public String labelScopeOfEffect() {
        return localizedText(LocalizationEnum.LABEL_SCOPE_OF_EFFECT);
    }

    public String checkBoxSwaggerEffected() {
        return localizedText(LocalizationEnum.CHECK_BOX_SWAGGER_EFFECTED);
    }

    public String checkBoxProtostuffEffected() {
        return localizedText(LocalizationEnum.CHECK_BOX_PROTOSTUFF_EFFECTED);
    }

    public String labelRightClickMenu() {
        return localizedText(LocalizationEnum.LABEL_RIGHT_CLICK_MENU);
    }

    public String checkBoxRightClickEnabled() {
        return localizedText(LocalizationEnum.RIGHT_CLICK_ENABLED);
    }

    public String labelIntentionAction() {
        return localizedText(LocalizationEnum.LABEL_INTENTION_ACTION);
    }

    public String checkBoxIntentionActionEnabled() {
        return localizedText(LocalizationEnum.INTENTION_ACTION_ENABLED);
    }

    public String labelProtostuffTagAssign() {
        return localizedText(LocalizationEnum.LABEL_PROTOSTUFF_TAG_ASSIGN);
    }

    public String labelProtostuffTagAssignToolTip() {
        return localizedText(LocalizationEnum.LABEL_PROTOSTUFF_TAG_ASSIGN_TOOL_TIP);
    }

    public String radioButtonProtostuffTagAssignNonRepeatable() {
        return localizedText(LocalizationEnum.RADIO_BUTTON_PROTOSTUFF_TAG_ASSIGN_NON_REPEATABLE);
    }

    public String radioButtonProtostuffTagAssignNonRepeatableToolTip() {
        return localizedText(LocalizationEnum.RADIO_BUTTON_PROTOSTUFF_TAG_ASSIGN_NON_REPEATABLE_TOOL_TIP);
    }

    public String radioButtonProtostuffTagAssignFromStartValue() {
        return localizedText(LocalizationEnum.RADIO_BUTTON_PROTOSTUFF_TAG_ASSIGN_FROM_START_VALUE);
    }

    public String radioButtonProtostuffTagAssignFromStartValueToolTip() {
        return localizedText(LocalizationEnum.RADIO_BUTTON_PROTOSTUFF_TAG_ASSIGN_FROM_START_VALUE_TOOL_TIP);
    }

    public String labelProtostuffTagStartValue() {
        return localizedText(LocalizationEnum.LABEL_PROTOSTUFF_TAG_START_VALUE);
    }

    public String labelProtostuffTagStartValueToolTip() {
        return localizedText(LocalizationEnum.LABEL_PROTOSTUFF_TAG_START_VALUE_TOOL_TIP);
    }

    public String labelProtostuffTagScopeInterval() {
        return localizedText(LocalizationEnum.LABEL_PROTOSTUFF_TAG_SCOPE_INTERVAL);
    }

    public String labelProtostuffTagScopeIntervalToolTip() {
        return localizedText(LocalizationEnum.LABEL_PROTOSTUFF_TAG_SCOPE_INTERVAL_TOOL_TIP);
    }

    public String labelSwaggerTagStayTuned() {
        return localizedText(LocalizationEnum.LABEL_SWAGGER_TAG_STAY_TUNED);
    }

    private String localizedText(LocalizationEnum localizationEnum) {
        return LocalizationConfigFactory.name(localizationEnum);
    }
}
