package cc.bitky.jetbrains.plugin.universalgenerate.action.action;

import cc.bitky.jetbrains.plugin.universalgenerate.action.action.base.AbstractBitkylinUniversalGenerateAction;
import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsState;
import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.CommandCommandTypeProcessorFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteCommand;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.builder.WriteContextBuilder;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * @author bitkylin
 */
@Slf4j
public class DeleteElementAnnotationSwaggerForElementAction extends AbstractBitkylinUniversalGenerateAction {

    public DeleteElementAnnotationSwaggerForElementAction(String text) {
        super(text);
    }

    public DeleteElementAnnotationSwaggerForElementAction() {
        super();
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        WriteContext writeContext = WriteContextBuilder.create(anActionEvent);

        WriteCommandAction.runWriteCommandAction(writeContext.fetchProject(), () -> {
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.DELETE_ANNOTATION_SWAGGER).writeElement();
        });
    }

    @Override
    public void update(@NotNull AnActionEvent anActionEvent) {
        super.update(anActionEvent);

        if (GlobalSettingsStateHelper.getInstance().getAnnotationAffectedList().contains(GlobalSettingsState.AnnotationAffectedEnum.SWAGGER)) {
            anActionEvent.getPresentation().setEnabled(true);
            anActionEvent.getPresentation().setVisible(true);
            return;
        }
        anActionEvent.getPresentation().setEnabled(false);
        anActionEvent.getPresentation().setVisible(false);
    }

    @Override
    protected ActionEnum fetchActionEnum() {
        return ActionEnum.DELETE_ANNOTATION_SWAGGER_FOR_ELEMENT;
    }
}
