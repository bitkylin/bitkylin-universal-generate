package cc.bitky.jetbrains.plugin.universalgenerate.factory.scope.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.scope.ICommandScopeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.NotificationUtils;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils.generateClassSwaggerAnnotation;
import static cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils.generateFieldSwaggerAnnotation;

/**
 * @author bitkylin
 */
public class CommandScopeElementProcessor implements ICommandScopeProcessor {

    private final WriteContext writeContext;

    public CommandScopeElementProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    @Override
    public void process() {
        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        if (!selectWrapper.isSelected()) {
            throw NotificationUtils.notifyAndNewException(writeContext.fetchProject(), ExceptionMsgEnum.ELEMENT_NOT_SELECT);
        }

        if (selectWrapper.getClz() != null) {
            generateClassSwaggerAnnotation(writeContext.getPsiFileContext(), selectWrapper.getSelectedPsiClassWrapper());
            return;
        }

        if (selectWrapper.getField() != null) {
            generateFieldSwaggerAnnotation(writeContext.getPsiFileContext(), selectWrapper.getField());
        }
    }
}
