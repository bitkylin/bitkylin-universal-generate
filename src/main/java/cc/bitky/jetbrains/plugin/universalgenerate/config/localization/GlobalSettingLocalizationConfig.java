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


    private String localizedText(LocalizationEnum localizationEnum) {
        return LocalizationConfigFactory.name(localizationEnum);
    }
}
