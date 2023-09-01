package cc.bitky.jetbrains.plugin.universalgenerate.action.action;

import cc.bitky.jetbrains.plugin.universalgenerate.action.action.base.AbstractUniversalGenerateAction;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.CommandCommandTypeProcessorFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteCommand;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.WriteCommandActionUtils;
import cc.bitky.jetbrains.plugin.universalgenerate.util.builder.WriteContextActionBuilder;
import com.intellij.openapi.actionSystem.AnActionEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bitkylin
 */
@Slf4j
public class DeleteElementAllForElementAction extends AbstractUniversalGenerateAction {

    public DeleteElementAllForElementAction(String text) {
        super(text);
    }

    public DeleteElementAllForElementAction() {
        super();
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        WriteContext writeContext = WriteContextActionBuilder.create(anActionEvent);

        WriteCommandActionUtils.runWriteCommandAction(writeContext.fetchProject(), () -> {
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.DELETE_JAVA_DOC).writeElement();
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.DELETE_ANNOTATION_SWAGGER).writeElement();
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.DELETE_ANNOTATION_TAG).writeElement();
        });
    }

    @Override
    protected ActionEnum fetchActionEnum() {
        return ActionEnum.DELETE_ELEMENT_ALL_FOR_ELEMENT;
    }
}
