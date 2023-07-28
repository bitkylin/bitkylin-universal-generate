package cc.bitky.jetbrains.plugin.universalgenerate.config.setting.state;

import cc.bitky.jetbrains.plugin.universalgenerate.util.ListUtils;
import com.google.common.base.Enums;
import com.intellij.openapi.application.ApplicationManager;

import java.util.List;

/**
 * @author bitkylin
 */
public class GlobalSettingsStateHelper {

    private final GlobalSettingsState state;

    private GlobalSettingsStateHelper(GlobalSettingsState state) {
        this.state = state;
    }

    public static GlobalSettingsStateHelper getInstance() {
        return new GlobalSettingsStateHelper(ApplicationManager.getApplication().getService(GlobalSettingsState.class));
    }

    public void setLanguage(GlobalSettingsState.LanguageEnum language) {
        this.state.setLanguage(language.name());
    }

    public GlobalSettingsState.LanguageEnum getLanguage() {
        return Enums.getIfPresent(GlobalSettingsState.LanguageEnum.class, this.state.getLanguage())
                .or(GlobalSettingsState.LanguageEnum.ENGLISH);
    }

    public List<GlobalSettingsState.AnnotationAffectedEnum> getAnnotationAffectedList() {
        return ListUtils.distinctMap(this.state.getAnnotationAffectedList(),
                enumStr -> Enums.getIfPresent(GlobalSettingsState.AnnotationAffectedEnum.class, enumStr).orNull());
    }

    public void setAnnotationAffectedList(List<GlobalSettingsState.AnnotationAffectedEnum> annotationAffectedList) {
        this.state.setAnnotationAffectedList(ListUtils.distinctMap(annotationAffectedList, Enum::name));
    }

    public void setContextMenuShowed(boolean contextMenuShowed) {
        this.state.setContextMenuShowed(contextMenuShowed);
    }

    public boolean isContextMenuShowed() {
        return this.state.getContextMenuShowed();
    }

}
