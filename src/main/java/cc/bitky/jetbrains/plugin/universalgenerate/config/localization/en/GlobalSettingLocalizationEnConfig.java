package cc.bitky.jetbrains.plugin.universalgenerate.config.localization.en;

import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.api.IGlobalSettingLocalizationConfig;

/**
 * @author bitkylin
 */
public class GlobalSettingLocalizationEnConfig implements IGlobalSettingLocalizationConfig {

    @Override
    public String settingDisplayName() {
        return "Bitkylin Universal Generate";
    }

    @Override
    public String labelLanguage() {
        return "language";
    }

    @Override
    public String radioButtonLanguageEnglish() {
        return "English";
    }

    @Override
    public String radioButtonLanguageChinese() {
        return "Chinese";
    }
}
