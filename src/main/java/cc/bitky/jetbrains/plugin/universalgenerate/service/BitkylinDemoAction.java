package cc.bitky.jetbrains.plugin.universalgenerate.service;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.FileTypeProcessorFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.impl.SelectionProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.DecisionUtils;
import cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils;
import com.google.common.base.Preconditions;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils.doWrite;

/**
 * @author bitkylin
 */
@Slf4j
public class BitkylinDemoAction extends AnAction {

    @SneakyThrows
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        Preconditions.checkNotNull(project);

        Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
        Preconditions.checkNotNull(editor);

        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        Preconditions.checkNotNull(psiFile);

        PsiClass psiClass = PsiTreeUtil.findChildOfAnyType(psiFile, PsiClass.class);
        Preconditions.checkNotNull(psiClass);

        // 通过光标偏移量获取当前psi元素
        PsiElement currentElement = psiFile.findElementAt(editor.getCaretModel().getOffset());

        WriteContext writeContext = new WriteContext();
        writeContext.setProject(project);
        writeContext.setPsiFile(psiFile);
        writeContext.setPsiClass(psiClass);
        writeContext.setElementFactory(JavaPsiFacade.getElementFactory(project));

        WriteContext.SelectWrapper selectWrapper = new WriteContext.SelectWrapper();
        selectWrapper.setCurrentElement(currentElement);
        writeContext.setSelectWrapper(selectWrapper);

        assembleUniversalClassInfo(0, psiClass, writeContext);

        WriteCommandAction.runWriteCommandAction(project, () -> {


            doWrite(writeContext, "Api", "io.swagger.annotations.Api", "@ApiModelProperty(\"一级类目ID列表\")", psiClass);
            doWrite(writeContext, "Tag", "io.protostuff.Tag", "@Tag(value = 101, alias = \"testAlias\")", psiClass);

            if (selectWrapper != null) {
                return;
            }


            // 遍历当前对象的所有属性
            if (selectWrapper.isSelected()) {
                new SelectionProcessor().doWrite(writeContext);
                return;
            }
            // 获取注释
            GenerateUtils.generateClassAnnotation(writeContext);
            FileTypeProcessorFactory.decide(writeContext).doWrite(writeContext);
        });
    }

    private void assembleUniversalClassInfo(int depth, PsiClass psiClass, WriteContext writeContext) {

        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        PsiElement currentElement = selectWrapper.getCurrentElement();

        Preconditions.checkArgument(depth < 5);

        WriteContext.ClassWrapper classWrapper = WriteContext.createClassWrapper(psiClass);
        writeContext.addClassWrapper(classWrapper);
        classWrapper.setController(DecisionUtils.isController(psiClass));

        if (currentElement.getTextOffset() == psiClass.getTextOffset()) {
            selectWrapper.setClz(psiClass);
            selectWrapper.setSelectedClassWrapper(classWrapper);
            selectWrapper.setSelected(true);
        }

        for (PsiField field : classWrapper.getFieldList()) {
            if (currentElement.getTextOffset() == field.getTextOffset()) {
                selectWrapper.setField(field);
                selectWrapper.setSelectedClassWrapper(classWrapper);
                selectWrapper.setSelected(true);
                break;
            }
        }
        for (PsiMethod method : classWrapper.getMethodList()) {
            if (currentElement.getTextOffset() == method.getTextOffset()) {
                selectWrapper.setMethod(method);
                selectWrapper.setSelectedClassWrapper(classWrapper);
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

        classWrapper.setInnerClassList(Arrays.asList(psiClass.getAllInnerClasses()));

        if (CollectionUtils.isEmpty(classWrapper.getInnerClassList())) {
            return;
        }

        for (PsiClass innerClass : classWrapper.getInnerClassList()) {
            assembleUniversalClassInfo(depth + 1, innerClass, writeContext);
        }

    }

}
