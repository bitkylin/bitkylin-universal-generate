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


    private String localizedText(LocalizationEnum localizationEnum) {
        return LocalizationConfigFactory.name(localizationEnum);
    }
}
