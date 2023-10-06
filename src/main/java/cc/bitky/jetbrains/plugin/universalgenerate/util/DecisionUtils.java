package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.config.UniversalGenerateConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.DecisionAnnotationEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.*;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper.ClassRoleEnum;
import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author bitkylin
 */
@Slf4j
public final class DecisionUtils {

    private DecisionUtils() {
    }

    /**
     * 不支持枚举类
     */
    public static boolean psiClassDisabled(PsiClass psiClass) {
        if (psiClass.isEnum()) {
            return true;
        }
        return false;
    }

    public static SelectWrapper parseSelectWrapper(Project project, Editor editor, PsiElement psiElement) {
        SelectWrapper selectWrapper = new SelectWrapper();
        if (!(psiElement instanceof PsiIdentifier psiIdentifier)) {
            return selectWrapper;
        }
        PsiElement parent = psiIdentifier.getParent();
        int caretOffset = editor.getCaretModel().getOffset();
        int currentOffset = psiElement.getTextOffset();
        int nextOffset = psiElement.getNextSibling().getTextOffset();

        // 如果光标指向元素边缘，则判定为未选中元素
        if (caretOffset == currentOffset || caretOffset == nextOffset) {
            return selectWrapper;
        }

        if (parent instanceof PsiClass psiClass) {
            if (DecisionUtils.psiClassDisabled(psiClass)) {
                return selectWrapper;
            }
            selectWrapper.setSelected(true);
            selectWrapper.setClz(psiClass);
            return selectWrapper;
        }

        if (parent.getParent() instanceof PsiClass psiClass) {
            if (DecisionUtils.psiClassDisabled(psiClass)) {
                return selectWrapper;
            }

            if (parent instanceof PsiField psiField) {
                selectWrapper.setSelected(true);
                PsiFieldWrapper psiFieldWrapper = new PsiFieldWrapper();
                psiFieldWrapper.setPsiField(psiField);
                selectWrapper.setField(psiFieldWrapper);
                return selectWrapper;
            }

            if (parent instanceof PsiMethod psiMethod) {
                selectWrapper.setSelected(true);
                PsiMethodWrapper psiMethodWrapper = new PsiMethodWrapper();
                psiMethodWrapper.setPsiMethod(psiMethod);
                selectWrapper.setMethod(psiMethodWrapper);
                return selectWrapper;
            }
        }

        return selectWrapper;
    }

    public static void assembleRoleAndLocation(PsiClassWrapper psiClassWrapper, WriteContext.PsiFileContext psiFileContext) {
        ClassRoleEnum classRole = DecisionUtils.calcClassRole(psiClassWrapper.getPsiClass());
        psiClassWrapper.setClassRole(classRole);
        psiClassWrapper.setClassLocation(DecisionUtils.calcClassLocation(psiFileContext, classRole));
    }

    /**
     * 类是否为controller
     *
     * @param psiClass 类元素
     * @return boolean
     */
    private static ClassRoleEnum calcClassRole(PsiClass psiClass) {

        if (isController(psiClass)) {
            return ClassRoleEnum.CONTROLLER;
        }

        PsiAnnotation componentAnnotation = AnnotationUtil.findAnnotationInHierarchy(psiClass, Set.of("org.springframework.stereotype.Component"));
        if (componentAnnotation != null) {
            return ClassRoleEnum.SERVICE;
        }

        if (isPojo(psiClass)) {
            return ClassRoleEnum.POJO;
        }

        return null;
    }

    private static PsiClassWrapper.ClassLocationEnum calcClassLocation(WriteContext.PsiFileContext psiFileContext, ClassRoleEnum classRole) {
        if (classRole == ClassRoleEnum.CONTROLLER) {
            return PsiClassWrapper.ClassLocationEnum.INTERFACE_ENTRANCE;
        }

        String className = psiFileContext.getPsiClass().getName();
        if (UniversalGenerateConfig.entranceClassNameSuffixList().stream().anyMatch(suffix -> StringUtils.endsWith(className, suffix))) {
            return PsiClassWrapper.ClassLocationEnum.INTERFACE_ENTRANCE;
        }
        return PsiClassWrapper.ClassLocationEnum.SERVICE_ENTRANCE;
    }

    private static boolean isController(PsiClass psiClass) {
        String className = psiClass.getName();
        if (ClassNameUtils.isControllerSuffix(className)) {
            return true;
        }

        return matchAnnotation(psiClass, List.of(DecisionAnnotationEnum.CONTROLLER, DecisionAnnotationEnum.REST_CONTROLLER));
    }

    /**
     * 当前仅通过Lombok注解判断是否为POJO
     */
    private static boolean isPojo(PsiClass psiClass) {
        return matchAnnotation(psiClass, List.of(
                DecisionAnnotationEnum.DATA,
                DecisionAnnotationEnum.GETTER,
                DecisionAnnotationEnum.SETTER,
                DecisionAnnotationEnum.VALUE)
        );
    }

    private static boolean matchAnnotation(PsiModifierListOwner psiModifierListOwner, List<DecisionAnnotationEnum> decisionAnnotationEnum) {
        Set<String> annotationNames = decisionAnnotationEnum.stream().map(DecisionAnnotationEnum::getQualifiedName).collect(Collectors.toSet());
        PsiAnnotation annotation = AnnotationUtil.findAnnotationInHierarchy(psiModifierListOwner, annotationNames);
        return annotation != null;

    }

}
