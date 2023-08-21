package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base.AbstractCommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiMethodWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.ExceptionUtils;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.JavaDocGenerateUtils.populateWriteJavaDocFromAnnotation;

/**
 * 移除swagger注解，并转换为JavaDoc，JavaDoc存在时不变更
 *
 * @author bitkylin
 */
public class CommandTypePopulateSwaggerToJavaDocProcessor extends AbstractCommandTypeProcessor implements ICommandTypeProcessor {

    private final WriteContext writeContext;

    public CommandTypePopulateSwaggerToJavaDocProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    @Override
    public void doWriteFile() {
        WriteContext.PsiFileContext psiFileContext = writeContext.getPsiFileContext();
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            populateWriteJavaDocFromAnnotation(psiFileContext, psiClassWrapper.getPsiClass());
            for (PsiMethodWrapper psiMethodWrapper : psiClassWrapper.getMethodList()) {
                populateWriteJavaDocFromAnnotation(psiFileContext, psiMethodWrapper.getPsiMethod());
            }
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                populateWriteJavaDocFromAnnotation(psiFileContext, psiFieldWrapper.getPsiField());
            }
        }
    }

    @Override
    public void doWriteElement() {
        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        PsiClassWrapper psiClassWrapper = selectWrapper.getSelectedPsiClassWrapper();
        if (!selectWrapper.isSelected()) {
            throw ExceptionUtils.newException(ExceptionMsgEnum.ELEMENT_NOT_SELECT);
        }

        if (selectWrapper.getClz() != null) {
            populateWriteJavaDocFromAnnotation(writeContext.getPsiFileContext(), psiClassWrapper.getPsiClass());
            return;
        }

        if (selectWrapper.getMethod() != null) {
            populateWriteJavaDocFromAnnotation(writeContext.getPsiFileContext(), selectWrapper.getMethod().getPsiMethod());
            return;
        }

        if (selectWrapper.getField() != null) {
            populateWriteJavaDocFromAnnotation(writeContext.getPsiFileContext(), selectWrapper.getField().getPsiField());
        }
    }

}
