package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.pojo.*;
import com.google.common.base.Preconditions;
import com.intellij.codeInsight.intention.preview.IntentionPreviewUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.source.PsiFieldImpl;
import com.intellij.psi.impl.source.PsiMethodImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

/**
 * @author limingliang
 */
public class WriteContextUtils {

    private WriteContextUtils() {
    }


    public static void assembleUniversalClassInfo(int depth, PsiClass psiClass, WriteContext writeContext) {

        SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        PsiElement currentElement = selectWrapper.getCurrentElement();

        Preconditions.checkArgument(depth < 5);

        if (DecisionUtils.psiClassDisabled(psiClass)) {
            return;
        }

        PsiClassWrapper psiClassWrapper = createPsiClassWrapper(psiClass, writeContext.getPsiFileContext());
        writeContext.addClassWrapper(psiClassWrapper);

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

        // 类最多两层嵌套，更多的嵌套不再识别
        if (depth >= 2) {
            return;
        }

        psiClassWrapper.setInnerClassList(Arrays.stream(psiClass.getInnerClasses())
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
        psiClassWrapper.setSupperFieldList(calcSupperClzFieldList(psiClass));
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

    private static List<PsiFieldWrapper> calcSupperClzFieldList(PsiClass psiClass) {
        List<PsiFieldWrapper> supperFieldList = Lists.newArrayList();
        LongAdder adder = new LongAdder();
        while (adder.longValue() < 10) {
            adder.increment();
            psiClass = psiClass.getSuperClass();
            if (psiClass == null) {
                return supperFieldList;
            }
            supperFieldList.addAll(Stream.of(psiClass.getFields())
                    .map(BitkylinPsiParseUtils::parsePsiField)
                    .filter(WriteContextUtils::filterSpecialPsiField)
                    .toList());
        }
        return supperFieldList;
    }

    private static boolean filterSpecialPsiField(PsiFieldWrapper psiFieldWrapper) {
        PsiField psiField = psiFieldWrapper.getPsiField();
        if (StringUtils.equals("serialVersionUID", psiField.getName())) {
            return false;
        }
        // Intention Preview场景下，所有字段都不是物理的
        if (!psiField.isPhysical()) {
            // lombok 生成的字段，不是该实例
            if (!(psiField instanceof PsiFieldImpl)) {
                return false;
            }
            if (IntentionPreviewUtils.isPreviewElement(psiField)) {
                return true;
            }
            return false;
        }
        return true;
    }

    private static boolean filterSpecialPsiMethod(PsiMethodWrapper psiMethodWrapper) {
        PsiMethod psiMethod = psiMethodWrapper.getPsiMethod();
        // Intention Preview场景下，所有字段都不是物理的
        if (!psiMethod.isPhysical()) {
            // lombok 生成的字段，不是该实例
            if (!(psiMethod instanceof PsiMethodImpl)) {
                return false;
            }
            if (IntentionPreviewUtils.isPreviewElement(psiMethod)) {
                return true;
            }
            return false;
        }
        return true;
    }

}
