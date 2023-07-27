package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.config.AnnotationTagConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.ModifierAnnotationUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.intellij.psi.PsiClass;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static cc.bitky.jetbrains.plugin.universalgenerate.service.tag.AnnotationTagUtils.calcNextGroupBeginNum;
import static cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils.writeAnnotationForce;
import static cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils.writeAnnotationOriginalPrimary;

/**
 * Tag注解填充处理器
 *
 * @author bitkylin
 */
public class CommandTypePopulateWriteTagProcessor extends CommandTypeAbstractWriteTagProcessor implements ICommandTypeProcessor {

    public CommandTypePopulateWriteTagProcessor(WriteContext writeContext, AnnotationTagConfig annotationTagConfig) {
        this.writeContext = writeContext;
        beginNumValue = annotationTagConfig.getBeginNumValue();
        stepNumValue = annotationTagConfig.getStepNumValue();
        tagExistedSet = Sets.newHashSet();
    }

    @Override
    public void writeFile() {
        Map<String, Integer> map = Maps.newHashMap();
        AtomicInteger nextGroupTagBeginNum = new AtomicInteger(beginNumValue);
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            PsiClass psiClass = psiClassWrapper.getPsiClass();
            if (!canWriteAnnotationTag(psiClassWrapper)) {
                continue;
            }
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                Optional<Integer> tagValueOptional = psiFieldWrapper.fetchTagValue();
                tagValueOptional.ifPresent(tagValue -> {
                    tagExistedSet.add(tagValue);
                    map.putIfAbsent(psiClass.getQualifiedName(), tagValue);
                    nextGroupTagBeginNum.set(Math.max(nextGroupTagBeginNum.get(), tagValue));
                });
            }
        }

        if (MapUtils.isNotEmpty(map)) {
            nextGroupTagBeginNum.set(calcNextGroupBeginNum(beginNumValue, stepNumValue, nextGroupTagBeginNum.get()));
        }

        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            if (!canWriteAnnotationTag(psiClassWrapper)) {
                continue;
            }
            PsiClass psiClass = psiClassWrapper.getPsiClass();
            Integer groupBeginNum = map.get(psiClass.getQualifiedName());
            if (groupBeginNum == null) {
                groupBeginNum = nextGroupTagBeginNum.get();
                nextGroupTagBeginNum.set(calcNextGroupBeginNum(beginNumValue, stepNumValue, nextGroupTagBeginNum.get()));
            }

            AtomicInteger currentNum = initAvailableBeginNum(groupBeginNum);
            psiClassWrapper.getFieldList().forEach(psiFieldWrapper -> {
                Optional<Integer> tagValueOptional = psiFieldWrapper.fetchTagValue();
                if (tagValueOptional.isPresent()) {
                    Integer psiFieldTagValue = tagValueOptional.get();
                    updateCurrentNum(currentNum, psiFieldTagValue, tagExistedSet);
                } else {
                    writeAnnotationForce(writeContext.getPsiFileContext(), ModifierAnnotationUtils.createWrapperTag(currentNum.get()), psiFieldWrapper.getPsiField());
                    tagExistedSet.add(currentNum.getAndIncrement());
                    updateCurrentNum(currentNum, currentNum.get(), tagExistedSet);
                }
            });
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

        selectedPsiClassWrapper.getFieldList().forEach(psiFieldWrapper -> {
            Optional<Integer> tagValueOptional = psiFieldWrapper.fetchTagValue();
            tagValueOptional.ifPresent(tagExistedSet::add);
        });

        AtomicInteger currentNum = initAvailableBeginNum(tagExistedSet.stream().mapToInt(Integer::intValue).min().orElse(beginNumValue));
        selectedPsiClassWrapper.getFieldList().forEach(psiFieldWrapper -> {
            if (psiFieldWrapper == fieldWrapper) {
                writeAnnotationOriginalPrimary(psiFileContext, ModifierAnnotationUtils.createWrapperTag(currentNum.get()), psiFieldWrapper.getPsiField());
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
