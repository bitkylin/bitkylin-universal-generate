package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsState;
import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base.CommandTypeAbstractWriteTagProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.SelectWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.ModifierAnnotationUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.intellij.psi.PsiClass;
import org.apache.commons.collections.MapUtils;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils.writeAnnotationForce;

/**
 * Tag注解填充处理器
 *
 * @author bitkylin
 */
public class CommandTypePopulateWriteTagProcessor extends CommandTypeAbstractWriteTagProcessor implements ICommandTypeProcessor {

    public CommandTypePopulateWriteTagProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
        beginNumValue = GlobalSettingsStateHelper.getInstance().getProtostuffTagStartValue();
        stepNumValue = GlobalSettingsStateHelper.getInstance().getProtostuffTagScopeInterval();
    }

    @Override
    public void doWriteFile() {
        Map<String, Integer> clzMapFirstTagValue = Maps.newHashMap();
        Map<String, Set<Integer>> clzMapTagExisted = Maps.newHashMap();
        AtomicInteger nextGroupTagBeginNum = new AtomicInteger(beginNumValue);
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            PsiClass psiClass = psiClassWrapper.getPsiClass();
            if (!canWriteAnnotationTag(psiClassWrapper)) {
                continue;
            }
            initTagExisted(psiClass.getQualifiedName(), clzMapTagExisted);
            Set<Integer> tagExistedSet = clzMapTagExisted.get(psiClass.getQualifiedName());
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                Optional<Integer> tagValueOptional = psiFieldWrapper.fetchTagValue();
                tagValueOptional.ifPresent(tagValue -> {
                    tagExistedSet.add(tagValue);
                    clzMapFirstTagValue.putIfAbsent(psiClass.getQualifiedName(), tagValue);
                    nextGroupTagBeginNum.set(Math.max(nextGroupTagBeginNum.get(), tagValue));
                });
            }
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getSupperFieldList()) {
                Optional<Integer> tagValueOptional = psiFieldWrapper.fetchTagValue();
                tagValueOptional.ifPresent(tagExistedSet::add);
            }
        }

        if (MapUtils.isNotEmpty(clzMapFirstTagValue)) {
            nextGroupTagBeginNum.set(calcNextGroupBeginNum(beginNumValue, stepNumValue, nextGroupTagBeginNum.get()));
        }

        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            if (!canWriteAnnotationTag(psiClassWrapper)) {
                continue;
            }
            PsiClass psiClass = psiClassWrapper.getPsiClass();
            Set<Integer> tagExistedSet = clzMapTagExisted.get(psiClass.getQualifiedName());
            Integer groupBeginNum = clzMapFirstTagValue.get(psiClass.getQualifiedName());
            if (groupBeginNum == null) {
                groupBeginNum = nextGroupTagBeginNum.get();
                nextGroupTagBeginNum.set(calcNextGroupBeginNum(beginNumValue, stepNumValue, nextGroupTagBeginNum.get()));
            }

            AtomicInteger currentNum = initAvailableBeginNum(groupBeginNum, tagExistedSet);
            psiClassWrapper.getFieldList().forEach(psiFieldWrapper -> {
                Optional<Integer> tagValueOptional = psiFieldWrapper.fetchTagValue();
                if (tagValueOptional.isPresent()) {
                    Integer psiFieldTagValue = tagValueOptional.get();
                    updateCurrentNum(currentNum, psiFieldTagValue, tagExistedSet);
                } else {
                    writeTagAnnotation(psiFieldWrapper, currentNum);
                    tagExistedSet.add(currentNum.getAndIncrement());
                    updateCurrentNum(currentNum, currentNum.get(), tagExistedSet);
                }
            });
        }
    }

    @Override
    public void doWriteElement() {
        SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        PsiFieldWrapper selectedPsiFieldWrapper = selectWrapper.getField();
        PsiClassWrapper selectedPsiClassWrapper = selectWrapper.getSelectedPsiClassWrapper();
        if (!canWriteAnnotationTag(selectedPsiClassWrapper)) {
            return;
        }
        if (selectedPsiFieldWrapper == null) {
            return;
        }

        // 以下代码和 {@link #doWriteFile}一样，仅一行代码有差异

        Map<String, Integer> clzMapFirstTagValue = Maps.newHashMap();
        Map<String, Set<Integer>> clzMapTagExisted = Maps.newHashMap();
        AtomicInteger nextGroupTagBeginNum = new AtomicInteger(beginNumValue);
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            PsiClass psiClass = psiClassWrapper.getPsiClass();
            if (!canWriteAnnotationTag(psiClassWrapper)) {
                continue;
            }
            initTagExisted(psiClass.getQualifiedName(), clzMapTagExisted);
            Set<Integer> tagExistedSet = clzMapTagExisted.get(psiClass.getQualifiedName());
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                Optional<Integer> tagValueOptional = psiFieldWrapper.fetchTagValue();
                tagValueOptional.ifPresent(tagValue -> {
                    tagExistedSet.add(tagValue);
                    clzMapFirstTagValue.putIfAbsent(psiClass.getQualifiedName(), tagValue);
                    nextGroupTagBeginNum.set(Math.max(nextGroupTagBeginNum.get(), tagValue));
                });
            }
            for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getSupperFieldList()) {
                Optional<Integer> tagValueOptional = psiFieldWrapper.fetchTagValue();
                tagValueOptional.ifPresent(tagExistedSet::add);
            }
        }

        if (MapUtils.isNotEmpty(clzMapFirstTagValue)) {
            nextGroupTagBeginNum.set(calcNextGroupBeginNum(beginNumValue, stepNumValue, nextGroupTagBeginNum.get()));
        }

        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            if (!canWriteAnnotationTag(psiClassWrapper)) {
                continue;
            }
            PsiClass psiClass = psiClassWrapper.getPsiClass();
            Set<Integer> tagExistedSet = clzMapTagExisted.get(psiClass.getQualifiedName());
            Integer groupBeginNum = clzMapFirstTagValue.get(psiClass.getQualifiedName());
            if (groupBeginNum == null) {
                groupBeginNum = nextGroupTagBeginNum.get();
                nextGroupTagBeginNum.set(calcNextGroupBeginNum(beginNumValue, stepNumValue, nextGroupTagBeginNum.get()));
            }

            AtomicInteger currentNum = initAvailableBeginNum(groupBeginNum, tagExistedSet);
            psiClassWrapper.getFieldList().forEach(psiFieldWrapper -> {
                Optional<Integer> tagValueOptional = psiFieldWrapper.fetchTagValue();
                if (tagValueOptional.isPresent()) {
                    Integer psiFieldTagValue = tagValueOptional.get();
                    updateCurrentNum(currentNum, psiFieldTagValue, tagExistedSet);
                } else {
                    if (selectedPsiClassWrapper == psiClassWrapper && psiFieldWrapper == selectedPsiFieldWrapper) {
                        writeTagAnnotation(psiFieldWrapper, currentNum);
                    }
                    tagExistedSet.add(currentNum.getAndIncrement());
                    updateCurrentNum(currentNum, currentNum.get(), tagExistedSet);
                }
            });
        }
    }

    private void writeTagAnnotation(PsiFieldWrapper psiFieldWrapper, AtomicInteger currentNum) {
        writeAnnotationForce(writeContext.getPsiFileContext(), ModifierAnnotationUtils.createWrapperTag(currentNum.get()), psiFieldWrapper.getPsiField());
    }

    private void initTagExisted(String qualifiedName, Map<String, Set<Integer>> clzMapTagExisted) {
        GlobalSettingsState.ProtostuffTagAssignEnum tagAssignEnum = GlobalSettingsStateHelper.getInstance().getProtostuffTagAssign();
        if (GlobalSettingsState.ProtostuffTagAssignEnum.FROM_START_VALUE == tagAssignEnum) {
            clzMapTagExisted.put(qualifiedName, Sets.newHashSet());
            return;
        }
        Optional<Set<Integer>> globalTagExisted = clzMapTagExisted.values().stream().findAny();
        globalTagExisted.ifPresentOrElse(set -> clzMapTagExisted.put(qualifiedName, set), () -> clzMapTagExisted.put(qualifiedName, Sets.newHashSet()));
    }

}
