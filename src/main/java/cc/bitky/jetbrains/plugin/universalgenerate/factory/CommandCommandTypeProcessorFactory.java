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
            case RE_GENERATE_WRITE_SWAGGER -> {
                return new CommandTypeReGenerateWriteSwaggerProcessor(writeContext);
            }
            case POPULATE_WRITE_SWAGGER -> {
                return new CommandTypePopulateWriteSwaggerProcessor(writeContext);
            }
            case RE_GENERATE_WRITE_TAG -> {
                return new CommandTypeReGenerateWriteTagProcessor(writeContext, new AnnotationTagConfig());
            }
            case POPULATE_WRITE_TAG -> {
                return new CommandTypePopulateWriteTagProcessor(writeContext, new AnnotationTagConfig());
            }
            case RE_GENERATE_SWAGGER_TO_JAVA_DOC -> {
                return new CommandTypeReGenerateSwaggerToJavaDocProcessor(writeContext);
            }
            case POPULATE_SWAGGER_TO_JAVA_DOC -> {
                return new CommandTypePopulateSwaggerToJavaDocProcessor(writeContext);
            }
            default ->
                    throw NotificationUtils.notifyAndNewException(writeContext.fetchProject(), ExceptionMsgEnum.COMMAND_SCOPE_UNSUPPORTED);

        }
    }
}
