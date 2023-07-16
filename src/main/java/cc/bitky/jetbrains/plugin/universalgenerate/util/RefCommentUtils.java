package cc.bitky.jetbrains.plugin.universalgenerate.util;

import com.google.common.collect.Lists;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import com.intellij.psi.javadoc.PsiDocComment;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pwhxbdk
 * @date 2020/4/6
 */
public class RefCommentUtils {


    public static String getDataType(String dataType, PsiType psiType) {
        String typeName = BaseTypeEnum.findByName(dataType);
        if (StringUtils.isNotEmpty(typeName)) {
            return typeName;
        }
        if (BaseTypeEnum.isName(dataType)) {
            return dataType;
        }
        String multipartFileText = "org.springframework.web.multipart.MultipartFile";
        String javaFileText = "java.io.File";
        if (psiType.getCanonicalText().equals(multipartFileText)
                || psiType.getCanonicalText().equals(javaFileText)) {
            return "file";
        }
        // 查找是否实现自File类
        for (PsiType superType : psiType.getSuperTypes()) {
            if (superType.getCanonicalText().equals(multipartFileText)
                    || superType.getCanonicalText().equals(javaFileText)) {
                return "file";
            }
        }
        return psiType.getPresentableText();
    }


    /**
     * 获取注解说明  不写/@desc/@describe/@description等 @*
     */
    public static String beautifyCommentFromJavaDoc(PsiDocComment psiDocComment) {
        List<String> commentList = getCommentFromJavaDoc(psiDocComment);
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
    public static List<String> getCommentFromJavaDoc(PsiDocComment psiDocComment) {
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

    /**
     * 获取注解说明  不写/@desc/@describe/@description
     *
     * @param comment 所有注释
     * @return String
     */
    public static Map<String, String> getCommentMethodParam(String comment) {
        Map<String, String> resultMap = new HashMap<>(4);
        String[] strings = comment.split("\n");
        if (strings.length == 0) {
            return null;
        }
        for (String string : strings) {
            String row = StringUtils.deleteWhitespace(string);
            if (StringUtils.isEmpty(row) || StringUtils.startsWith(row, "/**")) {
                continue;
            }

            if (StringUtils.startsWithIgnoreCase(row, "*@param")) {
                int paramIndex = StringUtils.ordinalIndexOf(string, "m", 1);
                string = string.substring(paramIndex + 1).trim().replaceAll("\\s+", " ");
                String[] s = string.split(" ");
                if (s.length < 2) {
                    continue;
                }

                StringBuilder paramDesc = new StringBuilder();
                for (int i = 1; i < s.length; i++) {
                    paramDesc.append(s[i]);
                    if (i == s.length - 1) {
                        break;
                    }
                    paramDesc.append(" ");
                }
                resultMap.put(s[0], paramDesc.toString());
            }
        }
        return resultMap;
    }
}
