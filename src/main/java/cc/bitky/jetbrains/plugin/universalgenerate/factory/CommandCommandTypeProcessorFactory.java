package cc.bitky.jetbrains.plugin.universalgenerate.factory;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl.CommandTypeWriteSwaggerProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl.CommandTypeWriteTagProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteCommand;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.NotificationUtils;

/**
 * @author bitkylin
 */
public final class CommandCommandTypeProcessorFactory {

    private CommandCommandTypeProcessorFactory() {
    }

    public static ICommandTypeProcessor decide(WriteContext writeContext, WriteCommand.Command command) {
        switch (command) {
            case WRITE_SWAGGER -> {
                return new CommandTypeWriteSwaggerProcessor();
            }
            case WRITE_TAG -> {
                return new CommandTypeWriteTagProcessor();
            }
            default ->
                    throw NotificationUtils.notifyAndNewException(writeContext.fetchProject(), ExceptionMsgEnum.COMMAND_SCOPE_UNSUPPORTED);

        }
    }
}
