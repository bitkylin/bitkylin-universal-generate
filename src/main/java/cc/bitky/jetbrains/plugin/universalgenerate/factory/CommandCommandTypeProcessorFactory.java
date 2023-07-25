package cc.bitky.jetbrains.plugin.universalgenerate.factory;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.config.AnnotationTagConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl.*;
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
            case RENEW_WRITE_SWAGGER -> {
                return new CommandTypeRenewWriteSwaggerProcessor(writeContext);
            }
            case PADDING_WRITE_SWAGGER -> {
                return new CommandTypePaddingWriteSwaggerProcessor(writeContext);
            }
            case RENEW_WRITE_TAG -> {
                return new CommandTypeRenewWriteTagProcessor(writeContext, new AnnotationTagConfig());
            }
            case PADDING_WRITE_TAG -> {
                return new CommandTypePaddingWriteTagProcessor(writeContext, new AnnotationTagConfig());
            }
            case MERGE_SWAGGER_TO_JAVA_DOC -> {
                return new CommandTypeMergeSwaggerToJavaDocProcessor(writeContext);
            }
            case PADDING_SWAGGER_TO_JAVA_DOC -> {
                return new CommandTypePaddingSwaggerToJavaDocProcessor(writeContext);
            }
            default ->
                    throw NotificationUtils.notifyAndNewException(writeContext.fetchProject(), ExceptionMsgEnum.COMMAND_SCOPE_UNSUPPORTED);

        }
    }
}
