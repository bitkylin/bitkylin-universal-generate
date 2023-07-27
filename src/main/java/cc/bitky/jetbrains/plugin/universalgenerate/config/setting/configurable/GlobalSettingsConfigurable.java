package cc.bitky.jetbrains.plugin.universalgenerate.config.setting.configurable;

/**
 * @author bitkylin
 */

import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.LocalizationConfigFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.GlobalSettingLocalizationConfig;
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
        return LocalizationConfigFactory.config().settingDisplayName();
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }

    @Override
    public JComponent createComponent() {
        globalSettingsComponent = new GlobalSettingsComponent();
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
        GlobalSettingLocalizationConfig localizationConfig = LocalizationConfigFactory.config();
        globalSettingsComponent.setLabelLanguage(localizationConfig.labelLanguage());
        globalSettingsComponent.setRadioButtonLanguageEnglish(localizationConfig.radioButtonLanguageEnglish());
        globalSettingsComponent.setRadioButtonLanguageChinese(localizationConfig.radioButtonLanguageChinese());

        GlobalSettingsState settingsState = GlobalSettingsState.getInstance();
        globalSettingsComponent.setLanguage(settingsState.getLanguage());
    }

    @Override
    public void disposeUIResources() {
        globalSettingsComponent = null;
    }

}