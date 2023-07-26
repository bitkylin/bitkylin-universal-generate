package cc.bitky.jetbrains.plugin.universalgenerate.config.localization;

import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.api.IGlobalSettingLocalizationConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.cn.GlobalSettingLocalizationCnConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.en.GlobalSettingLocalizationEnConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.config.setting.state.GlobalSettingsState;

/**
 * @author bitkylin
 */
public class LocalizationConfigFactory {

    private static GlobalSettingsState globalSettingsState() {
        return new GlobalSettingsState();
    }

    public static IGlobalSettingLocalizationConfig getLocalizationConfig() {
        switch (globalSettingsState().getLanguage()) {
            case ENGLISH -> {
                return new GlobalSettingLocalizationEnConfig();
            }
            case CHINESE -> {
                return new GlobalSettingLocalizationCnConfig();
            }
            default -> {
                return new GlobalSettingLocalizationCnConfig();
            }
        }
    }
}
