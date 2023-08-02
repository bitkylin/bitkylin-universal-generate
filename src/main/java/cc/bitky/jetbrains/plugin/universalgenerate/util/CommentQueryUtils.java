package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.config.ElementNameSuffixConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.LocalizationConfigFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.LocalizationEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ModifierAnnotationEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.ElementNameSuffixInfo;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.google.common.collect.Lists;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiJavaDocumentedElement;
import com.intellij.psi.impl.java.stubs.index.JavaFieldNameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.CommentParseUtils.parseAnnotationText;

/**
 * @author bitkylin
 */
public final class CommentQueryUtils {

    private CommentQueryUtils() {
    }

    public static List<String> queryFieldJavaDocByNameDirectly(WriteContext.PsiFileContext psiFileContext, String elementName) {
        elementName = StringUtils.trimToEmpty(elementName);
        if (StringUtils.isBlank(elementName)) {
            return Lists.newArrayList();
        }
        Collection<PsiField> psiFieldList = JavaFieldNameIndex.getInstance().get(
                elementName,
                psiFileContext.getProject(),
                GlobalSearchScope.projectScope(psiFileContext.getProject())
        );
        if (CollectionUtils.isEmpty(psiFieldList)) {
            return Lists.newArrayList();
        }

        List<String> commentList = psiFieldList.stream()
                .map(PsiJavaDocumentedElement::getDocComment)
                .map(CommentParseUtils::parseCommentListFromWholeJavaDoc)
                .flatMap(Collection::stream)
                .map(StringUtils::trimToEmpty)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(commentList)) {
            commentList = psiFieldList.stream()
                    .map(psiField -> parseAnnotationText(ModifierAnnotationEnum.API_MODEL_PROPERTY, psiField))
                    .flatMap(Collection::stream)
                    .map(StringUtils::trimToEmpty)
                    .filter(StringUtils::isNotBlank)
                    .distinct()
                    .collect(Collectors.toList());
        }
        return commentList;
    }

    public static String removeElementCommentSuffix(String elementName) {
        if (StringUtils.isBlank(elementName)) {
            return StringUtils.EMPTY;
        }
        for (String suffixComment : ElementNameSuffixConfig.fuzzySuffixNameSet()) {
            if (StringUtils.endsWithIgnoreCase(elementName, suffixComment)) {
                elementName = StringUtils.trimToEmpty(elementName.substring(0, elementName.length() - suffixComment.length()));
                if (StringUtils.endsWith(elementName, "-")) {
                    return StringUtils.trimToEmpty(elementName.substring(0, elementName.length() - 1));
                }
            }
        }
        return elementName;
    }

    /**
     * @param pureElementName 纯粹的字段名，不包含前缀、后缀等杂质
     * @return 仅返回模糊匹配结果
     */
    public static List<String> queryFieldJavaDocByNameFuzzy(WriteContext.PsiFileContext psiFileContext, String pureElementName) {
        for (String suffix : ElementNameSuffixConfig.fuzzySuffixNameList()) {
            List<String> commentList = queryFieldJavaDocByNameDirectly(psiFileContext, pureElementName + suffix);
            if (CollectionUtils.isNotEmpty(commentList)) {
                return ListUtils.distinctMap(commentList, CommentQueryUtils::removeElementCommentSuffix);
            }
        }
        return Lists.newArrayList();
    }

    private static ElementNameSuffixInfo createSuffixRemoved(String elementName, String suffix) {
        String removedElementName = elementName.substring(0, elementName.length() - suffix.length());
        LocalizationEnum localizationEnum = ElementNameSuffixConfig.fuzzySuffixNameLocalization(suffix);

        ElementNameSuffixInfo elementNameSuffixInfo = new ElementNameSuffixInfo();
        elementNameSuffixInfo.setElementName(elementName);
        elementNameSuffixInfo.setResolvedElementName(removedElementName);
        elementNameSuffixInfo.setSuffix(suffix);
        elementNameSuffixInfo.setSuffixName(LocalizationConfigFactory.name(localizationEnum));
        return elementNameSuffixInfo;
    }

    private static ElementNameSuffixInfo createSuffixAdded(String elementName, String suffix) {
        String addedElementName = elementName + suffix;

        LocalizationEnum localizationEnum = ElementNameSuffixConfig.fuzzySuffixNameLocalization(suffix);

        ElementNameSuffixInfo elementNameSuffixInfo = new ElementNameSuffixInfo();
        elementNameSuffixInfo.setElementName(elementName);
        elementNameSuffixInfo.setResolvedElementName(addedElementName);
        elementNameSuffixInfo.setSuffix(suffix);
        elementNameSuffixInfo.setSuffixName(LocalizationConfigFactory.name(localizationEnum));
        return elementNameSuffixInfo;
    }

    public static ElementNameSuffixInfo removeFuzzySuffix(String elementName) {
        if (StringUtils.isBlank(elementName)) {
            return null;
        }
        for (String suffix : ElementNameSuffixConfig.fuzzySuffixNameList()) {
            if (StringUtils.endsWithIgnoreCase(elementName, suffix)) {
                return createSuffixRemoved(elementName, suffix);
            }
        }
        return null;
    }

}
