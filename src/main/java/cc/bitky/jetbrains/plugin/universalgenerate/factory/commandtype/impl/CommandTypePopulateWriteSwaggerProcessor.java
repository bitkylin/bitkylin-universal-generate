package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base.AbstractCommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.*;
import cc.bitky.jetbrains.plugin.universalgenerate.util.CommentParseUtils;
import cc.bitky.jetbrains.plugin.universalgenerate.util.ExceptionUtils;
import cc.bitky.jetbrains.plugin.universalgenerate.util.ModifierAnnotationUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils.writeAnnotationOriginalPrimary;

/**
 * Swagger注解填充处理器
 *
 * @author bitkylin
 */
public class CommandTypePopulateWriteSwaggerProcessor extends AbstractCommandTypeProcessor implements ICommandTypeProcessor {

    private final WriteContext writeContext;

    public CommandTypePopulateWriteSwaggerProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    @Override
    public void doWriteFile() {
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {

            if (psiClassWrapper.getClassRole() == PsiClassWrapper.ClassRoleEnum.CONTROLLER) {
                generateClassControllerSwaggerAnnotation(writeContext.getPsiFileContext(), psiClassWrapper);
                for (PsiMethodWrapper psiMethodWrapper : psiClassWrapper.getMethodList()) {
                    generateMethodSwaggerAnnotation(writeContext.getPsiFileContext(), psiMethodWrapper);
                }
                continue;
            }

            if (psiClassWrapper.getClassRole() == PsiClassWrapper.ClassRoleEnum.POJO) {
                generateClassPojoSwaggerAnnotation(writeContext.getPsiFileContext(), psiClassWrapper);
                for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                    generateFieldSwaggerAnnotation(writeContext.getPsiFileContext(), psiFieldWrapper);
                }
            }
        }
    }

    @Override
    public void doWriteElement() {
        SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        PsiClassWrapper psiClassWrapper = selectWrapper.getSelectedPsiClassWrapper();
        if (!selectWrapper.isSelected()) {
            throw ExceptionUtils.newException(ExceptionMsgEnum.ELEMENT_NOT_SELECT);
        }

        if (selectWrapper.getClz() != null) {
            if (psiClassWrapper.getClassRole() == PsiClassWrapper.ClassRoleEnum.CONTROLLER) {
                generateClassControllerSwaggerAnnotation(writeContext.getPsiFileContext(), psiClassWrapper);
            } else if (psiClassWrapper.getClassRole() == PsiClassWrapper.ClassRoleEnum.POJO) {
                generateClassPojoSwaggerAnnotation(writeContext.getPsiFileContext(), psiClassWrapper);
            }
            return;
        }

        if (selectWrapper.getMethod() != null) {
            generateMethodSwaggerAnnotation(writeContext.getPsiFileContext(), selectWrapper.getMethod());
            return;
        }

        if (selectWrapper.getField() != null) {
            generateFieldSwaggerAnnotation(writeContext.getPsiFileContext(), selectWrapper.getField());
        }
    }

    /**
     * 生成类的Swagger注解
     */
    private static void generateClassPojoSwaggerAnnotation(WriteContext.PsiFileContext psiFileContext, PsiClassWrapper psiClassWrapper) {
        PsiClass psiClass = psiClassWrapper.getPsiClass();
        String commentDesc = CommentParseUtils.beautifyCommentFromJavaDoc(psiClass.getDocComment());
        writeAnnotationOriginalPrimary(psiFileContext, ModifierAnnotationUtils.createWrapperApiModel(commentDesc), psiClass);
    }

    /**
     * 生成类的Swagger注解
     */
    private static void generateClassControllerSwaggerAnnotation(WriteContext.PsiFileContext psiFileContext, PsiClassWrapper psiClassWrapper) {
        PsiClass psiClass = psiClassWrapper.getPsiClass();
        String commentDesc = CommentParseUtils.beautifyCommentFromJavaDoc(psiClass.getDocComment());
        writeAnnotationOriginalPrimary(psiFileContext, ModifierAnnotationUtils.createWrapperApi(commentDesc), psiClass);
    }

    /**
     * 生成属性的Swagger注解
     *
     * @param psiFieldWrapper 类属性元素
     */
    private static void generateFieldSwaggerAnnotation(WriteContext.PsiFileContext psiFileContext, PsiFieldWrapper psiFieldWrapper) {
        PsiField psiField = psiFieldWrapper.getPsiField();
        String commentDesc = CommentParseUtils.beautifyCommentFromJavaDoc(psiField.getDocComment());
        writeAnnotationOriginalPrimary(psiFileContext, ModifierAnnotationUtils.createWrapperApiModelProperty(commentDesc), psiField);
    }

    /**
     * 生成方法的Swagger注解
     *
     * @param psiMethodWrapper 类属性元素
     */
    private static void generateMethodSwaggerAnnotation(WriteContext.PsiFileContext psiFileContext, PsiMethodWrapper psiMethodWrapper) {
        PsiMethod psiMethod = psiMethodWrapper.getPsiMethod();
        String commentDesc = CommentParseUtils.beautifyCommentFromJavaDoc(psiMethod.getDocComment());
        writeAnnotationOriginalPrimary(psiFileContext, ModifierAnnotationUtils.createWrapperApiOperation(commentDesc), psiMethod);
    }

}
