package cc.bitky.jetbrains.plugin.universalgenerate.action.actiongroup;

import cc.bitky.jetbrains.plugin.universalgenerate.action.actiongroup.base.AbstractBitkylinUniversalGenerateActionGroup;
import cc.bitky.jetbrains.plugin.universalgenerate.config.ActionConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionGroupEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.ActionFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.builder.WriteContextBuilder;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author bitkylin
 */
@Slf4j
public class ElementNameToJavaDocActionGroup extends AbstractBitkylinUniversalGenerateActionGroup {

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent anActionEvent) {
        if (!GlobalSettingsStateHelper.getInstance().isContextMenuShowed()) {
            anActionEvent.getPresentation().setVisible(false);
            return new AnAction[0];
        }
        ActionConfig actionConfig = new ActionConfig();
        if (DumbService.isDumb(anActionEvent.getProject())) {
            anActionEvent.getPresentation().setEnabled(false);
            updateGroupText(anActionEvent, actionConfig, ActionGroupEnum.ELEMENT_TO_JAVA_DOC, actionConfig.fetchTextForDumbMode());
            return new AnAction[0];
        }
        WriteContext writeContext = WriteContextBuilder.create(anActionEvent);
        if (writeContext.fetchSelected()) {
            return anActionListForSelected(anActionEvent, writeContext.getSelectWrapper(), actionConfig);
        }
        updateGroupText(anActionEvent, actionConfig, ActionGroupEnum.ELEMENT_TO_JAVA_DOC, actionConfig.fetchTextForFile());
        return new AnAction[]{
                ActionFactory.create(actionConfig, ActionEnum.POPULATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_FILE),
                ActionFactory.create(actionConfig, ActionEnum.RE_GENERATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_FILE)
        };
    }

    private AnAction[] anActionListForSelected(AnActionEvent anActionEvent, WriteContext.SelectWrapper selectWrapper, ActionConfig actionConfig) {
        if (selectWrapper.getField() == null && selectWrapper.getMethod() == null) {
            anActionEvent.getPresentation().setVisible(false);
            updateGroupText(anActionEvent, actionConfig, ActionGroupEnum.ELEMENT_TO_JAVA_DOC, actionConfig.fetchTextForNotSupport());
            return new AnAction[0];
        }
        updateGroupTextForSelected(anActionEvent, actionConfig, ActionGroupEnum.ELEMENT_TO_JAVA_DOC, selectWrapper);
        return new AnAction[]{
                ActionFactory.create(actionConfig, ActionEnum.POPULATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_ELEMENT),
                ActionFactory.create(actionConfig, ActionEnum.RE_GENERATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_ELEMENT)
        };
    }

    @Override
    public void update(@NotNull AnActionEvent anActionEvent) {
        super.update(anActionEvent);
    }
}
