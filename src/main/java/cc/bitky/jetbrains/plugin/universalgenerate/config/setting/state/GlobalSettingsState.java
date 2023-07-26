package cc.bitky.jetbrains.plugin.universalgenerate.config.setting.state;

/**
 * @author bitkylin
 */

import com.google.common.base.Enums;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;

@State(
        name = "cc.bitky.jetbrains.plugin.universalgenerate.config.setting.state.globalSettingsState",
        storages = @Storage("bitkylinUniversalGenerateSettings.xml")
)
public class GlobalSettingsState implements PersistentStateComponent<GlobalSettingsState> {

    private String language = "ENGLISH";

    public static GlobalSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(GlobalSettingsState.class);
    }

    @Override
    public GlobalSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(GlobalSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language.name();
    }

    public LanguageEnum getLanguage() {
        return Enums.getIfPresent(LanguageEnum.class, language).or(LanguageEnum.ENGLISH);
    }

    public enum LanguageEnum {
        ENGLISH,
        CHINESE,
        ;
    }

}