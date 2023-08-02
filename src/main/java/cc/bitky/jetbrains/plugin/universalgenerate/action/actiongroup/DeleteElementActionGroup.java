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
import com.intellij.openapi.actionSystem.Separator;
import com.intellij.openapi.project.DumbService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author bitkylin
 */
@Slf4j
public class DeleteElementActionGroup extends AbstractBitkylinUniversalGenerateActionGroup {

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent anActionEvent) {
        if (!GlobalSettingsStateHelper.getInstance().isContextMenuShowed()) {
            anActionEvent.getPresentation().setVisible(false);
            return new AnAction[0];
        }
        ActionConfig actionConfig = new ActionConfig();
        if (DumbService.isDumb(anActionEvent.getProject())) {
            anActionEvent.getPresentation().setEnabled(false);
            updateGroupText(anActionEvent, actionConfig, ActionGroupEnum.DELETE_ELEMENT, actionConfig.fetchTextForDumbMode());
            return new AnAction[0];
        }
        WriteContext writeContext = WriteContextBuilder.create(anActionEvent);
        if (writeContext.fetchSelected()) {
            updateGroupText(anActionEvent, actionConfig, ActionGroupEnum.DELETE_ELEMENT, actionConfig.fetchTextForElement());
            return new AnAction[]{
                    ActionFactory.create(actionConfig, ActionEnum.DELETE_ELEMENT_ALL_FOR_ELEMENT),
                    Separator.create(),
                    ActionFactory.create(actionConfig, ActionEnum.DELETE_JAVA_DOC_FOR_ELEMENT),
                    ActionFactory.create(actionConfig, ActionEnum.DELETE_ANNOTATION_SWAGGER_FOR_ELEMENT),
                    ActionFactory.create(actionConfig, ActionEnum.DELETE_ANNOTATION_TAG_FOR_ELEMENT)
            };
        }
        updateGroupText(anActionEvent, actionConfig, ActionGroupEnum.DELETE_ELEMENT, actionConfig.fetchTextForFile());
        return new AnAction[]{
                ActionFactory.create(actionConfig, ActionEnum.DELETE_ELEMENT_ALL_FOR_FILE),
                Separator.create(),
                ActionFactory.create(actionConfig, ActionEnum.DELETE_JAVA_DOC_FOR_FILE),
                ActionFactory.create(actionConfig, ActionEnum.DELETE_ANNOTATION_SWAGGER_FOR_FILE),
                ActionFactory.create(actionConfig, ActionEnum.DELETE_ANNOTATION_TAG_FOR_FILE)
        };
    }

    @Override
    public void update(@NotNull AnActionEvent anActionEvent) {
//        log.error("update - SwaggerToJavaDocActionGroup");
    }
}
