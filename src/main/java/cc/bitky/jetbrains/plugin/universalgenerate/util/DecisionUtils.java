package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper.ClassRoleEnum;
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


    /**
     * 类是否为controller
     *
     * @param psiClass 类元素
     * @return boolean
     */
    public static ClassRoleEnum calcClassRole(PsiClass psiClass) {

        if (isController(psiClass)) {
            return ClassRoleEnum.CONTROLLER;
        }

        PsiAnnotation componentAnnotation = AnnotationUtil.findAnnotationInHierarchy(psiClass, Set.of("org.springframework.stereotype.Component"));
        if (componentAnnotation != null) {
            return ClassRoleEnum.SERVICE;
        }

        return ClassRoleEnum.POJO;
    }

    private static boolean isController(PsiClass psiClass) {
        String className = psiClass.getName();
        if (StringUtils.endsWith(className, "Controller")) {
            return true;
        }

        PsiAnnotation[] psiAnnotations = psiClass.getAnnotations();
        for (PsiAnnotation psiAnnotation : psiAnnotations) {
            String controllerAnnotation = "org.springframework.stereotype.Controller";
            String restControllerAnnotation = "org.springframework.web.bind.annotation.RestController";
            if (controllerAnnotation.equals(psiAnnotation.getQualifiedName())
                    || restControllerAnnotation.equals(psiAnnotation.getQualifiedName())) {
                return true;
            }
        }
        return false;
    }

}
