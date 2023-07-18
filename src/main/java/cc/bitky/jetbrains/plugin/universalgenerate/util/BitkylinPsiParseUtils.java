package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ModifierAnnotationEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiMethodWrapper;
import com.google.common.base.Preconditions;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;

import java.util.Objects;

/**
 * @author bitkylin
 */
public final class BitkylinPsiParseUtils {

    private BitkylinPsiParseUtils() {
    }

    public static PsiFieldWrapper parsePsiField(PsiField psiField) {
        PsiFieldWrapper psiFieldWrapper = new PsiFieldWrapper();
        psiFieldWrapper.setPsiField(psiField);
        psiFieldWrapper.setAnnotationTag(parseAnnotationTag(psiField));
        return psiFieldWrapper;
    }

    public static PsiMethodWrapper parsePsiMethod(PsiMethod psiMethod) {
        PsiMethodWrapper psiMethodWrapper = new PsiMethodWrapper();
        psiMethodWrapper.setPsiMethod(psiMethod);
        return psiMethodWrapper;
    }

    private static PsiFieldWrapper.AnnotationTag parseAnnotationTag(PsiField psiField) {
        PsiFieldWrapper.AnnotationTag annotationTag = new PsiFieldWrapper.AnnotationTag();
        PsiAnnotation existAnnotation = Preconditions.checkNotNull(psiField.getModifierList())
                .findAnnotation(ModifierAnnotationEnum.TAG.getQualifiedName());
        annotationTag.setExist(false);
        if (Objects.isNull(existAnnotation)) {
            return annotationTag;
        }
        PsiAnnotationMemberValue annotationMemberValue = existAnnotation.findAttributeValue("value");
        NotificationUtils.checks(psiField.getProject(), ExceptionMsgEnum.ANNOTATION_TAG_ERROR, Objects.nonNull(annotationMemberValue));
        assert Objects.nonNull(annotationMemberValue);

        annotationTag.setExist(true);
        annotationTag.setValue(Integer.parseInt(annotationMemberValue.getText()));
        return annotationTag;
    }
}
