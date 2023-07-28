package cc.bitky.jetbrains.plugin.universalgenerate.factory;

import cc.bitky.jetbrains.plugin.universalgenerate.config.AnnotationTagConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.config.setting.state.GlobalSettingsState;
import cc.bitky.jetbrains.plugin.universalgenerate.config.setting.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl.*;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteCommand;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author bitkylin
 */
public final class CommandCommandTypeProcessorFactory {

    private CommandCommandTypeProcessorFactory() {
    }

    public static ICommandTypeProcessor decide(WriteContext writeContext, WriteCommand.Command command) {
        Set<GlobalSettingsState.AnnotationAffectedEnum> annotationAffectedSet = Sets.newHashSet(GlobalSettingsStateHelper.getInstance().getAnnotationAffectedList());

        if (!writeContext.getPsiFileContext().valid()) {
            return new CommandTypeEmptyProcessor(writeContext);
        }

        if (annotationAffectedSet.contains(GlobalSettingsState.AnnotationAffectedEnum.SWAGGER)) {
            switch (command) {
                case RE_GENERATE_WRITE_SWAGGER -> {
                    return new CommandTypeReGenerateWriteSwaggerProcessor(writeContext);
                }
                case POPULATE_WRITE_SWAGGER -> {
                    return new CommandTypePopulateWriteSwaggerProcessor(writeContext);
                }
                case RE_GENERATE_SWAGGER_TO_JAVA_DOC -> {
                    return new CommandTypeReGenerateSwaggerToJavaDocProcessor(writeContext);
                }
                case POPULATE_SWAGGER_TO_JAVA_DOC -> {
                    return new CommandTypePopulateSwaggerToJavaDocProcessor(writeContext);
                }
            }
        }

        if (annotationAffectedSet.contains(GlobalSettingsState.AnnotationAffectedEnum.PROTOSTUFF)) {
            switch (command) {
                case RE_GENERATE_WRITE_TAG -> {
                    return new CommandTypeReGenerateWriteTagProcessor(writeContext, new AnnotationTagConfig());
                }
                case POPULATE_WRITE_TAG -> {
                    return new CommandTypePopulateWriteTagProcessor(writeContext, new AnnotationTagConfig());
                }
                case DELETE_TAG -> {
                    return new CommandTypeDeleteTagProcessor(writeContext);
                }

            }
        }

        return new CommandTypeEmptyProcessor(writeContext);
    }
}
