package cc.bitky.jetbrains.plugin.universalgenerate.service.tag;

import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.CommentParseUtils;
import cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils;
import com.intellij.psi.PsiDocCommentOwner;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.javadoc.PsiDocComment;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author bitkylin
 */
public final class JavaDocGenerateUtils {

    private JavaDocGenerateUtils() {
    }

    public static <T extends PsiModifierListOwner & PsiDocCommentOwner> void deleteTagAnnotation(T psiOwner) {
        GenerateUtils.deleteTagAnnotation(psiOwner);
    }

    public static <T extends PsiModifierListOwner & PsiDocCommentOwner> void reGenerateWriteJavaDoc(WriteContext.PsiFileContext psiFileContext, T psiOwner) {
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

    public static <T extends PsiModifierListOwner & PsiDocCommentOwner> void populateWriteJavaDoc(WriteContext.PsiFileContext psiFileContext, T psiOwner) {
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
