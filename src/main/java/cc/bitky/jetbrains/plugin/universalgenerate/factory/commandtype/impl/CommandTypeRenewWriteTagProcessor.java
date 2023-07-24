package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.config.AnnotationTagConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.ModifierAnnotationUtils;
import com.google.common.collect.Sets;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static cc.bitky.jetbrains.plugin.universalgenerate.service.tag.AnnotationTagUtils.calcNextGroupBeginNum;
import static cc.bitky.jetbrains.plugin.universalgenerate.service.tag.AnnotationTagUtils.generateFieldTagAnnotation;
import static cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils.deleteAnnotation;
import static cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils.writeAnnotationForce;

/**
 * Tag注解强制写处理器
 *
 * @author bitkylin
 */
public class CommandTypeRenewWriteTagProcessor extends CommandTypeAbstractWriteTagProcessor implements ICommandTypeProcessor {

    public CommandTypeRenewWriteTagProcessor(WriteContext writeContext, AnnotationTagConfig annotationTagConfig) {
        this.writeContext = writeContext;
        beginNumValue = annotationTagConfig.getBeginNumValue();
        stepNumValue = annotationTagConfig.getStepNumValue();
        tagExistedSet = Sets.newHashSet();
    }

    @Override
    public void writeFile() {
        int num = beginNumValue;
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            if (!canWriteAnnotationTag(psiClassWrapper)) {
                continue;
            }
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                generateFieldTagAnnotation(writeContext.getPsiFileContext(), psiFieldWrapper, num++);
            }
            num = calcNextGroupBeginNum(beginNumValue, stepNumValue, num);
        }
    }

    @Override
    public void writeElement() {
        WriteContext.PsiFileContext psiFileContext = writeContext.getPsiFileContext();
        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        PsiFieldWrapper fieldWrapper = selectWrapper.getField();
        PsiClassWrapper selectedPsiClassWrapper = selectWrapper.getSelectedPsiClassWrapper();
        if (!canWriteAnnotationTag(selectedPsiClassWrapper)) {
            return;
        }
        if (fieldWrapper == null) {
            return;
        }

        deleteAnnotation(psiFileContext, ModifierAnnotationUtils.createWrapperTag(0), fieldWrapper.getPsiField());

        selectedPsiClassWrapper.getFieldList().forEach(psiFieldWrapper -> {
            Optional<Integer> tagValueOptional = psiFieldWrapper.fetchTagValue();
            tagValueOptional.ifPresent(tagExistedSet::add);
        });

        AtomicInteger currentNum = initAvailableBeginNum(tagExistedSet.stream().mapToInt(Integer::intValue).min().orElse(beginNumValue));
        selectedPsiClassWrapper.getFieldList().forEach(psiFieldWrapper -> {
            if (psiFieldWrapper == fieldWrapper) {
                writeAnnotationForce(psiFileContext, ModifierAnnotationUtils.createWrapperTag(currentNum.get()), psiFieldWrapper.getPsiField());
                return;
            }
            Optional<Integer> tagValueOptional = psiFieldWrapper.fetchTagValue();
            if (tagValueOptional.isPresent()) {
                Integer psiFieldTagValue = tagValueOptional.get();
                updateCurrentNum(currentNum, psiFieldTagValue, tagExistedSet);
            }
        });
    }
}
