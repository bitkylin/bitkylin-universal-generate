package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.config.AnnotationTagConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ModifierAnnotationEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base.CommandTypeAbstractWriteTagProcessor;
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
public class CommandTypeReGenerateWriteTagProcessor extends CommandTypeAbstractWriteTagProcessor implements ICommandTypeProcessor {

    public CommandTypeReGenerateWriteTagProcessor(WriteContext writeContext, AnnotationTagConfig annotationTagConfig) {
        this.writeContext = writeContext;
        beginNumValue = annotationTagConfig.getBeginNumValue();
        stepNumValue = annotationTagConfig.getStepNumValue();
        tagExistedSet = Sets.newHashSet();
    }

    @Override
    public void doWriteFile() {
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
    public void doWriteElement() {
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

        deleteAnnotation(ModifierAnnotationEnum.TAG, fieldWrapper.getPsiField());

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
