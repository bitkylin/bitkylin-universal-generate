package cc.bitky.jetbrains.plugin.universalgenerate.factory;

import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsState;
import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
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
        Set<GlobalSettingsState.AnnotationAffectedEnum> annotationAffectedSet = Sets.newHashSet(GlobalSettingsStateHelper.getInstance().getAnnotationAffectedListShowed(writeContext.getPsiFileContext().getProject()));

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
                case DELETE_ANNOTATION_SWAGGER -> {
                    return new CommandTypeDeleteAnnotationSwaggerProcessor(writeContext);
                }
                default -> {
                }
            }
        }

        if (annotationAffectedSet.contains(GlobalSettingsState.AnnotationAffectedEnum.PROTOSTUFF)) {
            switch (command) {
                case RE_GENERATE_WRITE_TAG -> {
                    return new CommandTypeReGenerateWriteTagProcessor(writeContext);
                }
                case POPULATE_WRITE_TAG -> {
                    return new CommandTypePopulateWriteTagProcessor(writeContext);
                }
                case DELETE_ANNOTATION_TAG -> {
                    return new CommandTypeDeleteAnnotationTagProcessor(writeContext);
                }
                default -> {
                }
            }
        }

        switch (command) {
            case RE_GENERATE_ELEMENT_NAME_TO_JAVA_DOC -> {
                return new CommandTypeReGenerateElementNameToJavaDocProcessor(writeContext);
            }
            case POPULATE_ELEMENT_NAME_TO_JAVA_DOC -> {
                return new CommandTypePopulateElementNameToJavaDocProcessor(writeContext);
            }
            case DELETE_JAVA_DOC -> {
                return new CommandTypeDeleteJavaDocProcessor(writeContext);
            }
            default -> {
            }
        }

        return new CommandTypeEmptyProcessor(writeContext);
    }
}
