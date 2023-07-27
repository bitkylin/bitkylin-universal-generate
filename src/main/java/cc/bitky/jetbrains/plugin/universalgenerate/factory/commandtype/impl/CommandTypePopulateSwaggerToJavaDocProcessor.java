package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiMethodWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.NotificationUtils;

import static cc.bitky.jetbrains.plugin.universalgenerate.service.tag.JavaDocGenerateUtils.populateWriteJavaDoc;

/**
 * 移除swagger注解，并转换为JavaDoc，JavaDoc存在时不变更
 *
 * @author bitkylin
 */
public class CommandTypePopulateSwaggerToJavaDocProcessor implements ICommandTypeProcessor {

    private final WriteContext writeContext;

    public CommandTypePopulateSwaggerToJavaDocProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    @Override
    public void writeFile() {
        WriteContext.PsiFileContext psiFileContext = writeContext.getPsiFileContext();
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            populateWriteJavaDoc(psiFileContext, psiClassWrapper.getPsiClass());
            for (PsiMethodWrapper psiMethodWrapper : psiClassWrapper.getMethodList()) {
                populateWriteJavaDoc(psiFileContext, psiMethodWrapper.getPsiMethod());
            }
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                populateWriteJavaDoc(psiFileContext, psiFieldWrapper.getPsiField());
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
            populateWriteJavaDoc(writeContext.getPsiFileContext(), psiClassWrapper.getPsiClass());
            return;
        }

        if (selectWrapper.getMethod() != null) {
            populateWriteJavaDoc(writeContext.getPsiFileContext(), selectWrapper.getMethod().getPsiMethod());
            return;
        }

        if (selectWrapper.getField() != null) {
            populateWriteJavaDoc(writeContext.getPsiFileContext(), selectWrapper.getField().getPsiField());
        }
    }

}
