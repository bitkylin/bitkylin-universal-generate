package cc.bitky.jetbrains.plugin.universalgenerate.config.localization;

import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.LocalizationEnum;

/**
 * @author bitkylin
 */
public final class LocalizationConfigFactory {

    private LocalizationConfigFactory() {
    }

    private static GlobalSettingsStateHelper globalSettingsState() {
        return GlobalSettingsStateHelper.getInstance();
    }

    public static GlobalSettingLocalizationConfig config() {
        return new GlobalSettingLocalizationConfig();
    }

    public static String name(LocalizationEnum localizationEnum) {
        switch (globalSettingsState().getLanguage()) {
            case ENGLISH -> {
                return localizationEnum.getEnglish();
            }
            case CHINESE -> {
                return localizationEnum.getChinese();
            }
            default -> {
                return localizationEnum.getEnglish();
            }
        }

    }
}
