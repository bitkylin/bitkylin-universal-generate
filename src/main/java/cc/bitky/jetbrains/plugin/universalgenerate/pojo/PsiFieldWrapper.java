package cc.bitky.jetbrains.plugin.universalgenerate.pojo;

import com.intellij.psi.PsiField;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * @author bitkylin
 */
@Getter
@Setter
public class PsiFieldWrapper {

    private PsiField psiField;

    private AnnotationTag annotationTag;


    public Optional<Integer> fetchTagValue() {
        if (annotationTag != null && annotationTag.isExist()) {
            return Optional.of(annotationTag.getValue());
        }
        return Optional.empty();
    }

    @Getter
    @Setter
    public static class AnnotationTag {

        private boolean exist;

        private int value;

    }

}
