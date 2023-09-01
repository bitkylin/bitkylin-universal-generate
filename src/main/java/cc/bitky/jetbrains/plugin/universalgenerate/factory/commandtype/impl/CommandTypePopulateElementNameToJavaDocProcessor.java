package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base.AbstractCommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.*;
import cc.bitky.jetbrains.plugin.universalgenerate.util.ExceptionUtils;
import cc.bitky.jetbrains.plugin.universalgenerate.util.JavaDocGenerateUtils;

/**
 * @author bitkylin
 */
public class CommandTypePopulateElementNameToJavaDocProcessor extends AbstractCommandTypeProcessor implements ICommandTypeProcessor {

    private final WriteContext writeContext;

    public CommandTypePopulateElementNameToJavaDocProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    @Override
    public void doWriteFile() {
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                JavaDocGenerateUtils.populateWriteFieldJavaDocFromProjectElementName(
                        writeContext.getPsiFileContext(),
                        psiFieldWrapper.getPsiField()
                );
            }
            for (PsiMethodWrapper psiMethodWrapper : psiClassWrapper.getMethodList()) {
                JavaDocGenerateUtils.populateWriteMethodJavaDocFromProjectElementName(
                        writeContext.getPsiFileContext(),
                        psiMethodWrapper.getPsiMethod()
                );
            }
        }
    }

    @Override
    public void doWriteElement() {
        SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        if (!selectWrapper.isSelected()) {
            throw ExceptionUtils.newException(ExceptionMsgEnum.ELEMENT_NOT_SELECT);
        }
        if (selectWrapper.getField() != null) {
            JavaDocGenerateUtils.populateWriteFieldJavaDocFromProjectElementName(
                    writeContext.getPsiFileContext(),
                    selectWrapper.getField().getPsiField()
            );
            return;
        }
        if (selectWrapper.getMethod() != null) {
            JavaDocGenerateUtils.populateWriteMethodJavaDocFromProjectElementName(
                    writeContext.getPsiFileContext(),
                    selectWrapper.getMethod().getPsiMethod()
            );
        }
    }

}
