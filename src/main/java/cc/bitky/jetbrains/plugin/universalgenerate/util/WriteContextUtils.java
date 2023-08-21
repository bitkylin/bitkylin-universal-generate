package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiMethodWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.google.common.base.Preconditions;
import com.intellij.codeInsight.intention.preview.IntentionPreviewUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author limingliang
 */
public class WriteContextUtils {

    private WriteContextUtils() {
    }


    public static void assembleUniversalClassInfo(int depth, PsiClass psiClass, WriteContext writeContext) {

        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        PsiElement currentElement = selectWrapper.getCurrentElement();

        Preconditions.checkArgument(depth < 5);


        PsiClassWrapper psiClassWrapper = createPsiClassWrapper(psiClass, writeContext.getPsiFileContext());
        writeContext.addClassWrapper(psiClassWrapper);

        if (depth == 0) {
            writeContext.setFilePsiClassWrapper(psiClassWrapper);
        }

        if (currentElement != null && currentElement.getTextOffset() == psiClass.getTextOffset()) {
            selectWrapper.setClz(psiClass);
            selectWrapper.setSelectedPsiClassWrapper(psiClassWrapper);
            selectWrapper.setSelected(true);
        }

        for (PsiFieldWrapper field : psiClassWrapper.getFieldList()) {
            PsiField psiField = field.getPsiField();
            if (currentElement != null && currentElement.getTextOffset() == psiField.getTextOffset()) {
                selectWrapper.setField(field);
                selectWrapper.setSelectedPsiClassWrapper(psiClassWrapper);
                selectWrapper.setSelected(true);
                break;
            }
        }
        for (PsiMethodWrapper method : psiClassWrapper.getMethodList()) {
            PsiMethod psiMethod = method.getPsiMethod();
            if (currentElement != null && currentElement.getTextOffset() == psiMethod.getTextOffset()) {
                selectWrapper.setMethod(method);
                selectWrapper.setSelectedPsiClassWrapper(psiClassWrapper);
                selectWrapper.setSelected(true);
                break;
            }
        }

        if (psiClass.isEnum()) {
            return;
        }

        if (depth >= 1) {
            return;
        }

        psiClassWrapper.setInnerClassList(Arrays.stream(psiClass.getAllInnerClasses())
                .map(psiClassItem -> createPsiClassWrapper(psiClassItem, writeContext.getPsiFileContext()))
                .toList());

        if (CollectionUtils.isEmpty(psiClassWrapper.getInnerClassList())) {
            return;
        }

        for (PsiClassWrapper innerClass : psiClassWrapper.getInnerClassList()) {
            assembleUniversalClassInfo(depth + 1, innerClass.getPsiClass(), writeContext);
        }

    }

    @NotNull
    private static PsiClassWrapper createPsiClassWrapper(PsiClass psiClass, WriteContext.PsiFileContext psiFileContext) {
        PsiClassWrapper psiClassWrapper = new PsiClassWrapper();
        psiClassWrapper.setPsiClass(psiClass);
        psiClassWrapper.setFieldList(Stream.of(psiClass.getFields())
                .map(BitkylinPsiParseUtils::parsePsiField)
                .filter(WriteContextUtils::filterSpecialPsiField)
                .toList());
        psiClassWrapper.setMethodList(Stream.of(psiClass.getMethods())
                .map(BitkylinPsiParseUtils::parsePsiMethod)
                .filter(WriteContextUtils::filterSpecialPsiMethod)
                .toList());
        DecisionUtils.assembleRoleAndLocation(psiClassWrapper, psiFileContext);
        return psiClassWrapper;
    }

    private static boolean filterSpecialPsiField(PsiFieldWrapper psiFieldWrapper) {
        PsiField psiField = psiFieldWrapper.getPsiField();
        if (StringUtils.equals("serialVersionUID", psiField.getName())) {
            return false;
        }
        if (!psiField.isPhysical()) {
            if (IntentionPreviewUtils.isPreviewElement(psiField)) {
                return true;
            }
            return false;
        }
        return true;
    }

    private static boolean filterSpecialPsiMethod(PsiMethodWrapper psiMethodWrapper) {
        PsiMethod psiMethod = psiMethodWrapper.getPsiMethod();
        if (!psiMethod.isPhysical()) {
            if (IntentionPreviewUtils.isPreviewElement(psiMethod)) {
                return true;
            }
            return false;
        }
        return true;
    }

}
