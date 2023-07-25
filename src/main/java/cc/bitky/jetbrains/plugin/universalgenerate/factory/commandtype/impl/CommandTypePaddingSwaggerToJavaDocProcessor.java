package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiMethodWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.NotificationUtils;

import static cc.bitky.jetbrains.plugin.universalgenerate.service.tag.JavaDocGenerateUtils.paddingWriteJavaDoc;

/**
 * 移除swagger注解，并转换为JavaDoc，JavaDoc存在时不变更
 *
 * @author bitkylin
 */
public class CommandTypePaddingSwaggerToJavaDocProcessor implements ICommandTypeProcessor {

    private final WriteContext writeContext;

    public CommandTypePaddingSwaggerToJavaDocProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    @Override
    public void writeFile() {
        WriteContext.PsiFileContext psiFileContext = writeContext.getPsiFileContext();
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            paddingWriteJavaDoc(psiFileContext, psiClassWrapper.getPsiClass());
            for (PsiMethodWrapper psiMethodWrapper : psiClassWrapper.getMethodList()) {
                paddingWriteJavaDoc(psiFileContext, psiMethodWrapper.getPsiMethod());
            }
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                paddingWriteJavaDoc(psiFileContext, psiFieldWrapper.getPsiField());
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
            paddingWriteJavaDoc(writeContext.getPsiFileContext(), psiClassWrapper.getPsiClass());
            return;
        }

        if (selectWrapper.getMethod() != null) {
            paddingWriteJavaDoc(writeContext.getPsiFileContext(), selectWrapper.getMethod().getPsiMethod());
            return;
        }

        if (selectWrapper.getField() != null) {
            paddingWriteJavaDoc(writeContext.getPsiFileContext(), selectWrapper.getField().getPsiField());
        }
    }

}
