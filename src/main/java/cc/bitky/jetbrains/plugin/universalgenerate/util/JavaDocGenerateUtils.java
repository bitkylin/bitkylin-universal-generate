package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiDocTagWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.google.common.collect.Lists;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.CommentParseUtils.parseCommentFromElementName;

/**
 * @author bitkylin
 */
public final class JavaDocGenerateUtils {

    private JavaDocGenerateUtils() {
    }

    public static void reGenerateWriteFieldJavaDocFromProjectElementName(WriteContext.PsiFileContext psiFileContext, PsiField psiField) {
        GenerateUtils.deleteJavaDoc(psiField.getDocComment());
        List<String> commentList = parseCommentFromElementName(psiFileContext, psiField.getName());

        PsiDocComment psiDocCommentNew = psiFileContext.getElementFactory()
                .createDocCommentFromText(beautifyJavaDocFromComment(
                        Lists.newArrayList(),
                        commentList,
                        Lists.newArrayList())
                );
        psiField.addBefore(psiDocCommentNew, psiField.getFirstChild());
    }

    public static void reGenerateWriteMethodJavaDocFromProjectElementName(WriteContext.PsiFileContext psiFileContext, PsiMethod psiMethod) {
        List<String> rawCommentList = CommentParseUtils.parseCommentListFromWholeJavaDoc(psiMethod.getDocComment());
        List<PsiDocTagWrapper> existedTagList = CommentParseUtils.parseTagListFromWholeJavaDoc(psiMethod.getDocComment());
        List<PsiDocTagWrapper> mergedTagList = searchAndReplaceParameterTagFromProjectElementName(psiFileContext, psiMethod, existedTagList);

        GenerateUtils.deleteJavaDoc(psiMethod.getDocComment());

        PsiDocComment psiDocCommentNew = psiFileContext.getElementFactory()
                .createDocCommentFromText(beautifyJavaDocFromComment(
                        Lists.newArrayList(),
                        rawCommentList,
                        mergedTagList)
                );
        psiMethod.addBefore(psiDocCommentNew, psiMethod.getFirstChild());
    }

    public static void populateWriteFieldJavaDocFromProjectElementName(WriteContext.PsiFileContext psiFileContext, PsiField psiField) {
        List<String> rawCommentList = CommentParseUtils.parseCommentListFromWholeJavaDoc(psiField.getDocComment());
        GenerateUtils.deleteJavaDoc(psiField.getDocComment());
        List<String> newCommentList = parseCommentFromElementName(psiFileContext, psiField.getName());

        List<String> commentList = Lists.newArrayList();
        commentList.addAll(newCommentList);
        commentList.addAll(rawCommentList);

        PsiDocComment psiDocCommentNew = psiFileContext.getElementFactory()
                .createDocCommentFromText(beautifyJavaDocFromComment(
                        Lists.newArrayList(),
                        commentList,
                        Lists.newArrayList())
                );
        psiField.addBefore(psiDocCommentNew, psiField.getFirstChild());
    }

    public static void populateWriteMethodJavaDocFromProjectElementName(WriteContext.PsiFileContext psiFileContext, PsiMethod psiMethod) {
        List<String> rawCommentList = CommentParseUtils.parseCommentListFromWholeJavaDoc(psiMethod.getDocComment());
        List<PsiDocTagWrapper> existedTagList = CommentParseUtils.parseTagListFromWholeJavaDoc(psiMethod.getDocComment());
        List<PsiDocTagWrapper> mergedTagList = searchAndRetainParameterTagFromProjectElementName(psiFileContext, psiMethod, existedTagList);

        GenerateUtils.deleteJavaDoc(psiMethod.getDocComment());

        List<String> commentList = Lists.newArrayList();
        commentList.addAll(rawCommentList);

        PsiDocComment psiDocCommentNew = psiFileContext.getElementFactory()
                .createDocCommentFromText(beautifyJavaDocFromComment(
                        Lists.newArrayList(),
                        commentList,
                        mergedTagList)
                );
        psiMethod.addBefore(psiDocCommentNew, psiMethod.getFirstChild());
    }

    public static <T extends PsiModifierListOwner & PsiDocCommentOwner> void reGenerateWriteJavaDocFromAnnotation(WriteContext.PsiFileContext psiFileContext, T psiOwner) {
        List<String> annotationList = CommentParseUtils.parseAnnotationText(psiOwner);
        GenerateUtils.deleteSwaggerAnnotation(psiOwner);
        if (CollectionUtils.isEmpty(annotationList)) {
            return;
        }

        List<String> commentList = CommentParseUtils.parseCommentListFromWholeJavaDoc(psiOwner.getDocComment());
        List<PsiDocTagWrapper> tagList = CommentParseUtils.parseTagListFromWholeJavaDoc(psiOwner.getDocComment());

        GenerateUtils.deleteJavaDoc(psiOwner.getDocComment());

        PsiDocComment psiDocCommentNew = psiFileContext.getElementFactory()
                .createDocCommentFromText(beautifyJavaDocFromComment(annotationList, commentList, tagList));
        psiOwner.addBefore(psiDocCommentNew, psiOwner.getFirstChild());
    }

    public static <T extends PsiModifierListOwner & PsiDocCommentOwner> void populateWriteJavaDocFromAnnotation(WriteContext.PsiFileContext psiFileContext, T psiOwner) {
        List<String> annotationList = CommentParseUtils.parseAnnotationText(psiOwner);
        GenerateUtils.deleteSwaggerAnnotation(psiOwner);
        if (CollectionUtils.isEmpty(annotationList)) {
            return;
        }

        List<String> commentList = CommentParseUtils.parseCommentListFromWholeJavaDoc(psiOwner.getDocComment());
        List<PsiDocTagWrapper> tagList = CommentParseUtils.parseTagListFromWholeJavaDoc(psiOwner.getDocComment());


        if (CollectionUtils.isNotEmpty(commentList)) {
            return;
        }

        GenerateUtils.deleteJavaDoc(psiOwner.getDocComment());

        PsiDocComment psiDocCommentNew = psiFileContext.getElementFactory()
                .createDocCommentFromText(beautifyJavaDocFromComment(annotationList, commentList, tagList));
        psiOwner.addBefore(psiDocCommentNew, psiOwner.getFirstChild());
    }

    private static List<PsiDocTagWrapper> searchAndRetainParameterTagFromProjectElementName(WriteContext.PsiFileContext psiFileContext,
                                                                                            PsiMethod psiMethod,
                                                                                            List<PsiDocTagWrapper> existedTagList) {
        List<PsiDocTagWrapper> returnList = Lists.newArrayList();
        Map<String, List<PsiDocTagWrapper>> existedGroupMap = existedTagList.stream()
                .collect(Collectors.groupingBy(PsiDocTagWrapper::getElementName));

        PsiParameter[] parameterArray = psiMethod.getParameterList().getParameters();
        if (ArrayUtils.isEmpty(parameterArray)) {
            return Lists.newArrayList();
        }

        for (PsiParameter psiParameter : parameterArray) {
            String elementName = psiParameter.getName();
            if (existedGroupMap.containsKey(elementName)) {
                if (existedGroupMap.get(elementName).stream().anyMatch(PsiDocTagWrapper::paramIsValid)) {
                    returnList.addAll(existedGroupMap.remove(elementName));
                    continue;
                }
            }

            returnList.addAll(parsePsiDocTagByElementName(psiFileContext, psiParameter));
        }

        returnList.addAll(existedGroupMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));
        return returnList;
    }

    private static List<PsiDocTagWrapper> searchAndReplaceParameterTagFromProjectElementName(WriteContext.PsiFileContext psiFileContext,
                                                                                             PsiMethod psiMethod,
                                                                                             List<PsiDocTagWrapper> existedTagList) {
        List<PsiDocTagWrapper> returnList = Lists.newArrayList();
        Map<String, List<PsiDocTagWrapper>> existedGroupMap = existedTagList.stream()
                .collect(Collectors.groupingBy(PsiDocTagWrapper::getElementName));

        PsiParameter[] parameterArray = psiMethod.getParameterList().getParameters();
        if (ArrayUtils.isEmpty(parameterArray)) {
            return Lists.newArrayList();
        }

        for (PsiParameter psiParameter : parameterArray) {
            String elementName = psiParameter.getName();
            existedGroupMap.remove(elementName);
            returnList.addAll(parsePsiDocTagByElementName(psiFileContext, psiParameter));
        }

        returnList.addAll(existedGroupMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));
        return returnList;
    }

    @NotNull
    private static List<PsiDocTagWrapper> parsePsiDocTagByElementName(WriteContext.PsiFileContext psiFileContext, PsiParameter psiParameter) {
        List<String> elementDescList = parseCommentFromElementName(psiFileContext, psiParameter.getName());
        List<PsiDocTagWrapper> tagList = Lists.newArrayList();

        for (String elementDesc : elementDescList) {
            PsiDocTagWrapper psiDocTagWrapper = new PsiDocTagWrapper();
            psiDocTagWrapper.setElementName(psiParameter.getName());
            psiDocTagWrapper.setElementDesc(elementDesc);
            psiDocTagWrapper.setFullComment("@param " + psiParameter.getName() + " " + elementDesc);
            tagList.add(psiDocTagWrapper);
        }
        return tagList;
    }

    private static String beautifyJavaDocFromComment(List<String> annotationTextList,
                                                     List<String> commentList,
                                                     List<PsiDocTagWrapper> tagList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/**\n");
        Set<String> javaDocCommentSet = new LinkedHashSet<>();
        javaDocCommentSet.addAll(annotationTextList);
        javaDocCommentSet.addAll(commentList);

        Set<String> tagListSet = new LinkedHashSet<>(ListUtils.map(tagList, PsiDocTagWrapper::fetchFullComment));

        if (CollectionUtils.isNotEmpty(javaDocCommentSet) && CollectionUtils.isNotEmpty(tagListSet)) {
            for (String comment : javaDocCommentSet) {
                stringBuilder.append(beautifyJavaDocComment(comment));
            }
            stringBuilder.append(beautifyJavaDocBlackLine());
            for (String tag : tagListSet) {
                stringBuilder.append(beautifyJavaDocTag(tag));
            }

        } else if (CollectionUtils.isEmpty(javaDocCommentSet) && CollectionUtils.isEmpty(tagListSet)) {
            stringBuilder.append(beautifyJavaDocBlackLine());

        } else {
            for (String comment : javaDocCommentSet) {
                stringBuilder.append(beautifyJavaDocComment(comment));
            }
            for (String tag : tagListSet) {
                stringBuilder.append(beautifyJavaDocTag(tag));
            }
        }
        stringBuilder.append(" */");
        return stringBuilder.toString();
    }

    private static String beautifyJavaDocComment(String commentItem) {
        return "* " + commentItem + "\n";
    }

    private static String beautifyJavaDocBlackLine() {
        return "* \n";
    }

    private static String beautifyJavaDocTag(String tagItem) {
        return "* " + tagItem + "\n";
    }

}
