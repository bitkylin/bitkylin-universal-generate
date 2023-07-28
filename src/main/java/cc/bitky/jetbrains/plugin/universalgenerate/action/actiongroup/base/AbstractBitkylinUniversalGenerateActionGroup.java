package cc.bitky.jetbrains.plugin.universalgenerate.action.actiongroup.base;

import cc.bitky.jetbrains.plugin.universalgenerate.config.ActionConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionGroupEnum;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import org.jetbrains.annotations.NotNull;

/**
 * @author bitkylin
 */
public class AbstractBitkylinUniversalGenerateActionGroup extends DefaultActionGroup {

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    protected void updateGroupText(AnActionEvent anActionEvent,
                                   ActionConfig actionConfig,
                                   ActionGroupEnum actionGroupEnum,
                                   String scope) {
        anActionEvent.getPresentation().setText(actionConfig.fetchActionGroupTitle(actionGroupEnum) + " - " + scope);
    }
}
