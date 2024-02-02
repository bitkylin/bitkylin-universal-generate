package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.constants.ModifierAnnotationEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.ElementNameSuffixInfo;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiDocTagWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.javadoc.PsiDocTagValue;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author bitkylin
 */
public final class CommentParseUtils {

    private CommentParseUtils() {
    }

    public static List<String> parseCommentFromElementName(WriteContext.PsiFileContext psiFileContext, String elementName) {

        // 对原始元素名查询注释
        List<String> commentList = CommentQueryUtils.queryFieldJavaDocByNameDirectly(psiFileContext, elementName);
        if (CollectionUtils.isNotEmpty(commentList)) {
            return createElementName(commentList, null);
        }

        // 移除元素名通用后缀
        ElementNameSuffixInfo elementNameSuffixInfoAdded = CommentQueryUtils.removeFuzzySuffix(elementName);
        if (elementNameSuffixInfoAdded != null) {
            elementName = elementNameSuffixInfoAdded.getResolvedElementName();
        }

        // 对纯净元素名查询注释
        commentList = CommentQueryUtils.queryFieldJavaDocByNameDirectly(psiFileContext, elementName);
        commentList = ListUtils.distinctMap(commentList, CommentQueryUtils::removeElementCommentSuffix);
        if (CollectionUtils.isNotEmpty(commentList)) {
            return createElementName(commentList, elementNameSuffixInfoAdded);
        }

        // 对纯净元素名模糊匹配注释
        commentList = CommentQueryUtils.queryFieldJavaDocByNameFuzzy(psiFileContext, elementName);
        if (CollectionUtils.isNotEmpty(commentList)) {
            return createElementName(commentList, elementNameSuffixInfoAdded);
        }

        return Lists.newArrayList();
    }

    private static List<String> createElementName(List<String> rawCommentNameList, ElementNameSuffixInfo elementNameSuffixInfoAdded) {
        List<String> list = Lists.newArrayList();
        for (String elementName : rawCommentNameList) {
            if (elementNameSuffixInfoAdded != null && StringUtils.isNotBlank(elementNameSuffixInfoAdded.getSuffixName())) {
                elementName = elementName + " - " + elementNameSuffixInfoAdded.getSuffixName();
            }
            list.add(elementName);
        }
        return list;
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

        commentList = commentList.stream()
                .map(StringUtils::trim)
                .map(str -> StringUtils.remove(str, "\""))
                .toList();

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
            returnList.add(StringUtils.trimToEmpty(text));
        }
        return returnList;
    }

    /**
     * 获取注解说明-从完整的JavaDoc
     */
    public static List<PsiDocTagWrapper> parseTagListFromWholeJavaDoc(PsiDocComment psiDocComment) {
        List<PsiDocTagWrapper> returnList = Lists.newArrayList();
        if (psiDocComment == null) {
            return returnList;
        }
        PsiDocTag[] psiDocTags = psiDocComment.getTags();
        for (PsiDocTag psiDocTag : psiDocTags) {
            PsiDocTagWrapper psiDocTagWrapper = classifyTextFromJavaDocTag(psiDocTag);
            if (psiDocTagWrapper == null) {
                continue;
            }
            returnList.add(psiDocTagWrapper);
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

    public static PsiDocTagWrapper classifyTextFromJavaDocTag(PsiDocTag psiDocTag) {
        String javaDocTagText = psiDocTag.getText();
        if (StringUtils.isBlank(javaDocTagText)) {
            return null;
        }
        String fullComment = Arrays.stream(StringUtils.split(javaDocTagText, '\n'))
                .map(StringUtils::trim)
                .filter(CommentParseUtils::removeSpecialJavaDocTagItem)
                .findFirst()
                .orElse(StringUtils.EMPTY);
        if (StringUtils.isBlank(fullComment)) {
            return null;
        }
        PsiDocTagWrapper psiDocTagWrapper = new PsiDocTagWrapper();
        psiDocTagWrapper.setElementName(parseJavaDocTagElementName(psiDocTag));
        psiDocTagWrapper.setElementDesc(parseJavaDocTagElementDesc(psiDocTag, psiDocTagWrapper.getElementName()));
        psiDocTagWrapper.setFullComment(fullComment);
        return psiDocTagWrapper;
    }

    private static String parseJavaDocTagElementName(PsiDocTag psiDocTag) {
        PsiDocTagValue psiDocTagValue = psiDocTag.getValueElement();
        if (psiDocTagValue == null) {
            return StringUtils.EMPTY;
        }
        return psiDocTagValue.getText();
    }

    private static String parseJavaDocTagElementDesc(PsiDocTag psiDocTag, String elementName) {
        PsiElement[] psiElements = psiDocTag.getDataElements();
        if (ArrayUtils.isEmpty(psiElements)) {
            return StringUtils.EMPTY;
        }
        if (psiElements.length < 2) {
            return StringUtils.EMPTY;
        }

        String elementDesc = psiElements[1].getText();
        if (StringUtils.equals(elementDesc, elementName)) {
            return StringUtils.EMPTY;
        }
        return elementDesc;
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
        if (StringUtils.equals("@param", javaDocTagItem)) {
            return false;
        }
        if (StringUtils.equals("@return", javaDocTagItem)) {
            return false;
        }
        return true;
    }

}
