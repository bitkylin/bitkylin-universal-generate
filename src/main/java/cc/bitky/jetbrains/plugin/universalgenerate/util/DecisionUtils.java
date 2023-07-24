package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.config.UniversalGenerateConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.QualifiedNameConstants;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper.ClassRoleEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * @author bitkylin
 */
public final class DecisionUtils {

    private DecisionUtils() {
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

        return ClassRoleEnum.POJO;
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

        PsiAnnotation[] psiAnnotations = psiClass.getAnnotations();
        for (PsiAnnotation psiAnnotation : psiAnnotations) {
            String controllerAnnotation = QualifiedNameConstants.CONTROLLER;
            String restControllerAnnotation = QualifiedNameConstants.REST_CONTROLLER;
            if (controllerAnnotation.equals(psiAnnotation.getQualifiedName())
                    || restControllerAnnotation.equals(psiAnnotation.getQualifiedName())) {
                return true;
            }
        }
        return false;
    }

}
