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
 * 在当前文件中强制重新生成所有注解
 *
 * @author bitkylin
 */
@Slf4j
public class RenewGenerateForFileAction extends AnAction {

    @SneakyThrows
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        WriteContext writeContext = WriteContextBuilder.create(anActionEvent);

        WriteCommandAction.runWriteCommandAction(writeContext.fetchProject(), () -> {
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.RENEW_WRITE_SWAGGER).writeFile();
        });

        WriteCommandAction.runWriteCommandAction(writeContext.fetchProject(), () -> {
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.RENEW_WRITE_TAG).writeFile();
        });
    }

}
