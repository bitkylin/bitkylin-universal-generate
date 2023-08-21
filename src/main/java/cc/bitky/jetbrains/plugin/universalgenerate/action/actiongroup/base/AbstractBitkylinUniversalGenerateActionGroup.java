package cc.bitky.jetbrains.plugin.universalgenerate.action.actiongroup.base;

import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.ActionLocalizationConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionGroupEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author bitkylin
 */
public class AbstractBitkylinUniversalGenerateActionGroup extends DefaultActionGroup {

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    protected void updateGroupTextForSelected(AnActionEvent anActionEvent,
                                              ActionLocalizationConfig actionLocalizationConfig,
                                              ActionGroupEnum actionGroupEnum,
                                              WriteContext.SelectWrapper selectWrapper) {
        if (selectWrapper.getField() != null) {
            anActionEvent.getPresentation().setText(actionLocalizationConfig.fetchActionGroupTitle(actionGroupEnum) + " - " + actionLocalizationConfig.fetchTextForCurrentField());
            return;
        }
        if (selectWrapper.getMethod() != null) {
            anActionEvent.getPresentation().setText(actionLocalizationConfig.fetchActionGroupTitle(actionGroupEnum) + " - " + actionLocalizationConfig.fetchTextForCurrentMethod());
            return;
        }
        if (selectWrapper.getClz() != null) {
            anActionEvent.getPresentation().setText(actionLocalizationConfig.fetchActionGroupTitle(actionGroupEnum) + " - " + actionLocalizationConfig.fetchTextForCurrentClassName());
        }
    }

    protected void updateGroupText(AnActionEvent anActionEvent,
                                   ActionLocalizationConfig actionLocalizationConfig,
                                   ActionGroupEnum actionGroupEnum,
                                   String scope) {
        if (StringUtils.isBlank(scope)) {
            anActionEvent.getPresentation().setText(actionLocalizationConfig.fetchActionGroupTitle(actionGroupEnum));
            return;
        }
        anActionEvent.getPresentation().setText(actionLocalizationConfig.fetchActionGroupTitle(actionGroupEnum) + " - " + scope);
    }
}
