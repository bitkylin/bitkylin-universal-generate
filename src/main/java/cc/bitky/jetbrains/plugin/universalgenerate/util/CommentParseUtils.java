package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.constants.ModifierAnnotationEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.ElementNameSuffixInfo;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author bitkylin
 */
public final class CommentParseUtils {

    private CommentParseUtils() {
    }

    public static List<String> parseCommentFromElementName(WriteContext.PsiFileContext psiFileContext, PsiField psiField) {
        String pureFieldName = psiField.getName();
        ElementNameSuffixInfo elementNameSuffixInfoAdded = CommentQueryUtils.removeFuzzySuffix(pureFieldName);

        if (elementNameSuffixInfoAdded != null) {
            pureFieldName = elementNameSuffixInfoAdded.getElementName();
        }

        List<String> commentList = CommentQueryUtils.queryFieldJavaDocByNameDirectly(psiFileContext, pureFieldName);

        if (CollectionUtils.isNotEmpty(commentList)) {
            return createElementName(commentList, elementNameSuffixInfoAdded);
        }

        commentList = CommentQueryUtils.queryFieldJavaDocByNameFuzzy(psiFileContext, pureFieldName);

        if (CollectionUtils.isNotEmpty(commentList)) {
            return createElementName(commentList, elementNameSuffixInfoAdded);
        }

        return org.apache.commons.compress.utils.Lists.newArrayList();
    }

    private static List<String> createElementName(List<String> list, ElementNameSuffixInfo elementNameSuffixInfoAdded) {
        Set<String> set = new LinkedHashSet<>();
        for (String elementName : list) {
            if (elementNameSuffixInfoAdded != null) {
                elementName = elementName + " - " + elementNameSuffixInfoAdded.getSuffixName();
            }
            set.add(elementName);
        }
        return new ArrayList<>(set);
    }

    /**
     * 查询注解中的属性值
     *
     * @param annotationEnum       注解
     * @param psiModifierListOwner 当前写入对象
     */
    public static List<String> parseAnnotationText(ModifierAnnotationEnum annotationEnum,
                                                   PsiModifierListOwner psiModifierListOwner) {
        PsiModifierList modifierList = psiModifierListOwner.getModifierList();
        Preconditions.checkNotNull(modifierList);
        PsiAnnotation psiAnnotation = modifierList.findAnnotation(annotationEnum.getQualifiedName());
        if (psiAnnotation == null) {
            return Lists.newArrayList();
        }

        for (String attributeName : annotationEnum.getAttributeNameList()) {
            PsiAnnotationMemberValue annotationMemberValue = psiAnnotation.findAttributeValue(attributeName);
            if (annotationMemberValue == null) {
                continue;
            }
            String annotationText = annotationMemberValue.getText();
            List<String> annotationTextList = classifyTextFromAnnotationText(annotationText);
            if (CollectionUtils.isNotEmpty(annotationTextList)) {
                return annotationTextList;
            }
        }
        return Lists.newArrayList();
    }

    /**
     * 获取注解说明  不写/@desc/@describe/@description等 @*
     */
    public static String beautifyCommentFromJavaDoc(PsiDocComment psiDocComment) {
        List<String> commentList = parseCommentListFromComment(psiDocComment);
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
    public static List<String> parseCommentListFromComment(PsiDocComment psiDocComment) {
        if (psiDocComment == null) {
            return Lists.newArrayList();
        }
        List<String> returnList = Lists.newArrayList();
        PsiElement[] psiElements = psiDocComment.getDescriptionElements();
        for (PsiElement psiElement : psiElements) {
            String text = StringUtils.trimToEmpty(psiElement.getText());
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

    /**
     * 获取注解说明-从完整的JavaDoc
     */
    public static List<String> parseCommentListFromWholeJavaDoc(PsiDocComment psiDocComment) {
        if (psiDocComment == null) {
            return Lists.newArrayList();
        }
        List<String> returnList = Lists.newArrayList();
        PsiElement[] psiElements = psiDocComment.getDescriptionElements();
        for (PsiElement psiElement : psiElements) {
            String text = psiElement.getText();
            if (StringUtils.isBlank(text)) {
                continue;
            }
            returnList.add(StringUtils.trim(text));
        }
        return returnList;
    }

    /**
     * 获取注解说明-从完整的JavaDoc
     */
    public static List<String> parseTagListFromWholeJavaDoc(PsiDocComment psiDocComment) {
        if (psiDocComment == null) {
            return Lists.newArrayList();
        }
        List<String> returnList = Lists.newArrayList();
        PsiDocTag[] psiDocTags = psiDocComment.getTags();
        for (PsiDocTag psiDocTag : psiDocTags) {
            String text = classifyTextFromJavaDocTag(psiDocTag.getText());
            if (StringUtils.isBlank(text)) {
                continue;
            }
            returnList.add(StringUtils.trim(text));
        }
        return returnList;
    }

    public static <T extends PsiModifierListOwner> List<String> parseAnnotationText(T psiElement) {
        LinkedHashSet<String> commentSet = new LinkedHashSet<>();
        commentSet.addAll(parseAnnotationText(ModifierAnnotationEnum.API, psiElement));
        commentSet.addAll(parseAnnotationText(ModifierAnnotationEnum.API_OPERATION, psiElement));
        commentSet.addAll(parseAnnotationText(ModifierAnnotationEnum.API_MODEL, psiElement));
        commentSet.addAll(parseAnnotationText(ModifierAnnotationEnum.API_MODEL_PROPERTY, psiElement));
        return Lists.newArrayList(commentSet);
    }

    public static List<String> classifyTextFromAnnotationText(String annotationText) {
        if (StringUtils.isBlank(annotationText)) {
            return Lists.newArrayList();
        }
        annotationText = StringUtils.trim(annotationText);
        if (StringUtils.startsWith(annotationText, "{") && StringUtils.endsWith(annotationText, "}")) {
            annotationText = StringUtils.substring(annotationText, 1, -1);
        }
        return Arrays.stream(StringUtils.split(annotationText, '\n'))
                .flatMap(item -> Arrays.stream(StringUtils.split(item, "\"")))
                .map(StringUtils::trim)
                .filter(CommentParseUtils::removeSpecialAnnotationTextItem)
                .toList();
    }

    public static String classifyTextFromJavaDocTag(String javaDocTag) {
        if (StringUtils.isBlank(javaDocTag)) {
            return StringUtils.EMPTY;
        }
        return Arrays.stream(StringUtils.split(javaDocTag, '\n'))
                .map(StringUtils::trim)
                .filter(CommentParseUtils::removeSpecialJavaDocTagItem)
                .findFirst()
                .orElse(StringUtils.EMPTY);
    }

    private static boolean removeSpecialAnnotationTextItem(String annotationTextItem) {
        if (StringUtils.isBlank(annotationTextItem)) {
            return false;
        }
        if (StringUtils.equals("\\n", annotationTextItem)) {
            return false;
        }
        if (StringUtils.equals("+", annotationTextItem)) {
            return false;
        }
        return true;
    }

    private static boolean removeSpecialJavaDocTagItem(String javaDocTagItem) {
        if (StringUtils.isBlank(javaDocTagItem)) {
            return false;
        }
        if (StringUtils.equals("*", javaDocTagItem)) {
            return false;
        }
        return true;
    }

}
