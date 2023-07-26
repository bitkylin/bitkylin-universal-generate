package cc.bitky.jetbrains.plugin.universalgenerate.config.setting.configurable;

/**
 * @author bitkylin
 */

import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.LocalizationConfigFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.api.IGlobalSettingLocalizationConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.config.setting.component.GlobalSettingsComponent;
import cc.bitky.jetbrains.plugin.universalgenerate.config.setting.state.GlobalSettingsState;
import com.intellij.openapi.options.Configurable;

import javax.swing.*;

/**
 * Provides controller functionality for application settings.
 */
public class GlobalSettingsConfigurable implements Configurable {

    private GlobalSettingsComponent globalSettingsComponent;

    @Override
    public String getDisplayName() {
        return LocalizationConfigFactory.getLocalizationConfig().settingDisplayName();
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }

    @Override
    public JComponent createComponent() {
        globalSettingsComponent = new GlobalSettingsComponent();
        IGlobalSettingLocalizationConfig localizationConfig = LocalizationConfigFactory.getLocalizationConfig();
        globalSettingsComponent.getLabelLanguage().setText(localizationConfig.labelLanguage());
        globalSettingsComponent.getRadioButtonLanguageEnglish().setText(localizationConfig.radioButtonLanguageEnglish());
        globalSettingsComponent.getRadioButtonLanguageChinese().setText(localizationConfig.radioButtonLanguageChinese());

        GlobalSettingsState settingsState = GlobalSettingsState.getInstance();
        switch (settingsState.getLanguage()) {
            case ENGLISH -> globalSettingsComponent.getRadioButtonLanguageEnglish().setSelected(true);
            case CHINESE -> globalSettingsComponent.getRadioButtonLanguageChinese().setSelected(true);
            default -> globalSettingsComponent.getRadioButtonLanguageEnglish().setSelected(true);
        }

        return globalSettingsComponent.getMainPanel();
    }

    @Override
    public boolean isModified() {
        GlobalSettingsState settings = GlobalSettingsState.getInstance();
        if (globalSettingsComponent.getLanguage() != settings.getLanguage()) {
            return true;
        }
        return false;
    }

    @Override
    public void apply() {
        GlobalSettingsState settings = GlobalSettingsState.getInstance();
        settings.setLanguage(globalSettingsComponent.getLanguage());
    }

    @Override
    public void reset() {
        globalSettingsComponent.setLanguage(GlobalSettingsState.LanguageEnum.ENGLISH);
    }

    @Override
    public void disposeUIResources() {
        globalSettingsComponent = null;
    }

}