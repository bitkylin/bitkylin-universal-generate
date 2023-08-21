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
public class CommandTypeDeleteAnnotationSwaggerProcessor extends AbstractCommandTypeProcessor implements ICommandTypeProcessor {

    private final WriteContext writeContext;

    public CommandTypeDeleteAnnotationSwaggerProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    public void doWriteFile() {
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            GenerateUtils.deleteSwaggerAnnotation(psiClassWrapper.getPsiClass());
            for (PsiMethodWrapper psiMethodWrapper : psiClassWrapper.getMethodList()) {
                GenerateUtils.deleteSwaggerAnnotation(psiMethodWrapper.getPsiMethod());
            }
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                GenerateUtils.deleteSwaggerAnnotation(psiFieldWrapper.getPsiField());
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
            GenerateUtils.deleteSwaggerAnnotation(selectWrapper.getClz());

            return;
        }

        if (selectWrapper.getMethod() != null) {
            GenerateUtils.deleteSwaggerAnnotation(selectWrapper.getMethod().getPsiMethod());
            return;
        }

        if (selectWrapper.getField() != null) {
            GenerateUtils.deleteSwaggerAnnotation(selectWrapper.getField().getPsiField());
        }
    }


}
