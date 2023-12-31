package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base.AbstractCommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.SelectWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.ExceptionUtils;
import cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils;

/**
 * 删除Tag注解
 *
 * @author bitkylin
 */
public class CommandTypeDeleteAnnotationTagProcessor extends AbstractCommandTypeProcessor implements ICommandTypeProcessor {

    private final WriteContext writeContext;

    public CommandTypeDeleteAnnotationTagProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    @Override
    public void doWriteFile() {
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                GenerateUtils.deleteTagAnnotation(psiFieldWrapper.getPsiField());
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
            GenerateUtils.deleteTagAnnotation(selectWrapper.getField().getPsiField());
        }
    }

}
