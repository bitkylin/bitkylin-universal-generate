package cc.bitky.jetbrains.plugin.universalgenerate.pojo;

import com.intellij.psi.PsiField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bitkylin
 */
@Getter
@Setter
public class PsiFieldWrapper {

    private PsiField psiField;

    private AnnotationTag annotationTag;

    @Getter
    @Setter
    public static class AnnotationTag {

        private boolean exist;

        private int value;

    }

}
