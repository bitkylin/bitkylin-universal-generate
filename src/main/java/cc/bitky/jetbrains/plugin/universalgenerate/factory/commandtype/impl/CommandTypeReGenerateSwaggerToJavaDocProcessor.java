package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiMethodWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.NotificationUtils;

import static cc.bitky.jetbrains.plugin.universalgenerate.service.tag.JavaDocGenerateUtils.reGenerateWriteJavaDoc;

/**
 * 移除swagger注解，并转换为JavaDoc
 *
 * @author bitkylin
 */
public class CommandTypeReGenerateSwaggerToJavaDocProcessor implements ICommandTypeProcessor {

    private final WriteContext writeContext;

    public CommandTypeReGenerateSwaggerToJavaDocProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    @Override
    public void writeFile() {
        WriteContext.PsiFileContext psiFileContext = writeContext.getPsiFileContext();
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            reGenerateWriteJavaDoc(psiFileContext, psiClassWrapper.getPsiClass());
            for (PsiMethodWrapper psiMethodWrapper : psiClassWrapper.getMethodList()) {
                reGenerateWriteJavaDoc(psiFileContext, psiMethodWrapper.getPsiMethod());
            }
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                reGenerateWriteJavaDoc(psiFileContext, psiFieldWrapper.getPsiField());
            }
        }
    }

    @Override
    public void writeElement() {
        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        PsiClassWrapper psiClassWrapper = selectWrapper.getSelectedPsiClassWrapper();
        if (!selectWrapper.isSelected()) {
            throw NotificationUtils.notifyAndNewException(writeContext.fetchProject(), ExceptionMsgEnum.ELEMENT_NOT_SELECT);
        }

        if (selectWrapper.getClz() != null) {
            reGenerateWriteJavaDoc(writeContext.getPsiFileContext(), psiClassWrapper.getPsiClass());
            return;
        }

        if (selectWrapper.getMethod() != null) {
            reGenerateWriteJavaDoc(writeContext.getPsiFileContext(), selectWrapper.getMethod().getPsiMethod());
            return;
        }

        if (selectWrapper.getField() != null) {
            reGenerateWriteJavaDoc(writeContext.getPsiFileContext(), selectWrapper.getField().getPsiField());
        }
    }

}
