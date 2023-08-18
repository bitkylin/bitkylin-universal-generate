package cc.bitky.jetbrains.plugin.universalgenerate.action.action;

import cc.bitky.jetbrains.plugin.universalgenerate.action.action.base.AbstractBitkylinUniversalGenerateAction;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.CommandCommandTypeProcessorFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteCommand;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.builder.WriteContextBuilder;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import lombok.extern.slf4j.Slf4j;

/**
 * 在当前文件中填充所有注解：Swagger注解、Protostuff注解
 *
 * @author bitkylin
 */
@Slf4j
public class PopulateMissingAnnotationForFileAction extends AbstractBitkylinUniversalGenerateAction {

    public PopulateMissingAnnotationForFileAction(String text) {
        super(text);
    }

    public PopulateMissingAnnotationForFileAction() {
        super();
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        WriteContext writeContext = WriteContextBuilder.create(anActionEvent);

        WriteCommandAction.runWriteCommandAction(writeContext.fetchProject(), () -> {
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.POPULATE_WRITE_SWAGGER).writeFile();
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.POPULATE_WRITE_TAG).writeFile();
        });
    }

    @Override
    protected ActionEnum fetchActionEnum() {
        return ActionEnum.POPULATE_MISSING_ANNOTATION_FOR_FILE;
    }
}
