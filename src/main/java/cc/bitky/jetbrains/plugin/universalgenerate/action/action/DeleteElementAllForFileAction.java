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
public class DeleteElementAllForFileAction extends AbstractUniversalGenerateAction {

    public DeleteElementAllForFileAction(String text) {
        super(text);
    }

    public DeleteElementAllForFileAction() {
        super();
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        WriteContext writeContext = WriteContextActionBuilder.create(anActionEvent);

        WriteCommandActionUtils.runWriteCommandAction(writeContext.fetchProject(), () -> {
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.DELETE_JAVA_DOC).writeFile();
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.DELETE_ANNOTATION_SWAGGER).writeFile();
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.DELETE_ANNOTATION_TAG).writeFile();
        });
    }

    @Override
    protected ActionEnum fetchActionEnum() {
        return ActionEnum.DELETE_ELEMENT_ALL_FOR_FILE;
    }
}
