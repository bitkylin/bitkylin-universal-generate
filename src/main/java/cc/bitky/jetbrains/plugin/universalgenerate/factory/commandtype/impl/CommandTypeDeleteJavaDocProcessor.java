package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base.AbstractCommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiMethodWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.ExceptionUtils;
import cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils;

/**
 * 删除Tag注解
 *
 * @author bitkylin
 */
public class CommandTypeDeleteJavaDocProcessor extends AbstractCommandTypeProcessor implements ICommandTypeProcessor {

    private final WriteContext writeContext;

    public CommandTypeDeleteJavaDocProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    public void doWriteFile() {
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            GenerateUtils.deleteJavaDoc(psiClassWrapper.getPsiClass().getDocComment());
            for (PsiMethodWrapper psiMethodWrapper : psiClassWrapper.getMethodList()) {
                GenerateUtils.deleteJavaDoc(psiMethodWrapper.getPsiMethod().getDocComment());
            }
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                GenerateUtils.deleteJavaDoc(psiFieldWrapper.getPsiField().getDocComment());
            }
        }
    }

    @Override
    public void doWriteElement() {
        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        if (!selectWrapper.isSelected()) {
            throw ExceptionUtils.newException(ExceptionMsgEnum.ELEMENT_NOT_SELECT);
        }

        if (selectWrapper.getClz() != null) {
            GenerateUtils.deleteJavaDoc(selectWrapper.getClz().getDocComment());

            return;
        }

        if (selectWrapper.getMethod() != null) {
            GenerateUtils.deleteJavaDoc(selectWrapper.getMethod().getPsiMethod().getDocComment());
            return;
        }

        if (selectWrapper.getField() != null) {
            GenerateUtils.deleteJavaDoc(selectWrapper.getField().getPsiField().getDocComment());
        }
    }


}
