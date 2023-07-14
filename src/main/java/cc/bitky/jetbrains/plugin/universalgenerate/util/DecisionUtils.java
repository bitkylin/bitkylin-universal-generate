package cc.bitky.jetbrains.plugin.universalgenerate.util;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;

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
        PsiAnnotation[] psiAnnotations = psiClass.getModifierList().getAnnotations();
        for (PsiAnnotation psiAnnotation : psiAnnotations) {
            String controllerAnnotation = "org.springframework.stereotype.Controller";
            String restControllerAnnotation = "org.springframework.web.bind.annotation.RestController";
            if (controllerAnnotation.equals(psiAnnotation.getQualifiedName())
                    || restControllerAnnotation.equals(psiAnnotation.getQualifiedName())) {
                // controller
                return true;
            }
        }
        return false;
    }

}
