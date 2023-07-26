package cc.bitky.jetbrains.plugin.universalgenerate.config.localization.cn;

import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.api.IGlobalSettingLocalizationConfig;

/**
 * @author bitkylin
 */
public class GlobalSettingLocalizationCnConfig implements IGlobalSettingLocalizationConfig {

    @Override
    public String settingDisplayName() {
        return "Bitkylin 通用生成";
    }

    @Override
    public String labelLanguage() {
        return "语言";
    }

    @Override
    public String radioButtonLanguageEnglish() {
        return "英文";
    }

    @Override
    public String radioButtonLanguageChinese() {
        return "中文";
    }
}
