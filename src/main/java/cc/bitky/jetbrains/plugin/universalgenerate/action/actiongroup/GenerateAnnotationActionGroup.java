package cc.bitky.jetbrains.plugin.universalgenerate.action.actiongroup;

import cc.bitky.jetbrains.plugin.universalgenerate.action.actiongroup.base.AbstractBitkylinUniversalGenerateActionGroup;
import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.ActionLocalizationConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionGroupEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.ActionFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.builder.WriteContextActionBuilder;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author bitkylin
 */
@Slf4j
public class GenerateAnnotationActionGroup extends AbstractBitkylinUniversalGenerateActionGroup {

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent anActionEvent) {
        if (!GlobalSettingsStateHelper.getInstance().isRightClickMenuEnabled()) {
            anActionEvent.getPresentation().setVisible(false);
            return new AnAction[0];
        }
        ActionLocalizationConfig actionLocalizationConfig = new ActionLocalizationConfig();
        if (DumbService.isDumb(anActionEvent.getProject())) {
            anActionEvent.getPresentation().setEnabled(false);
            updateGroupText(anActionEvent, actionLocalizationConfig, ActionGroupEnum.GENERATE_ANNOTATION, actionLocalizationConfig.fetchTextForDumbMode());
            return new AnAction[0];
        }
        WriteContext writeContext = WriteContextActionBuilder.create(anActionEvent);
        if (writeContext.fetchSelected()) {
            updateGroupTextForSelected(anActionEvent, actionLocalizationConfig, ActionGroupEnum.GENERATE_ANNOTATION, writeContext.getSelectWrapper());
            return new AnAction[]{
                    ActionFactory.create(actionLocalizationConfig, ActionEnum.POPULATE_MISSING_ANNOTATION_FOR_ELEMENT),
                    ActionFactory.create(actionLocalizationConfig, ActionEnum.RE_GENERATE_ANNOTATION_FOR_ELEMENT)
            };
        }
        updateGroupText(anActionEvent, actionLocalizationConfig, ActionGroupEnum.GENERATE_ANNOTATION, actionLocalizationConfig.fetchTextForFile());
        return new AnAction[]{
                ActionFactory.create(actionLocalizationConfig, ActionEnum.POPULATE_MISSING_ANNOTATION_FOR_FILE),
                ActionFactory.create(actionLocalizationConfig, ActionEnum.RE_GENERATE_ANNOTATION_FOR_FILE)
        };
    }

    @Override
    public void update(@NotNull AnActionEvent anActionEvent) {
        super.update(anActionEvent);

        if (CollectionUtils.isNotEmpty(GlobalSettingsStateHelper.getInstance().getAnnotationAffectedListShowed(anActionEvent.getProject()))) {
            anActionEvent.getPresentation().setEnabled(true);
            anActionEvent.getPresentation().setVisible(true);
            return;
        }
        anActionEvent.getPresentation().setEnabled(false);
        anActionEvent.getPresentation().setVisible(false);
    }
}
