package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base.AbstractCommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiMethodWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.NotificationUtils;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.JavaDocGenerateUtils.reGenerateWriteFieldJavaDocFromProjectElementName;
import static cc.bitky.jetbrains.plugin.universalgenerate.util.JavaDocGenerateUtils.reGenerateWriteMethodJavaDocFromProjectElementName;

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
                reGenerateWriteFieldJavaDocFromProjectElementName(
                        writeContext.getPsiFileContext(),
                        psiFieldWrapper.getPsiField()
                );
            }
            for (PsiMethodWrapper psiMethodWrapper : psiClassWrapper.getMethodList()) {
                reGenerateWriteMethodJavaDocFromProjectElementName(
                        writeContext.getPsiFileContext(),
                        psiMethodWrapper.getPsiMethod()
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
            reGenerateWriteFieldJavaDocFromProjectElementName(
                    writeContext.getPsiFileContext(),
                    selectWrapper.getField().getPsiField()
            );
        }
        if (selectWrapper.getMethod() != null) {
            reGenerateWriteMethodJavaDocFromProjectElementName(
                    writeContext.getPsiFileContext(),
                    selectWrapper.getMethod().getPsiMethod()
            );
        }
    }
}

