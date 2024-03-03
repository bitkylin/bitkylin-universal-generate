package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base.CommandTypeAbstractWriteTagProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;

/**
 * Tag注解强制写处理器
 *
 * @author bitkylin
 */
public class CommandTypeReGenerateWriteTagProcessor extends CommandTypeAbstractWriteTagProcessor implements ICommandTypeProcessor {

    private final CommandTypePopulateWriteTagProcessor populateWriteTagProcessor;
    private final CommandTypeDeleteAnnotationTagProcessor deleteAnnotationTagProcessor;

    public CommandTypeReGenerateWriteTagProcessor(WriteContext writeContext,
                                                  CommandTypePopulateWriteTagProcessor populateWriteTagProcessor,
                                                  CommandTypeDeleteAnnotationTagProcessor deleteAnnotationTagProcessor) {
        this.writeContext = writeContext;
        this.populateWriteTagProcessor = populateWriteTagProcessor;
        this.deleteAnnotationTagProcessor = deleteAnnotationTagProcessor;
        beginNumValue = GlobalSettingsStateHelper.getInstance().getProtostuffTagStartValue();
        stepNumValue = GlobalSettingsStateHelper.getInstance().getProtostuffTagScopeInterval();
    }

    @Override
    public void doWriteFile() {
        deleteAnnotationTagProcessor.doWriteFile();
        populateWriteTagProcessor.doWriteFile();
    }

    @Override
    public void doWriteElement() {
        deleteAnnotationTagProcessor.doWriteElement();
        populateWriteTagProcessor.doWriteElement();
    }
}
