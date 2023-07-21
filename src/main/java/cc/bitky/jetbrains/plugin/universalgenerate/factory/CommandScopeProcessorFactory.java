package cc.bitky.jetbrains.plugin.universalgenerate.factory;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.scope.ICommandScopeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.scope.impl.CommandScopeElementProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.scope.impl.CommandScopeFileProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.NotificationUtils;

/**
 * @author bitkylin
 */
public final class CommandScopeProcessorFactory {

    private CommandScopeProcessorFactory() {
    }

    public static ICommandScopeProcessor decide(WriteContext writeContext) {
        switch (writeContext.getWriteCommand().getScope()) {
            case FILE -> {
                return new CommandScopeFileProcessor(writeContext);
            }
            case ELEMENT -> {
                return new CommandScopeElementProcessor(writeContext);
            }
            default ->
                    throw NotificationUtils.notifyAndNewException(writeContext.fetchProject(), ExceptionMsgEnum.COMMAND_SCOPE_UNSUPPORTED);
        }
    }
}
