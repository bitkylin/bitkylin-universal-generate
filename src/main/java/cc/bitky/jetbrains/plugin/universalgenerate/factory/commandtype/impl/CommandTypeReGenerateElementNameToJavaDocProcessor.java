package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base.AbstractCommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.NotificationUtils;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.JavaDocGenerateUtils.reGenerateWriteJavaDocFromProjectElementName;

/**
 * @author bitkylin
 */
public class CommandTypeReGenerateElementNameToJavaDocProcessor extends AbstractCommandTypeProcessor implements ICommandTypeProcessor {

    private final WriteContext writeContext;

    public CommandTypeReGenerateElementNameToJavaDocProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    @Override
    public void doWriteFile() {
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                reGenerateWriteJavaDocFromProjectElementName(
                        writeContext.getPsiFileContext(),
                        psiFieldWrapper.getPsiField()
                );
            }
        }
    }

    @Override
    public void doWriteElement() {
        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        if (!selectWrapper.isSelected()) {
            throw NotificationUtils.notifyAndNewException(writeContext.fetchProject(), ExceptionMsgEnum.ELEMENT_NOT_SELECT);
        }
        if (selectWrapper.getField() != null) {
            reGenerateWriteJavaDocFromProjectElementName(
                    writeContext.getPsiFileContext(),
                    selectWrapper.getField().getPsiField()
            );
        }
    }
}

