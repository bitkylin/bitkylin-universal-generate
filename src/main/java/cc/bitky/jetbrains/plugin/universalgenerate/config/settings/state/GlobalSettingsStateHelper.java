package cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state;

import cc.bitky.jetbrains.plugin.universalgenerate.constants.ModifierAnnotationEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.util.ListUtils;
import com.google.common.base.Enums;
import com.google.common.collect.Lists;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.List;

/**
 * @author bitkylin
 */
public class GlobalSettingsStateHelper {

    private final GlobalSettingsState state;

    /**
     * swagger 依赖是否存在
     */
    private Boolean swaggerDependencyExisted;

    /**
     * protostuff 依赖是否存在
     */
    private Boolean protostuffDependencyExisted;

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

    public boolean annotationSwaggerEnabledShowed(Project project) {
        boolean showed = this.state.getAnnotationAffectedList().contains(GlobalSettingsState.AnnotationAffectedEnum.SWAGGER.name());
        if (!showed) {
            return false;
        }
        return calcSwaggerDependencyExisted(project);
    }

    public boolean annotationProtostuffEnabledShowed(Project project) {
        boolean showed = this.state.getAnnotationAffectedList().contains(GlobalSettingsState.AnnotationAffectedEnum.PROTOSTUFF.name());
        if (!showed) {
            return false;
        }
        return calcProtostuffDependencyExisted(project);
    }

    public List<GlobalSettingsState.AnnotationAffectedEnum> getAnnotationAffectedListShowed(Project project) {
        List<GlobalSettingsState.AnnotationAffectedEnum> list = Lists.newArrayList();
        if (annotationSwaggerEnabledShowed(project)) {
            list.add(GlobalSettingsState.AnnotationAffectedEnum.SWAGGER);
        }
        if (annotationProtostuffEnabledShowed(project)) {
            list.add(GlobalSettingsState.AnnotationAffectedEnum.PROTOSTUFF);
        }
        return list;
    }

    private boolean calcSwaggerDependencyExisted(Project project) {
        if (swaggerDependencyExisted == null) {
            swaggerDependencyExisted = null != JavaPsiFacade.getInstance(project)
                    .findClass(ModifierAnnotationEnum.API_MODEL.getQualifiedName(), GlobalSearchScope.allScope(project));
        }
        return swaggerDependencyExisted;
    }

    private boolean calcProtostuffDependencyExisted(Project project) {
        if (protostuffDependencyExisted == null) {
            protostuffDependencyExisted = null != JavaPsiFacade.getInstance(project)
                    .findClass(ModifierAnnotationEnum.TAG.getQualifiedName(), GlobalSearchScope.allScope(project));
        }
        return protostuffDependencyExisted;
    }

    public List<GlobalSettingsState.AnnotationAffectedEnum> getAnnotationAffectedList() {
        return ListUtils.distinctMap(this.state.getAnnotationAffectedList(),
                enumStr -> Enums.getIfPresent(GlobalSettingsState.AnnotationAffectedEnum.class, enumStr).orNull());
    }

    public void setAnnotationAffectedList(List<GlobalSettingsState.AnnotationAffectedEnum> annotationAffectedList) {
        this.state.setAnnotationAffectedList(ListUtils.distinctMap(annotationAffectedList, Enum::name));
    }

    public void setRightClickMenuEnabled(boolean contextMenuShowed) {
        this.state.setContextMenuShowed(contextMenuShowed);
    }

    public boolean isRightClickMenuEnabled() {
        return this.state.getContextMenuShowed();
    }

    public void setIntentionActionEnabled(boolean enabled) {
        this.state.setIntentionActionEnabled(enabled);
    }

    public boolean isIntentionActionEnabled() {
        return this.state.getIntentionActionEnabled();
    }

    /**
     * 为降低使用成本，Intention Action 目前仅「填充」生效，「重新生成」无效
     */
    public boolean isIntentionReGenerateShowed() {
        return false;
    }

    public void setProtostuffTagAssign(GlobalSettingsState.ProtostuffTagAssignEnum tagAssignEnum) {
        this.state.setProtostuffTagAssign(tagAssignEnum.name());
    }

    public GlobalSettingsState.ProtostuffTagAssignEnum getProtostuffTagAssign() {
        return Enums.getIfPresent(GlobalSettingsState.ProtostuffTagAssignEnum.class, this.state.getProtostuffTagAssign())
                .or(GlobalSettingsState.ProtostuffTagAssignEnum.NON_REPEATABLE);
    }

    public void setProtostuffTagStartValue(int startValue) {
        this.state.setProtostuffTagStartValue(startValue);
    }

    public int getProtostuffTagStartValue() {
        return this.state.getProtostuffTagStartValue();
    }

    public void setProtostuffTagScopeInterval(int scopeInterval) {
        this.state.setProtostuffTagScopeInterval(scopeInterval);
    }

    public int getProtostuffTagScopeInterval() {
        return this.state.getProtostuffTagScopeInterval();
    }

}
