package cc.bitky.jetbrains.plugin.universalgenerate.action;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.CommandCommandTypeProcessorFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteCommand;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.builder.WriteContextBuilder;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 在当前文件中填充所有注解
 *
 * @author bitkylin
 */
@Slf4j
public class PaddingGenerateForFileAction extends AnAction {

    @SneakyThrows
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        WriteContext writeContext = WriteContextBuilder.create(anActionEvent);

        WriteCommandAction.runWriteCommandAction(writeContext.fetchProject(), () -> {
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.PADDING_WRITE_SWAGGER).writeFile();
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.PADDING_WRITE_TAG).writeFile();
        });
    }

}