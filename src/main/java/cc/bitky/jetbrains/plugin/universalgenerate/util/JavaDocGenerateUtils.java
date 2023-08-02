package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.intellij.psi.PsiDocCommentOwner;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.javadoc.PsiDocComment;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.CommentParseUtils.parseCommentFromElementName;

/**
 * @author bitkylin
 */
public final class JavaDocGenerateUtils {

    private JavaDocGenerateUtils() {
    }

    public static void reGenerateWriteJavaDocFromProjectElementName(WriteContext.PsiFileContext psiFileContext, PsiField psiField) {
        GenerateUtils.deleteJavaDoc(psiField.getDocComment());
        List<String> commentList = parseCommentFromElementName(psiFileContext, psiField);

        PsiDocComment psiDocCommentNew = psiFileContext.getElementFactory()
                .createDocCommentFromText(beautifyJavaDocFromComment(
                        Lists.newArrayList(),
                        commentList,
                        Lists.newArrayList())
                );
        psiField.addBefore(psiDocCommentNew, psiField.getFirstChild());
    }

    public static void populateWriteJavaDocFromProjectElementName(WriteContext.PsiFileContext psiFileContext, PsiField psiField) {
        List<String> rawCommentList = CommentParseUtils.parseCommentListFromWholeJavaDoc(psiField.getDocComment());
        GenerateUtils.deleteJavaDoc(psiField.getDocComment());
        List<String> newCommentList = parseCommentFromElementName(psiFileContext, psiField);

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

    public static <T extends PsiModifierListOwner & PsiDocCommentOwner> void reGenerateWriteJavaDocFromAnnotation(WriteContext.PsiFileContext psiFileContext, T psiOwner) {
        List<String> annotationList = CommentParseUtils.parseAnnotationText(psiOwner);
        GenerateUtils.deleteSwaggerAnnotation(psiOwner);
        if (CollectionUtils.isEmpty(annotationList)) {
            return;
        }

        List<String> commentList = CommentParseUtils.parseCommentListFromWholeJavaDoc(psiOwner.getDocComment());
        List<String> tagList = CommentParseUtils.parseTagListFromWholeJavaDoc(psiOwner.getDocComment());

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
        List<String> tagList = CommentParseUtils.parseTagListFromWholeJavaDoc(psiOwner.getDocComment());


        if (CollectionUtils.isNotEmpty(commentList)) {
            return;
        }

        GenerateUtils.deleteJavaDoc(psiOwner.getDocComment());

        PsiDocComment psiDocCommentNew = psiFileContext.getElementFactory()
                .createDocCommentFromText(beautifyJavaDocFromComment(annotationList, commentList, tagList));
        psiOwner.addBefore(psiDocCommentNew, psiOwner.getFirstChild());
    }

    private static String beautifyJavaDocFromComment(List<String> annotationTextList,
                                                     List<String> commentList,
                                                     List<String> tagList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/**\n");
        Set<String> javaDocCommentSet = new LinkedHashSet<>();
        javaDocCommentSet.addAll(annotationTextList);
        javaDocCommentSet.addAll(commentList);

        if (CollectionUtils.isNotEmpty(javaDocCommentSet) && CollectionUtils.isNotEmpty(tagList)) {
            for (String comment : javaDocCommentSet) {
                stringBuilder.append(beautifyJavaDocComment(comment));
            }
            stringBuilder.append(beautifyJavaDocBlackLine());
            for (String tag : tagList) {
                stringBuilder.append(beautifyJavaDocTag(tag));
            }

        } else if (CollectionUtils.isEmpty(javaDocCommentSet) && CollectionUtils.isEmpty(tagList)) {
            stringBuilder.append(beautifyJavaDocBlackLine());

        } else {
            for (String comment : javaDocCommentSet) {
                stringBuilder.append(beautifyJavaDocComment(comment));
            }
            for (String tag : tagList) {
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
