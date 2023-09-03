package cc.bitky.jetbrains.plugin.universalgenerate.action.actiongroup;

import cc.bitky.jetbrains.plugin.universalgenerate.action.actiongroup.base.AbstractBitkylinUniversalGenerateActionGroup;
import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.ActionLocalizationConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionGroupEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.ActionFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.SelectWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.builder.WriteContextActionBuilder;
import com.google.common.collect.Lists;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Separator;
import com.intellij.openapi.project.DumbService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
        ActionLocalizationConfig actionLocalizationConfig = new ActionLocalizationConfig();
        if (DumbService.isDumb(anActionEvent.getProject())) {
            anActionEvent.getPresentation().setEnabled(false);
            updateGroupText(anActionEvent, actionLocalizationConfig, ActionGroupEnum.DELETE_ELEMENT, actionLocalizationConfig.fetchTextForDumbMode());
            return new AnAction[0];
        }
        WriteContext writeContext = WriteContextActionBuilder.create(anActionEvent);
        if (writeContext.fetchSelected()) {
            return anActionListForSelected(anActionEvent, writeContext.getSelectWrapper(), actionLocalizationConfig);
        }
        updateGroupText(anActionEvent, actionLocalizationConfig, ActionGroupEnum.DELETE_ELEMENT, actionLocalizationConfig.fetchTextForFile());
        return new AnAction[]{
                ActionFactory.create(actionLocalizationConfig, ActionEnum.DELETE_ELEMENT_ALL_FOR_FILE),
                Separator.create(),
                ActionFactory.create(actionLocalizationConfig, ActionEnum.DELETE_JAVA_DOC_FOR_FILE),
                ActionFactory.create(actionLocalizationConfig, ActionEnum.DELETE_ANNOTATION_SWAGGER_FOR_FILE),
                ActionFactory.create(actionLocalizationConfig, ActionEnum.DELETE_ANNOTATION_TAG_FOR_FILE)
        };
    }

    private AnAction[] anActionListForSelected(AnActionEvent anActionEvent, SelectWrapper selectWrapper, ActionLocalizationConfig actionLocalizationConfig) {
        updateGroupTextForSelected(anActionEvent, actionLocalizationConfig, ActionGroupEnum.DELETE_ELEMENT, selectWrapper);
        List<AnAction> list = Lists.newArrayList();
        list.add(ActionFactory.create(actionLocalizationConfig, ActionEnum.DELETE_ELEMENT_ALL_FOR_ELEMENT));
        list.add(Separator.create());
        list.add(ActionFactory.create(actionLocalizationConfig, ActionEnum.DELETE_JAVA_DOC_FOR_ELEMENT));
        list.add(ActionFactory.create(actionLocalizationConfig, ActionEnum.DELETE_ANNOTATION_SWAGGER_FOR_ELEMENT));
        if (selectWrapper.getField() != null) {
            list.add(ActionFactory.create(actionLocalizationConfig, ActionEnum.DELETE_ANNOTATION_TAG_FOR_ELEMENT));
        }
        return list.toArray(new AnAction[0]);
    }

    @Override
    public void update(@NotNull AnActionEvent anActionEvent) {
        super.update(anActionEvent);
    }
}
