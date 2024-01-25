package cc.bitky.jetbrains.plugin.universalgenerate.config.settings.configurable;

/**
 * @author bitkylin
 */

import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.GlobalSettingLocalizationConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.LocalizationConfigFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.component.GlobalSettingsComponent;
import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import com.google.common.collect.Sets;
import com.intellij.openapi.options.Configurable;
import org.apache.commons.collections.SetUtils;

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
        GlobalSettingsStateHelper settings = GlobalSettingsStateHelper.getInstance();
        if (globalSettingsComponent.getLanguage() != settings.getLanguage()) {
            return true;
        }
        if (!SetUtils.isEqualSet(Sets.newHashSet(globalSettingsComponent.getAnnotationAffectedList()), Sets.newHashSet(settings.getAnnotationAffectedList()))) {
            return true;
        }
        if (globalSettingsComponent.rightClickMenuEnabled() != settings.isRightClickMenuEnabled()) {
            return true;
        }
        if (globalSettingsComponent.intentionActionEnabled() != settings.isIntentionActionEnabled()) {
            return true;
        }
        if (globalSettingsComponent.getProtostuffTagAssign() != settings.getProtostuffTagAssign()) {
            return true;
        }
        return false;
    }

    @Override
    public void apply() {
        GlobalSettingsStateHelper settings = GlobalSettingsStateHelper.getInstance();
        settings.setLanguage(globalSettingsComponent.getLanguage());
        settings.setAnnotationAffectedList(globalSettingsComponent.getAnnotationAffectedList());
        settings.setRightClickMenuEnabled(globalSettingsComponent.rightClickMenuEnabled());
        settings.setIntentionActionEnabled(globalSettingsComponent.intentionActionEnabled());
        settings.setProtostuffTagAssign(globalSettingsComponent.getProtostuffTagAssign());
    }

    @Override
    public void reset() {
        GlobalSettingLocalizationConfig localizationConfig = LocalizationConfigFactory.config();
        globalSettingsComponent.setTextBlockLanguage(
                localizationConfig.labelLanguage(),
                localizationConfig.radioButtonLanguageEnglish(),
                localizationConfig.radioButtonLanguageChinese()
        );

        globalSettingsComponent.setTextBlockScopeOfEffect(
                localizationConfig.labelScopeOfEffect(),
                localizationConfig.checkBoxSwaggerEffected(),
                localizationConfig.checkBoxProtostuffEffected()
        );

        globalSettingsComponent.setTextBlockRightClickEnabled(
                localizationConfig.labelRightClickMenu(),
                localizationConfig.checkBoxRightClickEnabled()
        );

        globalSettingsComponent.setTextBlockIntentionActionEnabled(
                localizationConfig.labelIntentionAction(),
                localizationConfig.checkBoxIntentionActionEnabled()
        );

        GlobalSettingsStateHelper settingsState = GlobalSettingsStateHelper.getInstance();
        globalSettingsComponent.setLanguage(settingsState.getLanguage());
        globalSettingsComponent.setAnnotationAffectedList(settingsState.getAnnotationAffectedList());
        globalSettingsComponent.setRightClickMenuEnabled(settingsState.isRightClickMenuEnabled());
        globalSettingsComponent.setIntentionActionEnabled(settingsState.isIntentionActionEnabled());
        globalSettingsComponent.setProtostuffTagAssign(settingsState.getProtostuffTagAssign());
    }

    @Override
    public void disposeUIResources() {
        globalSettingsComponent = null;
    }

}