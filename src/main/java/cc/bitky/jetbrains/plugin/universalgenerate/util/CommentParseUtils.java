package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.constants.ModifierAnnotationEnum;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bitkylin
 */
public final class CommentParseUtils {

    private CommentParseUtils() {
    }


    /**
     * 查询注解中的属性值
     *
     * @param annotationEnum       注解
     * @param psiModifierListOwner 当前写入对象
     */
    public static List<String> parseAnnotationTextList(ModifierAnnotationEnum annotationEnum,
                                                       PsiModifierListOwner psiModifierListOwner) {
        String annotationText = parseAnnotationText(annotationEnum, psiModifierListOwner);
        return Arrays.stream(StringUtils.split(annotationText, '\n'))
                .filter(StringUtils::isNotBlank)
                .map(StringUtils::trim)
                .collect(Collectors.toList());
    }

    /**
     * 查询注解中的属性值
     *
     * @param annotationEnum       注解
     * @param psiModifierListOwner 当前写入对象
     */
    public static String parseAnnotationText(ModifierAnnotationEnum annotationEnum,
                                             PsiModifierListOwner psiModifierListOwner) {
        PsiModifierList modifierList = psiModifierListOwner.getModifierList();
        Preconditions.checkNotNull(modifierList);
        PsiAnnotation psiAnnotation = modifierList.findAnnotation(annotationEnum.getQualifiedName());
        if (psiAnnotation == null) {
            return StringUtils.EMPTY;
        }
        PsiAnnotationMemberValue annotationMemberValue = psiAnnotation.findAttributeValue(annotationEnum.getPrimaryAttributeName());
        if (annotationMemberValue == null) {
            return StringUtils.EMPTY;
        }
        return annotationMemberValue.getText();
    }

    /**
     * 获取注解说明  不写/@desc/@describe/@description等 @*
     */
    public static String beautifyCommentFromJavaDoc(PsiDocComment psiDocComment) {
        List<String> commentList = parseCommentListFromJavaDoc(psiDocComment);
        if (CollectionUtils.size(commentList) < 1) {
            return StringUtils.EMPTY;
        }
        if (CollectionUtils.size(commentList) == 1) {
            return commentList.get(0);
        }

        return StringUtils.join(commentList, "\"\n + \"\\n\" + \"");
    }

    /**
     * 获取注解说明  不写/@desc/@describe/@description等 @*
     */
    public static List<String> parseCommentListFromJavaDoc(PsiDocComment psiDocComment) {
        if (psiDocComment == null) {
            return Lists.newArrayList();
        }
        List<String> returnList = Lists.newArrayList();
        PsiElement[] psiElements = psiDocComment.getDescriptionElements();
        for (PsiElement psiElement : psiElements) {
            String text = psiElement.getText();
            if (StringUtils.startsWith(text, "@")) {
                continue;
            }
            if (StringUtils.isBlank(text)) {
                continue;
            }
            if (StringUtils.startsWith(text, "<") && StringUtils.endsWith(text, ">")) {
                continue;
            }
            returnList.add(StringUtils.trim(text));
        }
        return returnList;
    }


}
