package cc.bitky.jetbrains.plugin.universalgenerate.util;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import org.apache.commons.lang3.StringUtils;

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
    public static boolean isController(PsiClass psiClass) {
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
